package com.ma.text.widget.dialog;

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

public class DialogBuilderImpl implements IDialogBuilder {

	private Dialog dialog;
	private TextView dialog_title;
	private TextView dialog_msg;
	private TextView dialog_cancle;
	private TextView dialog_confirm;

	private OnClickListener okListener;

	private OnClickListener mLisener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			if (v.getId() == R.id.dialog_confirm && okListener != null)
				okListener.onClick(v);
		}
	};

	/**
	 * 构建一个原始的Dialog组件，一行文字，一个按钮
	 * 
	 * @param con
	 */
	public DialogBuilderImpl(Activity con) {
		WindowManager windowManager = (WindowManager) con
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		dialog = new Dialog(con, R.style.AlertDialogStyle);
		View view = LayoutInflater.from(con).inflate(R.layout.layout_dialog,
				null);
		// 获取自定义Dialog布局中的控件
		RelativeLayout lLayout_bg = (RelativeLayout) view
				.findViewById(R.id.lLayout_bg);
		dialog_title = (TextView) view.findViewById(R.id.dialog_title);
		dialog_msg = (TextView) view.findViewById(R.id.dialog_msg);
		dialog_cancle = (TextView) view.findViewById(R.id.dialog_cancle);
		dialog_confirm = (TextView) view.findViewById(R.id.dialog_confirm);

		// 定义Dialog布局和参数
		dialog.setContentView(view);
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
		dialog_confirm.setOnClickListener(mLisener);
	}

	/**
	 * 设置标题并显示
	 */
	@Override
	public IDialogBuilder buildTitle(String title) {
		dialog_title.setVisibility(View.VISIBLE);
		dialog_title.setText(title);
		return this;
	}

	/**
	 * 设置内容
	 */
	@Override
	public IDialogBuilder buildContent(String content) {
		dialog_msg.setText(content);
		return this;
	}

	/**
	 * 设置OK键监听器
	 */
	@Override
	public IDialogBuilder setListener(OnClickListener okListener) {
		this.okListener = okListener;
		return this;
	}

	/**
	 * 获取构建的Dialo实例
	 */
	@Override
	public Dialog getDialog() {
		return dialog;
	}

	/**
	 * 设置是否显示取消键（默认单个）
	 */
	@Override
	public IDialogBuilder setTwoButton(boolean two) {
		if (two) {
			dialog_cancle.setVisibility(View.VISIBLE);
			dialog_confirm
					.setBackgroundResource(R.drawable.selector_dialog_right);
			dialog_cancle.setOnClickListener(mLisener);
		}
		return this;
	}

	@Override
	public IDialogBuilder setOkText(String okStr) {
		dialog_confirm.setText(okStr);
		return this;
	}

	@Override
	public IDialogBuilder setCancelText(String cancleStr) {
		dialog_cancle.setText(cancleStr);
		return this;
	}

	@Override
	public IDialogBuilder setCancelble(boolean cancelable) {
		dialog.setCancelable(cancelable);
		return this;
	}
}