package com.ma.text.widget.dialoginput;

import android.app.Activity;

/**
 * 四个方法相互独立，创建四个类型dialog，可以换种写法
 * 
 * @author machuang
 * 
 */
public class DialogInputClient {

	/**
	 * 一行提示+两个按钮
	 */
	public static void show(Activity con, final InputListener okListener) {
		DialogBuilderInputImpl builder = new DialogBuilderInputImpl(con);
		try {
			builder.setInputListener(okListener).getDialog().show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}