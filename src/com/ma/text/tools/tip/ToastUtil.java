package com.ma.text.tools.tip;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ma.text.R;
import com.ma.text.app.App;
/**
 * Toast工具
 * 
 */
public class ToastUtil {

	private static LayoutInflater inflater = null;
	private static View toastLayout = null;
	private static TextView text1 = null;
	private static Toast toast = null;
	private static Context context = null;
	private static Context getContext() {
		if (context == null)
			context = App.getApp();
		return context;
	}

	public static void show(int resID) {
		String str = null;
		try {
			str = getContext().getResources().getString(resID);
		} catch (Exception e) {
		}
		show(str);
	}

	public static void show(String msg) {
		try {
			synchronized (Toast.class) {
				if (toast != null) {
					setMsg(msg);
				} else {
					toast = new Toast(getContext());
					toast.setGravity(
							Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 150);
					toast.setDuration(Toast.LENGTH_SHORT);
					setMsg(msg);
					toast.setView(toastLayout);
				}
			}
			toast.show();
		} catch (Exception e) {
		}
	}

	public static void setMsg(String s) {
		if (text1 == null) {
			if (toastLayout == null) {
				if (inflater == null) {
					inflater = LayoutInflater.from(getContext());
				}
				toastLayout = inflater.inflate(R.layout.layout_toast, null);
			}
			text1 = (TextView) toastLayout.findViewById(R.id.toast_msg1);
		}
		text1.setText(s == null ? "" : "" + s);
	}
}
