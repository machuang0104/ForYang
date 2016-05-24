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

package com.lidroid.xutils.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/**
 * Created by wyouflf on 13-8-30.
 */
public class OtherUtils {
	private OtherUtils() {
	}

	/**
	 * @param context
	 *            if null, use the default format (Mozilla/5.0 (Linux; U;
	 *            Android %s) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0
	 *            %sSafari/534.30).
	 * @return
	 */
	public static String getUserAgent(Context context) {
		String webUserAgent = null;
		if (context != null) {
			try {
				Class<?> sysResCls = Class
						.forName("com.android.internal.R$string");
				Field webUserAgentField = sysResCls
						.getDeclaredField("web_user_agent");
				Integer resId = (Integer) webUserAgentField.get(null);
				webUserAgent = context.getString(resId);
			} catch (Throwable ignored) {
			}
		}
		if (TextUtils.isEmpty(webUserAgent)) {
			webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
		}

		Locale locale = Locale.getDefault();
		StringBuffer buffer = new StringBuffer();
		// Add version
		final String version = Build.VERSION.RELEASE;
		if (version.length() > 0) {
			buffer.append(version);
		} else {
			// default to "1.0"
			buffer.append("1.0");
		}
		buffer.append("; ");
		final String language = locale.getLanguage();
		if (language != null) {
			buffer.append(language.toLowerCase(locale));
			final String country = locale.getCountry();
			if (country != null) {
				buffer.append("-");
				buffer.append(country.toLowerCase(locale));
			}
		} else {
			// default to "en"
			buffer.append("en");
		}
		// add the model for the release build
		if ("REL".equals(Build.VERSION.CODENAME)) {
			final String model = Build.MODEL;
			if (model.length() > 0) {
				buffer.append("; ");
				buffer.append(model);
			}
		}
		final String id = Build.ID;
		if (id.length() > 0) {
			buffer.append(" Build/");
			buffer.append(id);
		}
		return String.format(webUserAgent, buffer, "Mobile ");
	}

	/**
	 * @param context
	 * @param dirName
	 *            Only the folder name, not full path.
	 * @return app_cache_path/dirName
	 */
	public static boolean isSupportRange(final HttpResponse response) {
		if (response == null)
			return false;
		Header header = response.getFirstHeader("Accept-Ranges");
		if (header != null) {
			return "bytes".equals(header.getValue());
		}
		header = response.getFirstHeader("Content-Range");
		if (header != null) {
			String value = header.getValue();
			return value != null && value.startsWith("bytes");
		}
		return false;
	}

	public static String getFileNameFromHttpResponse(final HttpResponse response) {
		if (response == null)
			return null;
		String result = null;
		Header header = response.getFirstHeader("Content-Disposition");
		if (header != null) {
			for (HeaderElement element : header.getElements()) {
				NameValuePair fileNamePair = element
						.getParameterByName("filename");
				if (fileNamePair != null) {
					result = fileNamePair.getValue();
					// try to get correct encoding str
					result = CharsetUtils.toCharset(result, HTTP.UTF_8,
							result.length());
					break;
				}
			}
		}
		return result;
	}

	public static Charset getCharsetFromHttpRequest(
			final HttpRequestBase request) {
		if (request == null)
			return null;
		String charsetName = null;
		Header header = request.getFirstHeader("Content-Type");
		if (header != null) {
			for (HeaderElement element : header.getElements()) {
				NameValuePair charsetPair = element
						.getParameterByName("charset");
				if (charsetPair != null) {
					charsetName = charsetPair.getValue();
					break;
				}
			}
		}

		boolean isSupportedCharset = false;
		if (!TextUtils.isEmpty(charsetName)) {
			try {
				isSupportedCharset = Charset.isSupported(charsetName);
			} catch (Throwable e) {
			}
		}

		return isSupportedCharset ? Charset.forName(charsetName) : null;
	}

	private static final int STRING_BUFFER_LENGTH = 100;

	public static long sizeOfString(final String str, String charset)
			throws UnsupportedEncodingException {
		if (TextUtils.isEmpty(str)) {
			return 0;
		}
		int len = str.length();
		if (len < STRING_BUFFER_LENGTH) {
			return str.getBytes(charset).length;
		}
		long size = 0;
		for (int i = 0; i < len; i += STRING_BUFFER_LENGTH) {
			int end = i + STRING_BUFFER_LENGTH;
			end = end < len ? end : len;
			String temp = new String(str.substring(i, end));
			size += temp.getBytes(charset).length;
		}
		return size;
	}

	public static StackTraceElement getCallerStackTraceElement() {
		return Thread.currentThread().getStackTrace()[4];
	}

}