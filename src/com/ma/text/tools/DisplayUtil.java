package com.ma.text.tools;

import com.ma.text.app.App;

import android.util.DisplayMetrics;

/**
 * @Description:
 */
public class DisplayUtil {

	public static DisplayMetrics getMetrics() {

		// 第一种方式
		DisplayMetrics metric = App.getApp().getResources().getDisplayMetrics();
		// 第二种方式
		// DisplayMetrics metric = new DisplayMetrics();
		// ac.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}

	public static int getScreenWidth() {
		DisplayMetrics metric = getMetrics();
		int width = metric.widthPixels; // 屏幕宽度（像素）
		return width;
	}

	public static int getScreenHeight() {
		DisplayMetrics metric = getMetrics();
		int width = metric.heightPixels; // 屏幕高度（像素）
		return width;
	}

	/**
	 * 将px值转换为dp值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = getMetrics().density;
		int dip = (int) (pxValue / scale + 0.5f);
		return dip;
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(float dipValue) {
		final float scale = getMetrics().density;
		int px = (int) (dipValue * scale + 0.5f);
		return px;
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = getMetrics().scaledDensity;
		int sp = (int) (pxValue / fontScale + 0.5f);
		return sp;
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = getMetrics().scaledDensity;
		int px = (int) (spValue * fontScale + 0.5f);
		return px;
	}

}
