package com.ma.text.client.http.action;

import com.ma.text.module.weather.vo.WeatherStatusVo;
import com.ma.text.widget.http.MCallBack;

public interface IAction {
	
	/** * 保留demo */
	public abstract void login(String phoneNumber, String verifycode,
			double latitude, double longitude, MCallBack<String> callBack);


	/** * 获取事件详情 */
	public abstract void getWeather(String cityName,
			MCallBack<WeatherStatusVo> callBack);

}