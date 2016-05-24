package com.ma.text.compoment.dialog;

import android.app.Activity;
import android.view.View.OnClickListener;

/**
 * 四个方法相互独立，创建四个类型dialog，可以换种写法
 * 
 * @author machuang
 * 
 */
public class DialogClient {

	/**
	 * 一行提示+一个按钮
	 */
	public static void showSingle(Activity con, String msg,
			final OnClickListener okListener) {
		DialogBuilderImpl builder = new DialogBuilderImpl(con);
		try {
			builder.buildContent(msg).setListener(okListener)
					.setTwoButton(false).getDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 两行提示+一个按钮
	 */
	public static void showSingle(Activity con, String title, String msg,
			final OnClickListener okListener) {
		DialogBuilderImpl builder = new DialogBuilderImpl(con);
		try {
			builder.buildTitle(title).buildContent(msg).setListener(okListener)
					.setTwoButton(false).getDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 两行提示+两个按钮
	 */
	public static void showTwo(Activity con, String title, String msg,
			final OnClickListener okListener) {
		DialogBuilderImpl builder = new DialogBuilderImpl(con);
		try {
			builder.buildTitle(title).buildContent(msg).setListener(okListener)
					.setTwoButton(true).getDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 一行提示+两个按钮
	 */
	public static void showTwo(Activity con, String msg,
			final OnClickListener okListener) {
		DialogBuilderImpl builder = new DialogBuilderImpl(con);
		try {
			builder.buildContent(msg).setListener(okListener)
					.setTwoButton(true).getDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 两行提示+两个按钮
	 */
	public static void showTwo(Activity con, String title, String msg,
			String txtOk, String txtCancle, final OnClickListener okListener) {
		DialogBuilderImpl builder = new DialogBuilderImpl(con);
		try {
			builder.buildTitle(title).buildContent(msg).setListener(okListener)
					.setTwoButton(true).setOkText(txtOk)
					.setCancelText(txtCancle).getDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}