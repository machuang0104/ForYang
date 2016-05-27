/**
 * Project Name:ParkingPass
 * File Name:MyViewPager.java
 * Package Name:cn.com.parkingpass.view.viewpager
 * Date:2015-9-15上午9:49:52
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.widget.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ClassName:MyViewPager
 * 
 * @author machuang
 */
public class ScrollViewPager extends ViewPager {

	public ScrollViewPager(Context context) {
		super(context);
	}

	public ScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if ((getCurrentItem() + 1) == getChildCount())
			return false;
		else
			return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if ((getCurrentItem() + 1) == getChildCount())
			return false;
		else
			return super.onTouchEvent(arg0);
	}
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		try {
			super.onLayout(arg0, arg1, arg2, arg3, arg4);
		} catch (Exception e) {
		}
	}

}
