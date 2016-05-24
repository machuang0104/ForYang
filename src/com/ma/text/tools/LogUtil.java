package com.ma.text.tools;

import com.ma.text.common.Config;

import android.util.Log;

/**
 * 
 * @author libin
 * 
 */
public class LogUtil {

	private LogUtil() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	private static final String TAG = "way";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (Config.isDEBUG)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (Config.isDEBUG)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (Config.isDEBUG)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (Config.isDEBUG)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (Config.isDEBUG)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (Config.isDEBUG)
			Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (Config.isDEBUG)
			Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (Config.isDEBUG)
			Log.v(tag, msg);
	}
	public static void w(String tag, String msg) {
		if (Config.isDEBUG)
			Log.w(tag, msg);
	}
}