/**
 * Project Name:ParkingPass
 * File Name:UserCache.java
 * Package Name:cn.com.parkingpass.tools.cacheinfo
 * Date:2015-9-22下午2:02:35
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.tools.cache;

/**
 * ClassName:UserCache
 * 
 * @author machuang
 */
public class SetCache {



	private static final String VERSION = "app_version";

	public static void saveVersion(String version) {
		ShareUtil.saveString(VERSION, version);
	}

	public static String getVersion() {
		String ver = ShareUtil.getString(VERSION);
		if (ver.equals("")) {
			return "1.0.0_1";
		} else
			return ver;
	}

}