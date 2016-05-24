/**
 * Project Name:ParkingPass
 * File Name:Config.java
 * Package Name:cn.com.parkingpass.common
 * Date:2015-9-23下午3:44:30
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.common;

/**
 * ClassName:Config <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2015-9-23 下午3:44:30 <br/>
 * 
 * @author machuang
 */
public class Config {

	/**
	 * 是否调试，发布时为false，控制日志输出等
	 */
	public static boolean isDEBUG = true;
	/** 默认数据库的名字 */
	public static final String DB_NAME = "darling.db";
	public static final String SDCARD_PATH = "darling";
	public static int MAP_ZOOM = 16;
	public static String SHARE_URL = "http://51freeparking.com.cn/pk/webPages/coupon/userCouponShare.jsp";
	/**
	 * 网页中分享按钮的ID
	 */
	public static String SHARE_BTN_ID = "btn-coupon-share";
}
