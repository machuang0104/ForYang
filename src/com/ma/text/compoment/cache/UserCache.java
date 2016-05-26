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
		SharedUtil.saveFloat("location_time", System.currentTimeMillis());
		SharedUtil.saveString("city", city);
	}

	public static String getCity() {
		return SharedUtil.getString("city");
	}

	/**
	 * 100秒内不重新定位
	 * @return
	 */
	public static boolean isNeddLocation() {
		float t = SharedUtil.getFloat("location_time");
		if (t == -1) {
			return true;
		}
		if (System.currentTimeMillis() - t > 100000) {
			return true;
		} else {
			return false;
		}
	}
}