package com.ma.text.compoment.cache;

import com.ma.text.tools.cache.ShareUtil;

public class UserCache {
	public static String getPWD() {
		return ShareUtil.getString("d_pwd");
	}

	public static void savePWD(String pwd) {
		ShareUtil.saveString("d_pwd", pwd);
	}

	public static void saveCity(String city) {
		ShareUtil.saveString("city", city);
	}

	public static String getCity() {
		return ShareUtil.getString("city");
	}

}