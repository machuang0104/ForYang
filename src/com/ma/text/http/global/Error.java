/**
 * Project Name:ParkingPass
 * File Name:Error.java
 * Package Name:com.zf.parking.http.global
 * Date:2015-10-21下午4:32:19
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.http.global;

import com.ma.text.R;
import com.ma.text.tools.ResUtil;

/**
 * ClassName:Error <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2015-10-21 下午4:32:19 <br/>
 * 
 * @author machuang
 */
public class Error {

	public static String getExcMsg(int errorCode) {
		switch (errorCode) {
		case 400 :
			return "请求服务器失败:400";
		case 403 :
			return "服务器拒绝访问:403";
		case 404 :
			return "请求未找到:404";
		case 405 :
			return "请求失败:405";
		case 406 :
			return "请求响应失败:406";
		case 408 :
			return "服务器响应超时:408";
		case 409 :
			return "请求资源冲突:409";
		case 410 :
			return "请求不可用:410";
		case 413 :
			return "服务器拒绝访问:413";
		case 414 :
			return "请求url过长:414";
		case 415 :
			return "请求格式不支持:415";
		case 421 :
			return "当前链接数过多:421";
		case 422 :
			return "请求语法错误:422";
		case 423 :
			return "当前资源被锁定:423";
		case 500 :
			return "服务器内部错误:500";
		case 501 :
			return "服务器连接失败:501";
		case 502 :
			return "服务器连接失败:502";
		case 503 :
			return "服务器连接失败:503";
		case 504 :
			return "服务器连接失败:504";
		case 505 :
			return "服务器连接失败:505";
		case 506 :
			return "服务器连接失败:506";
		case 507 :
			return "服务器连接失败:507";
		case 508 :
			return "服务器连接失败:508";
		case 509 :
			return "服务器连接失败:509";
		case 510 :
			return "服务器连接失败:510";
		default :
			return ResUtil.getStr(R.string.exc_base) + ":" + errorCode;
		}
	}
}
