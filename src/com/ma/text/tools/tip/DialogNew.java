/**
 * Project Name:ParkingPass
 * File Name:DialogUtil.java
 * Package Name:cn.com.parkingpass.tools
 * Date:2015-6-4上午11:07:35
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.tools.tip;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ma.text.R;

/**
 * ClassName:DialogUtil <br/>
 * 
 * @author machuang
 */
public class DialogNew {
	public static void showSingle(Activity con, String title, String msg,
			final OnClickListener okListener) {
		show(con, title, msg, okListener, true);
	}
	public static void show(Activity con, String title, String msg,
			final OnClickListener okListener) {
		show(con, title, msg, okListener, false);
	}
	public static void show(Activity con, String msg,
			final OnClickListener okListener) {
		show(con, null, msg, okListener, false);
	}

	private static void show(Activity con, String title, String msg,
			final OnClickListener okListener, boolean single) {

		WindowManager windowManager = (WindowManager) con
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		View view = LayoutInflater.from(con).inflate(R.layout.layout_dialog, null);
		// 获取自定义Dialog布局中的控件
		RelativeLayout lLayout_bg = (RelativeLayout) view
				.findViewById(R.id.lLayout_bg);
		TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title);
		TextView dialog_msg = (TextView) view.findViewById(R.id.dialog_msg);
		TextView dialog_cancle = (TextView) view.findViewById(R.id.dialog_cancle);
		TextView dialog_confirm = (TextView) view.findViewById(R.id.dialog_confirm);

		if (title != null)
			dialog_title.setText(title);
		else
			dialog_title.setVisibility(View.GONE);
		if (msg != null)
			dialog_msg.setText(msg);
		else
			dialog_msg.setVisibility(View.GONE);
		// 定义Dialog布局和参数
		final Dialog dialog = new Dialog(con, R.style.AlertDialogStyle);
		dialog.setContentView(view);

		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

		dialog_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (okListener != null)
					okListener.onClick(v);
				dialog.dismiss();
			}
		});

		if (single) {
			dialog_cancle.setVisibility(View.GONE);
			dialog_confirm.setBackgroundResource(R.drawable.selector_dialog_single);
		}
		dialog_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		try {
			dialog.show();
		} catch (Exception e) {
			con.finish();
			e.printStackTrace();
		}
	}
}
