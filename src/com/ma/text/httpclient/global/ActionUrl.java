package com.ma.text.httpclient.global;

/**
 * baseurl没有以/结尾，所以每个都要以/开头
 * 
 * @author machuang
 */
public class ActionUrl {
	/**
	 * 拟为按模块划分接口，否则接口太多，同一类文件过大
	 * 
	 * @author machuang
	 */
	public static final class ActionType {
		public static final int USER = 1;
		public static final int EVENT = 2;
	}

	/**
	 * 处理无参的url协议地址
	 */
	public static String getUrl(int actionId) {
		switch (actionId) {
		case ActionId.ACTION_TEST:
			return "/v1/ecif/user/login";
		default:
			return null;
		}
	}

	/**
	 * 处理含参的url协议地址，目前所有接口地址至多含一个参数
	 */
	public static String getUrl(int actionId, String urlParams) {
		switch (actionId) {
		case ActionId.WEATHER_DETAIL:
			// return "http://wthrcdn.etouch.cn/WeatherApi?city=" + urlParams;
			return "http://wthrcdn.etouch.cn/weather_mini?city=" + urlParams;
		default:
			return null;
		}
	}
}