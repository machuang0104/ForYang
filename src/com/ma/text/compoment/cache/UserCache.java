package com.ma.text.compoment.cache;

public class UserCache {
	public static String getPWD() {
		return SharedUtil.getString("d_pwd");
	}

	public static void savePWD(String pwd) {
		SharedUtil.saveString("d_pwd", pwd);
	}

	public static void saveVersion(String version) {
		SharedUtil.saveString("app_version", version);
	}

	public static void saveCity(String city) {
		SharedUtil.saveString("city", city);
	}

	public static String getCity() {
		return SharedUtil.getString("city");
	}
}