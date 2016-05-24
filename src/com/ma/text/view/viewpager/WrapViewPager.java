/**
 * Project Name:ParkingPass
 * File Name:MyViewPager.java
 * Package Name:cn.com.parkingpass.view.viewpager
 * Date:2015-9-15上午9:49:52
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * ClassName:MyViewPager
 * 
 * @author machuang
 */
public class WrapViewPager extends ViewPager {

	public WrapViewPager(Context context) {
		super(context);
	}

	public WrapViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();
			if (h > height) // 采用最大的view的高度。
				height = h;
		}
		heightMeasureSpec = MeasureSpec
				.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
