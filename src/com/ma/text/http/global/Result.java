package com.ma.text.http.global;

import com.ma.text.R;
import com.ma.text.app.App;
import com.ma.text.http.task.TaskId;

/**
 * response status codes of server
 */
/**
 * ClassName: Result date: 2015-7-10 涓婂崍9:42:27
 * 
 * @author machuang
 */
public class Result {

	/** * OK:sucess */
	public static final int SUCESS = 0;
	public static final int LOGIN_NO = -1;
	public static final int LOGIN_NOT = -2;
	public static final int ERROR_REQUEST = -3;

	public static String getMsg(int taskId, int code, String msg) {

		switch (taskId) {
		case TaskId.LOGIN:
			return getMsgLogin(code, msg);
		default:
			return getMsgBase(code, msg);
		}

	}

	private static String getMsgLogin(int code, String msg) {
		switch (code) {
		case 1:
			return getResource(R.string.error_login1);
		case 2:
			return getResource(R.string.error_login2);
		case 3:
			return getResource(R.string.error_login3);
		default:
			return getMsgBase(code, msg);
		}
	}


	private static String getMsgBase(int code, String msg) {
		switch (code) {
		case SUCESS:
			return getResource(R.string.result_sucess);
		case LOGIN_NO:
			return getResource(R.string.result_error_login);
		case LOGIN_NOT:
			return getResource(R.string.result_error_login);
		case ERROR_REQUEST:
			return getResource(R.string.result_error_request);
		default:
			return msg;
		}
	}

	private static String getResource(int resId) {
		String result = "";
		try {
			result = App.getApp().getString(resId);
		} catch (Exception e) {
		}
		return result;
	}
}