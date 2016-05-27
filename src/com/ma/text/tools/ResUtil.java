/**
 * Project Name:ParkingPass
 * File Name:StrUtil.java
 * Package Name:cn.com.parkingpass.tools
 * Date:2015-7-24上午9:17:20
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.tools;

import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.ma.text.App;
import com.ma.text.R;

/**
 * ClassName:StrUtil <br/>
 * Function: 获取资源工具类 Date: 2015-7-24 上午9:17:20 <br/>
 * 
 * @author machuang
 */
public class ResUtil {
	public static final String getStr(int strId) {
		StringBuilder s = new StringBuilder();
		try {
			s.append(App.getApp().getResources().getString(strId));
		} catch (NotFoundException e) {
			LogUtil.e("ResUtil", e.getMessage());
			return "Resource Not Found " + strId;
		}
		return s.toString();
	}

	public static final int getColor(int colorId) {
		int color;
		try {
			color = App.getApp().getResources().getColor(colorId);
		} catch (NotFoundException e) {
			e.printStackTrace();
			color = Color.BLACK;
		}
		return color;
	}
	public static final float getDimen(int dimenId) {
		float dimen;
		try {
			dimen = App.getApp().getResources().getDimension(dimenId);
		} catch (NotFoundException e) {
			e.printStackTrace();
			dimen = 16;
		}
		return dimen;
	}

	public static final Drawable getDrawable(int drawableId) {
		Drawable dra = null;
		try {
			dra = App.getApp().getResources().getDrawable(drawableId);
		} catch (NotFoundException e) {
			dra = App.getApp().getResources().getDrawable(R.drawable.ic_launcher);
			e.printStackTrace();
		}
		return dra;
	}
}
