package com.ma.text.compoment.dialoginput;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ma.text.R;

public class DialogBuilderInputImpl implements IDialogInputBuilder {

	private Dialog dialog;
	private TextView dialog_cancle;
	private TextView dialog_confirm;
	private EditText dialog_input;

	private InputListener okListener;

	private OnClickListener mLisener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.dialog_confirm) {
				if (okListener != null) {
					okListener.inputDone(dialog_input.getText().toString());
				}
			}
			dialog.dismiss();
		}
	};

	/**
	 * 构建一个原始的Dialog组件，一行文字，一个按钮
	 * 
	 * @param con
	 */
	public DialogBuilderInputImpl(Activity con) {
		WindowManager windowManager = (WindowManager) con
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		dialog = new Dialog(con, R.style.AlertDialogStyle);
		View view = LayoutInflater.from(con).inflate(
				R.layout.layout_dialog_input, null);
		// 获取自定义Dialog布局中的控件
		RelativeLayout lLayout_bg = (RelativeLayout) view
				.findViewById(R.id.lLayout_bg);
		dialog_cancle = (TextView) view.findViewById(R.id.dialog_cancle);
		dialog_confirm = (TextView) view.findViewById(R.id.dialog_confirm);
		dialog_input = (EditText) view.findViewById(R.id.dialog_input);

		// 定义Dialog布局和参数
		dialog.setContentView(view);
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));
		dialog_confirm.setOnClickListener(mLisener);
		dialog_cancle.setOnClickListener(mLisener);
	}

	/**
	 * 设置OK键监听器
	 */
	@Override
	public IDialogInputBuilder setInputListener(InputListener okListener) {
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

	@Override
	public IDialogInputBuilder setCancelble(boolean cancelable) {
		dialog.setCancelable(cancelable);
		return this;
	}
}