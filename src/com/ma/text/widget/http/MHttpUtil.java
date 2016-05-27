/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ma.text.widget.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpCache;
import com.lidroid.xutils.http.callback.HttpRedirectHandler;
import com.lidroid.xutils.http.client.RetryHandler;
import com.lidroid.xutils.http.client.entity.GZipDecompressingEntity;
import com.lidroid.xutils.https.SSLTrust;
import com.lidroid.xutils.task.PriorityExecutor;
import com.lidroid.xutils.util.OtherUtils;
import com.ma.text.App;
import com.ma.text.R;
import com.ma.text.broadcast.receiver.netstate.NetWorkUtil;
import com.ma.text.client.http.global.ActionUrl;
import com.ma.text.common.K;
import com.ma.text.tools.tip.ToastUtil;
import com.ma.text.widget.http.MRequest.HttpMethod;
import com.ma.text.widget.log.LogUtil;

import android.text.TextUtils;

@SuppressWarnings("deprecation")
public class MHttpUtil {
	public final static String TAG = "tag_http";
	public final static HttpCache sHttpCache = new HttpCache();

	private final DefaultHttpClient httpClient;
	private final HttpContext httpContext = new BasicHttpContext();

	private HttpRedirectHandler httpRedirectHandler;

	public MHttpUtil() {
		this(DEFAULT_CONN_TIMEOUT, null);
	}

	public MHttpUtil(String userAgent) {
		this(DEFAULT_CONN_TIMEOUT, userAgent);
	}

	public MHttpUtil(int connTimeout, String userAgent) {
		HttpParams params = new BasicHttpParams();

		ConnManagerParams.setTimeout(params, connTimeout);
		HttpConnectionParams.setSoTimeout(params, connTimeout);
		HttpConnectionParams.setConnectionTimeout(params, connTimeout);

		if (TextUtils.isEmpty(userAgent)) {
			userAgent = OtherUtils.getUserAgent(null);
		}
		HttpProtocolParams.setUserAgent(params, userAgent);

		ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(10));
		ConnManagerParams.setMaxTotalConnections(params, 10);

		HttpConnectionParams.setTcpNoDelay(params, true);
		HttpConnectionParams.setSocketBufferSize(params, 1024 * 8);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLTrust.getSocketFactory1(), 443));

		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, schemeRegistry), params);
		httpClient.setHttpRequestRetryHandler(new RetryHandler(DEFAULT_RETRY_TIMES));

		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
			@Override
			public void process(org.apache.http.HttpRequest httpRequest, HttpContext httpContext)
					throws org.apache.http.HttpException, IOException {
				if (!httpRequest.containsHeader(HEADER_ACCEPT_ENCODING)) {
					httpRequest.addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
				}
			}
		});

		httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
			@Override
			public void process(HttpResponse response, HttpContext httpContext)
					throws org.apache.http.HttpException, IOException {
				final HttpEntity entity = response.getEntity();
				if (entity == null) {
					return;
				}
				final Header encoding = entity.getContentEncoding();
				if (encoding != null) {
					for (HeaderElement element : encoding.getElements()) {
						if (element.getName().equalsIgnoreCase("gzip")) {
							response.setEntity(new GZipDecompressingEntity(response.getEntity()));
							return;
						}
					}
				}
			}
		});
	}

	// ************************************ default settings & fields
	// ****************************

	private String responseTextCharset = HTTP.UTF_8;

	private long currentRequestExpiry = HttpCache.getDefaultExpiryTime();

	private final static int DEFAULT_CONN_TIMEOUT = 1000 * 15; // 15s

	private final static int DEFAULT_RETRY_TIMES = 1;

	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";

	private final static int DEFAULT_POOL_SIZE = 3;
	private final static PriorityExecutor EXECUTOR = new PriorityExecutor(DEFAULT_POOL_SIZE);

	public HttpClient getHttpClient() {
		return this.httpClient;
	}

	// ***************************************** config
	// *******************************************

	public MHttpUtil configResponseTextCharset(String charSet) {
		if (!TextUtils.isEmpty(charSet)) {
			this.responseTextCharset = charSet;
		}
		return this;
	}

	public MHttpUtil configHttpRedirectHandler(HttpRedirectHandler httpRedirectHandler) {
		this.httpRedirectHandler = httpRedirectHandler;
		return this;
	}

	public MHttpUtil configHttpCacheSize(int httpCacheSize) {
		sHttpCache.setCacheSize(httpCacheSize);
		return this;
	}

	public MHttpUtil configDefaultHttpCacheExpiry(long defaultExpiry) {
		HttpCache.setDefaultExpiryTime(defaultExpiry);
		currentRequestExpiry = HttpCache.getDefaultExpiryTime();
		return this;
	}

	public MHttpUtil configCurrentHttpCacheExpiry(long currRequestExpiry) {
		this.currentRequestExpiry = currRequestExpiry;
		return this;
	}

	public MHttpUtil configCookieStore(CookieStore cookieStore) {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		return this;
	}

	public MHttpUtil configUserAgent(String userAgent) {
		HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
		return this;
	}

	public MHttpUtil configTimeout(int timeout) {
		final HttpParams httpParams = this.httpClient.getParams();
		ConnManagerParams.setTimeout(httpParams, timeout);
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		return this;
	}

	public MHttpUtil configSoTimeout(int timeout) {
		final HttpParams httpParams = this.httpClient.getParams();
		HttpConnectionParams.setSoTimeout(httpParams, timeout);
		return this;
	}

	public MHttpUtil configRegisterScheme(Scheme scheme) {
		this.httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
		return this;
	}

	public MHttpUtil configSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		Scheme scheme = new Scheme("https", sslSocketFactory, 443);
		this.httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
		return this;
	}

	public MHttpUtil configRequestRetryCount(int count) {
		this.httpClient.setHttpRequestRetryHandler(new RetryHandler(count));
		return this;
	}

	public MHttpUtil configRequestThreadPoolSize(int threadPoolSize) {
		MHttpUtil.EXECUTOR.setPoolSize(threadPoolSize);
		return this;
	}

	// ***************************************** send request
	// *******************************************

	public <T> MHandler<T> post(int actionId, MParams params, String urlParams, MCallBack<T> callBack) {
		return send(actionId, HttpMethod.POST, urlParams, params, callBack);
	}

	public <T> MHandler<T> post(int actionId, MParams params, MCallBack<T> callBack) {
		return send(actionId, HttpMethod.POST, null, params, callBack);
	}

	public <T> MHandler<T> get(int actionId, MParams params, MCallBack<T> callBack) {
		return send(actionId, HttpMethod.GET, null, params, callBack);
	}

	public <T> MHandler<T> get(int actionId, MParams params, String urlParams, MCallBack<T> callBack) {
		return send(actionId, HttpMethod.GET, urlParams, params, callBack);
	}

	private <T> MHandler<T> send(int actionId, MRequest.HttpMethod method, String urlParams, MParams params,
			MCallBack<T> callBack) {
		String url = null;
		if (!NetWorkUtil.isNetworkConnected(App.getApp())) {
			callBack.onFailure(new HttpException(-1), "no network");
			return null;
		}
		if (urlParams == null) {
			url = ActionUrl.getUrl(actionId);
		} else {
			url = ActionUrl.getUrl(actionId, urlParams);
		}
		if (url == null)
			throw new IllegalArgumentException("url may not be null");

		LogUtil.d(K.TAG.TAG_HTTP, "url = " + url);
		if (params != null)
			LogUtil.d(K.TAG.TAG_HTTP, params.toString());
		MRequest request = new MRequest(method, url);
		request.setActionId(actionId);
		return sendRequest(request, params, callBack);
	}

	/**
	 * postBody: 参数添加至body中的post方法
	 * 
	 * @param map
	 *            查询参数
	 * @param isEncrypt
	 * @param callBack
	 */
	public <T> MHandler<T> postBody(int taskId, Object map, boolean isEncrypt, MCallBack<T> callBack) {
		String url = ActionUrl.getUrl(taskId);
		MRequest request = new MRequest(HttpMethod.POST, url);
		request.setActionId(taskId);
		LogUtil.i(TAG, "url =" + url + ";TaskId = " + taskId);
		MParams params = new MParams();
		params.addHeader("Content-Type", "application/json");
		String json = "";
		// getRequestStr2(map, taskId, isEncrypt);
		LogUtil.i(TAG, "params = " + json);
		try {
			StringEntity en = new StringEntity(json, "UTF-8");
			en.setContentEncoding("UTF-8");
			en.setContentType("application/json");
			params.setBodyEntity(en);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sendRequest(request, params, callBack);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	private <T> MHandler<T> sendRequest(MRequest request, MParams params, MCallBack<T> callBack) {

		MHandler<T> handler = new MHandler<T>(httpClient, httpContext, responseTextCharset, callBack);

		handler.setExpiry(currentRequestExpiry);
		handler.setHttpRedirectHandler(httpRedirectHandler);
		request.setRequestParams(params, handler);

		if (!NetWorkUtil.isNetworkConnected(App.getApp())) {
			ToastUtil.show(R.string.tip_no_network);
			HttpException error = new HttpException(App.getApp().getString(R.string.tip_no_network));
			callBack.onFailure(error, App.getApp().getString(R.string.tip_no_network));
			return null;
		}
		handler.executeOnExecutor(EXECUTOR, request);
		// App.addRequest(request.getTaskId(), handler);
		return handler;
	}

}