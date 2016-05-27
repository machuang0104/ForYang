package com.ma.text.client.http.error;

import org.json.JSONObject;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.ma.text.tools.tip.ToastUtil;

public class HandleError {

	/**
	 * return：true-处理完毕，说明错误是服务器定义的；false-说明错误是其他处理不了的异常
	 */
	public static boolean showTip(HttpException e) {

		try {
			if (e.getResponseInfo() == null) {
				return false;
			}
			ResponseInfo<String> res = (ResponseInfo<String>) e
					.getResponseInfo();
			if (res != null && res.result != null) {
				String msg = new JSONObject(res.result).getString("msg");
				ToastUtil.show(msg);
				return true;
			} else {
				return false;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return false;
		}

	}
}