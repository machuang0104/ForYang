package com.ma.text.httpclient.action;

import com.ma.text.httpclient.http.MCallBack;
import com.ma.text.module.weather.vo.WeatherStatusVo;

public interface IAction {
	
	/** * 保留demo */
	public abstract void login(String phoneNumber, String verifycode,
			double latitude, double longitude, MCallBack<String> callBack);


	/** * 获取事件详情 */
	public abstract void getWeather(String cityName,
			MCallBack<WeatherStatusVo> callBack);

}