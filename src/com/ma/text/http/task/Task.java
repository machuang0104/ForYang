/**
 * Project Name:ParkingPass
 * File Name:HttpTask.java
 * Package Name:cn.com.parkingpass.httpmy
 * Date:2015-6-16下午2:37:29
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.http.task;

import java.util.HashMap;

import com.ma.text.http.MCallBack;
import com.ma.text.http.MHttpUtil;
import com.ma.text.tools.encrypt.MD5Util;
import com.ma.text.vo.db.TestVo;

/**
 * ClassName:HttpTask <br/>
 * Date: 2015-6-16 下午2:37:29 <br/>
 * 
 */
public class Task {
	private static MHttpUtil http;

	private static MHttpUtil getInstance() {
		if (http == null)
			http = new MHttpUtil();
		return http;
	}

	public static void login(String account, String pwd,
			MCallBack<TestVo> callback) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("password", MD5Util.generatePassword(pwd));
		getInstance().postBody(TaskId.LOGIN, map, false, callback);
	}

}