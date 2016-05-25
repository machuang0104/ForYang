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

	public static String getVersion() {
		String ver = SharedUtil.getString("app_version1");
		if (ver.equals("")) {
			return "1.0.0_1";
		} else
			return ver;
	}
}