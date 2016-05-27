package com.ma.text.client.http.global;

import com.lidroid.xutils.exception.HttpException;
import com.ma.text.R;
import com.ma.text.tools.ResUtil;

/**
 * Date: 2016-4-21
 * 
 * @author machuang
 */
public class ErrorMy {

	public static String getExcMsg(HttpException ex) {
		int errorCode = ex.getExceptionCode();
		if (errorCode == 0) {
			errorCode = getCode(ex.getMessage());
		}
		switch (errorCode) {
		case Code.FAIL_HOST:
			return ResUtil.getStr(R.string.tip_network_weak);
		case Code.FAIL_TIMEOUT:
			return ResUtil.getStr(R.string.tip_network_weak);
		case Code.FAIL_TIMEOUT2:
			return ResUtil.getStr(R.string.tip_network_weak);
		case 400:
			return "请求服务器失败:400";
		case 403:
			return "服务器拒绝访问:403";
		case 404:
			return "请求未找到:404";
		case 405:
			return "请求失败:405";
		case 406:
			return "请求响应失败:406";
		case 408:
			return ResUtil.getStr(R.string.tip_network_weak);
		case 409:
			return "请求资源冲突:409";
		case 410:
			return "请求不可用:410";
		case 413:
			return "服务器拒绝访问:413";
		case 414:
			return "请求url过长:414";
		case 415:
			return "请求格式不支持:415";
		case 421:
			return "当前链接数过多:421";
		case 422:
			return "请求语法错误:422";
		case 423:
			return "当前资源被锁定:423";
		case 500:
			return "服务器内部错误:500";
		case 501:
			return "服务器连接失败:501";
		case 502:
			return "服务器连接失败:502";
		case 503:
			return "服务器连接失败:503";
		case 504:
			return "服务器连接失败:504";
		case 505:
			return "服务器连接失败:505";
		case 506:
			return "服务器连接失败:506";
		case 507:
			return "服务器连接失败:507";
		case 508:
			return "服务器连接失败:508";
		case 509:
			return "服务器连接失败:509";
		case 510:
			return "服务器连接失败:510";
		case Code.FAIL_NET_DEAD:
			return ResUtil.getStr(R.string.tip_no_network);
		case 0:
			return ResUtil.getStr(R.string.tip_network_weak);
		default:
			return ResUtil.getStr(R.string.tip_network_weak);
		}
	}

	public static class Code {
		public static final int FAIL_HOST = 901;
		public static final int FAIL_TIMEOUT = 902;
		public static final int FAIL_CONNECT = 903;
		public static final int FAIL_TIMEOUT2 = 904;
		public static final int FAIL_NET_DEAD = 999;
	}

	public static int getCode(String eMsg) {
		if (eMsg.contains(Str.FAIL_HOST)) {
			return Code.FAIL_HOST;
		} else if (eMsg.contains(Str.FAIL_TIMEOUT)) {
			return Code.FAIL_TIMEOUT;
		} else if (eMsg.contains(Str.FAIL_TIMEOUT2)) {
			return Code.FAIL_TIMEOUT2;
		} else if (eMsg.contains(Str.FAIL_CONNECT)) {
			return Code.FAIL_CONNECT;
		}
		return 0;
	}

	private static class Str {
		public static final String FAIL_HOST = "UnknownHostException";
		public static final String FAIL_TIMEOUT = "SocketTimeoutException";
		public static final String FAIL_CONNECT = "HttpHostConnectException";
		public static final String FAIL_TIMEOUT2 = "ConnectTimeoutException";

	}
}
