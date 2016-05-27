package com.ma.text.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.ma.text.App;
import com.ma.text.R;

/**
 * @Description: view工具类
 */
public class ViewUtil {

	// <<<<<<<<<<<<<<<<<<<<<<<<<< view 构建相关操作 begin <<<<<<<<<<<<<<

	private static LayoutInflater inflater;

	public static View buildView(int layout) {
		return buildView(layout, null);
	}

	public static View buildView(int resource, ViewGroup root) {
		return buildView(resource, root, root != null);
	}

	/**
	 * 
	 * @Description: 创建视图
	 * @param resource
	 *            ID for an XML layout resource to load (e.g.,
	 *            R.layout.main_page)
	 * @param root
	 * @param attachToRoot
	 * @return
	 */
	public static View buildView(int resource, ViewGroup root,
			boolean attachToRoot) {
		return getInflater().inflate(resource, root, attachToRoot);
	}

	/**
	 * 创建一个listview里的view,并且可以设置高度
	 * 
	 * @param layoutId
	 * @param heightDip
	 * @return
	 */
	public static View buildListViewChildByPxHeight(int layoutId, int heightPx) {
		View view = buildView(layoutId);
		ListView.LayoutParams lp = new ListView.LayoutParams(
				ListView.LayoutParams.MATCH_PARENT, heightPx);
		view.setLayoutParams(lp);
		return view;
	}

	/**
	 * @return
	 */
	private static LayoutInflater getInflater() {
		if (null == inflater) {
			inflater = LayoutInflater.from(App.getApp());
		}
		return inflater;
	}

	public static ListView getListView(Context con) {
		ListView list = new ListView(con);
		return list;
	}

	private static TranslateAnimation animDown;
	private static TranslateAnimation animUp;

	public static TranslateAnimation getAnimDown(View v) {
		if (animDown == null) {
			animDown = new TranslateAnimation(0, 0, 0, v.getHeight());
			animDown.setDuration(300);
			animDown.setFillAfter(true);
		}
		return animDown;
	}

	public static TranslateAnimation getAnimUp(View v) {
		if (animUp == null) {
			animUp = new TranslateAnimation(0, 0, v.getHeight(), 0);
			animUp.setDuration(300);
			animUp.setFillAfter(true);
		}
		return animUp;
	}

	public static TextView getTipView(Context con, String tip) {
		TextView view = new TextView(con);
		view.setPadding(0, 15, 0, 15);
		view.setTextColor(Color.WHITE);
		view.setBackgroundColor(Color.argb(40, 74, 196, 247));
		view.setText(tip);
		view.setGravity(Gravity.CENTER);
		android.view.ViewGroup.LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);
		// view.setAnimation(animation);
		return view;
	}

	public static Bitmap convertViewToBitmap(View view) {

		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		return bitmap;
	}
}