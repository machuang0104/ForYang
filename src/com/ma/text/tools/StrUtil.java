/**
 * Project Name:ParkingPass
 * File Name:StrUtil.java
 * Package Name:cn.com.parkingpass.tools
 * Date:2015-7-31上午9:15:18
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.tools;
/**
 * Date: 2015-7-31 上午9:15:18
 * 
 * @author machuang
 */
public class StrUtil {

	public static final boolean isEmpty(String str) {
		if (str == null)
			return true;
		else if (str.length() == 0)
			return true;
		else if (str.trim().length() == 0)
			return true;
		else if (str.equals("null"))
			return true;
		return false;
	}

}
