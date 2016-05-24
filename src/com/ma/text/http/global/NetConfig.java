/**
 * Project Name:ParkingPass
 * File Name:NetConfig.java
 * Package Name:cn.com.parkingpass.common
 * Date:2015-5-28上午9:46:40
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.http.global;

/**
 * ClassName:NetConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2015-5-28 上午9:46:40 <br/>
 * 
 */
public class NetConfig {
	// ========================后台请求地址===================
	/** * BASE_HTTP_IP:阿里云生产地址 */
//	private static String IP_BASE = "http://51freeparking.com.cn/pk";
	/** * BASE_HTTP_IP:公司服务器地址 */
	private static String IP_BASE = "http://180.168.51.234:21502/pk";
	private static String IP_FULL = IP_BASE + "/pm/main";

	public static String getServerUrl() {
		return IP_FULL;
	}

	public static String getImgUrl(String imgUrl) {

		if (imgUrl.contains("http")) {
			return imgUrl;
		} else {
			return IP_BASE + imgUrl;
		}
	}


	/**
	 * @return 正式支付时替换https
	 */
	public static final String getPayIp() {
		return IP_BASE.replace("http", "http") + "/app-protect/paymentOrder";
	}

}