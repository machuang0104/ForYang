package com.ma.text.httpclient.action;

import com.lidroid.xutils.exception.HttpException;
import com.ma.text.httpclient.http.MCallBack;
import com.ma.text.module.weather.vo.WeatherStatusVo;

/**
 * 
 * 这一层用于业务校验，如登录密码是否为空等，校验通过后发起网络请求
 * 
 * 理论上每个Activity调用从这一层开始
 * 
 * @author machuang
 * 
 */
public class Task implements IAction {
	private static HttpException ex;

	private HttpException getException() {
		if (ex == null) {
			ex = new HttpException(-1);
		}
		return ex;
	}

	private static Task instance;

	public static Task getInstance() {
		if (instance == null) {
			instance = new Task();
		}
		return instance;
	}

	@Override
	public void login(String phone, String code, double latitude,
			double longitude, MCallBack<String> callBack) {
		ActionImpl.getInstance().login(phone, code, latitude, longitude,
				callBack);
	}

	@Override
	public void getWeather(String cityName,MCallBack<WeatherStatusVo> callBack) {
		ActionImpl.getInstance().getWeather(cityName, callBack);
	}

}