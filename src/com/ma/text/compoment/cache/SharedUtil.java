package com.ma.text.compoment.cache;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ma.text.app.App;
import com.ma.text.common.K;

/**
 * ClassName:SharePreference <br/>
 * Date: 2016-2-12 上午11:11:46 <br/>
 * @author  machuang
 */
public class SharedUtil {

	public static void saveInt(String key, int value) {
		SharedPreferences set = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void saveString(String key, String value) {
		SharedPreferences set = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		if (value == null)
			value = "";
		editor.putString(key, value);
		editor.commit();
	}

	public static void saveFloat(String key, float value) {
		SharedPreferences set = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	public static int getInt(String key) {
		SharedPreferences get = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		return get.getInt(key, -1);
	}

	public static String getString(String key) {
		SharedPreferences get = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		return get.getString(key, "");
	}

	public static float getFloat(String key) {
		SharedPreferences get = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		return get.getFloat(key, -1);
	}

	/**
	 * 全部清空
	 */
	public static void clear() {
		SharedPreferences set = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 清空部分数据
	 * 
	 * @param key
	 */
	public static void clear(String key) {
		SharedPreferences set = App.getApp().getSharedPreferences(
				K.cache.SHARED_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = set.edit();
		editor.remove(key);
		editor.commit();
	}
}