/*     */package com.lidroid.xutils.http;

/*     */
/*     */import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

import android.os.SystemClock;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.callback.DefaultHttpRedirectHandler;
import com.lidroid.xutils.http.callback.FileDownloadHandler;
import com.lidroid.xutils.http.callback.HttpRedirectHandler;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.callback.RequestCallBackHandler;
import com.lidroid.xutils.http.callback.StringDownloadHandler;
import com.lidroid.xutils.task.PriorityAsyncTask;
import com.lidroid.xutils.util.OtherUtils;

public class HttpHandler<T> extends PriorityAsyncTask<Object, Object, Void>
		implements RequestCallBackHandler {
	private final AbstractHttpClient client;
	private final HttpContext context;
	private HttpRedirectHandler httpRedirectHandler;
	private String requestUrl;
	private String requestMethod;
	private HttpRequestBase request;
	private boolean isUploading = true;
	private RequestCallBack<T> callback;
	private int retriedCount = 0;
	private String fileSavePath = null;
	private boolean isDownloadingFile = false;
	private boolean autoResume = false;
	private boolean autoRename = false;
	private String charset;
	private State state = State.WAITING;

	private long expiry = HttpCache.getDefaultExpiryTime();
	private static final int UPDATE_START = 1;
	private static final int UPDATE_LOADING = 2;
	private static final int UPDATE_FAILURE = 3;
	private static final int UPDATE_SUCCESS = 4;
	private long lastUpdateTime;
	private static final NotUseApacheRedirectHandler notUseApacheRedirectHandler = new NotUseApacheRedirectHandler();

	public void setHttpRedirectHandler(HttpRedirectHandler httpRedirectHandler) {
		if (httpRedirectHandler != null)
			this.httpRedirectHandler = httpRedirectHandler;
	}

	public HttpHandler(AbstractHttpClient client, HttpContext context,
			String charset, RequestCallBack<T> callback) {
		this.client = client;
		this.context = context;
		this.callback = callback;
		this.charset = charset;
		this.client.setRedirectHandler(notUseApacheRedirectHandler);
	}

	public State getState() {
		return this.state;
	}

	public void setExpiry(long expiry) {
		this.expiry = expiry;
	}

	public void setRequestCallBack(RequestCallBack<T> callback) {
		this.callback = callback;
	}

	public RequestCallBack<T> getRequestCallBack() {
		return this.callback;
	}

	private ResponseInfo<T> sendRequest(HttpRequestBase request)
			throws HttpException {
		HttpRequestRetryHandler retryHandler = this.client
				.getHttpRequestRetryHandler();
		boolean retry;
		IOException exception;
		do {
			if ((this.autoResume) && (this.isDownloadingFile)) {
				File downloadFile = new File(this.fileSavePath);
				long fileLen = 0L;
				if ((downloadFile.isFile()) && (downloadFile.exists())) {
					fileLen = downloadFile.length();
				}
				if (fileLen > 0L) {
					request.setHeader("RANGE", "bytes=" + fileLen + "-");
				}
			}
			retry = true;
			exception = null;
			try {
				this.requestMethod = request.getMethod();
				if (HttpUtils.sHttpCache.isEnabled(this.requestMethod)) {
					String result = HttpUtils.sHttpCache.get(this.requestUrl);
					if (result != null) {
						return new ResponseInfo(null, result, true);
					}
				}
				ResponseInfo responseInfo = null;
				HttpResponse response = null;
				if (!isCancelled()) {
					response = this.client.execute(request, this.context);
				}
				return handleResponse(response);
			} catch (UnknownHostException e) {
				exception = e;
				retry = retryHandler.retryRequest(exception,
						++this.retriedCount, this.context);
			} catch (IOException e) {
				exception = e;
				retry = retryHandler.retryRequest(exception,
						++this.retriedCount, this.context);
			} catch (NullPointerException e) {
				exception = new IOException(e.getMessage());
				exception.initCause(e);
				retry = retryHandler.retryRequest(exception,
						++this.retriedCount, this.context);
			} catch (HttpException e) {
				throw e;
			} catch (Throwable e) {
				exception = new IOException(e.getMessage());
				exception.initCause(e);
				retry = retryHandler.retryRequest(exception,
						++this.retriedCount, this.context);
			}
		} while (retry);
		throw new HttpException(exception);
	}

	protected Void doInBackground(Object[] params) {
		if ((this.state == State.CANCELLED) || (params == null)
				|| (params.length == 0)) {
			return null;
		}
		if (params.length > 3) {
			this.fileSavePath = String.valueOf(params[1]);
			this.isDownloadingFile = (this.fileSavePath != null);
			this.autoResume = ((Boolean) params[2]).booleanValue();
			this.autoRename = ((Boolean) params[3]).booleanValue();
		}
		try {
			if (this.state == State.CANCELLED) {
				return null;
			}
			this.request = ((HttpRequestBase) params[0]);
			this.requestUrl = this.request.getURI().toString();
			if (this.callback != null) {
				this.callback.setRequestUrl(this.requestUrl);
			}
			publishProgress(new Object[] { Integer.valueOf(1) });

			this.lastUpdateTime = SystemClock.uptimeMillis();

			ResponseInfo responseInfo = sendRequest(this.request);
			if (responseInfo != null) {
				publishProgress(new Object[] { Integer.valueOf(4), responseInfo });
				return null;
			}
		} catch (HttpException e) {
			publishProgress(new Object[] { Integer.valueOf(3), e,
					e.getMessage() });
		}
		return null;
	}

	protected void onProgressUpdate(Object[] values) {
		if ((this.state == State.CANCELLED) || (values == null)
				|| (values.length == 0) || (this.callback == null)) {
			return;
		}
		switch (((Integer) values[0]).intValue()) {
		case 1:
			this.state = State.STARTED;
			this.callback.onStart();
			break;
		case 2:
			if (values.length != 3) {
				return;
			}
			this.state = State.LOADING;
			this.callback.onLoading(Long.valueOf(String.valueOf(values[1]))
					.longValue(), Long.valueOf(String.valueOf(values[2]))
					.longValue(), this.isUploading);
			break;
		case 3:
			if (values.length != 3) {
				return;
			}
			this.state = State.FAILURE;
			this.callback.onFailure((HttpException) values[1],
					(String) values[2]);
			break;
		case 4:
			if (values.length != 2) {
				return;
			}
			this.state = State.SUCCESS;
			this.callback.onSuccess((ResponseInfo) values[1]);
		}
	}

	private ResponseInfo<T> handleResponse(HttpResponse response)
			throws HttpException, IOException {
		if (response == null) {
			throw new HttpException("response is null");
		}
		if (isCancelled()) {
			return null;
		}
		StatusLine status = response.getStatusLine();
		int statusCode = status.getStatusCode();
		if (statusCode < 300) {
			Object result = null;
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				this.isUploading = false;
				if (this.isDownloadingFile) {
					this.autoResume = ((this.autoResume) && (OtherUtils
							.isSupportRange(response)));
					String responseFileName = this.autoRename ? OtherUtils
							.getFileNameFromHttpResponse(response) : null;
					FileDownloadHandler downloadHandler = new FileDownloadHandler();
					result = downloadHandler.handleEntity(entity, this,
							this.fileSavePath, this.autoResume,
							responseFileName);
				} else {
					StringDownloadHandler downloadHandler = new StringDownloadHandler();
					result = downloadHandler.handleEntity(entity, this,
							this.charset);
					if (HttpUtils.sHttpCache.isEnabled(this.requestMethod)) {
						HttpUtils.sHttpCache.put(this.requestUrl,
								(String) result, this.expiry);
					}
				}
			}
			return new ResponseInfo(response, result, false);
		}
		if ((statusCode == 301) || (statusCode == 302)) {
			if (this.httpRedirectHandler == null) {
				this.httpRedirectHandler = new DefaultHttpRedirectHandler();
			}
			HttpRequestBase request = this.httpRedirectHandler
					.getDirectRequest(response);
			if (request != null) {
				return sendRequest(request);
			}
		} else {
			if (statusCode == 416) {
				throw new HttpException(statusCode,
						"maybe the file has downloaded completely");
			}
			HttpEntity entity = response.getEntity();
			StringDownloadHandler downloadHandler = new StringDownloadHandler();
			Object result = downloadHandler.handleEntity(entity, this,
					this.charset);
			throw new HttpException(statusCode, status.getReasonPhrase(),
					response, new ResponseInfo(response, result, false));
		}
		return null;
	}

	public void cancel() {
		this.state = State.CANCELLED;
		if ((this.request != null) && (!this.request.isAborted()))
			try {
				this.request.abort();
			} catch (Throwable localThrowable) {
			}
		if (!isCancelled())
			try {
				cancel(true);
			} catch (Throwable localThrowable1) {
			}
		if (this.callback != null)
			this.callback.onCancelled();
	}

	public boolean updateProgress(long total, long current,
			boolean forceUpdateUI) {
		if ((this.callback != null) && (this.state != State.CANCELLED)) {
			if (forceUpdateUI) {
				publishProgress(new Object[] { Integer.valueOf(2),
						Long.valueOf(total), Long.valueOf(current) });
			} else {
				long currTime = SystemClock.uptimeMillis();
				if (currTime - this.lastUpdateTime >= this.callback.getRate()) {
					this.lastUpdateTime = currTime;
					publishProgress(new Object[] { Integer.valueOf(2),
							Long.valueOf(total), Long.valueOf(current) });
				}
			}
		}
		return this.state != State.CANCELLED;
	}

	private static final class NotUseApacheRedirectHandler implements
			RedirectHandler {
		public boolean isRedirectRequested(HttpResponse httpResponse,
				HttpContext httpContext) {
			return false;
		}

		public URI getLocationURI(HttpResponse httpResponse,
				HttpContext httpContext) throws ProtocolException {
			return null;
		}
	}

	public static enum State {
		WAITING(0), STARTED(1), LOADING(2), FAILURE(3), CANCELLED(4), SUCCESS(5);

		private int value = 0;

		private State(int value) {
			this.value = value;
		}

		public static State valueOf(int value) {
			switch (value) {
			case 0:
				return WAITING;
			case 1:
				return STARTED;
			case 2:
				return LOADING;
			case 3:
				return FAILURE;
			case 4:
				return CANCELLED;
			case 5:
				return SUCCESS;
			}
			return FAILURE;
		}

		public int value() {
			return this.value;
		}
	}
}