/**
 * Project Name:LightingExpress
 * File Name:SharePreference.java
 * Package Name:com.express.main.module.ecommerce
 * Date:2015-2-12上午11:11:46
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.tools.cache;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ma.text.app.App;

/**
 * ClassName:SharePreference <br/>
 * Date: 2015-2-12 上午11:11:46 <br/>
 */
public class ShareUtil {
	public static final String PREFS_NAME = "manager_preference";

	public static void saveInt(String k, int v) {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.putInt(k, v);
		editor.commit();
	}

	public static void saveString(String k, String v) {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		if (v == null)
			v = "";
		editor.putString(k, v);
		editor.commit();
	}

	public static void saveDouble(String k, double v) {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.putFloat(k, (float) v);
		editor.commit();
	}

	public static void saveFloat(String k, float v) {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.putFloat(k, v);
		editor.commit();
	}

	public static void saveBoolean(String k, boolean v) {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.putBoolean(k, v);
		editor.commit();
	}

	public static int getInt(String k) {
		SharedPreferences get = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		return get.getInt(k, -1);
	}

	public static String getString(String k) {
		SharedPreferences get = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		return get.getString(k, "");
	}

	public static double getDouble(String k) {
		SharedPreferences get = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		return (double) get.getFloat(k, -1);
	}

	public static float getFloat(String k) {
		SharedPreferences get = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		return get.getFloat(k, -1);
	}

	public static boolean getBoolean(String k) {
		SharedPreferences get = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		return get.getBoolean(k, false);
	}

	/**
	 * 全部清空
	 */
	public static void clear() {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 清空部分数据
	 * 
	 * @param k
	 */
	public static void clear(String k) {
		SharedPreferences set = App.getApp().getSharedPreferences(PREFS_NAME,
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.remove(k);
		editor.commit();
	}

}