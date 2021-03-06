package com.ma.text.client.http.action;

import com.ma.text.client.http.global.ActionId;
import com.ma.text.module.weather.vo.WeatherStatusVo;
import com.ma.text.widget.http.MCallBack;
import com.ma.text.widget.http.MHttpUtil;
import com.ma.text.widget.http.MParams;

/**
 * @author machuang Date: 2016-4-16
 * 
 */
class ActionImpl implements IAction {
	private static ActionImpl task;

	public static ActionImpl getInstance() {
		if (task == null) {
			task = new ActionImpl();
		}
		return task;
	}

	private static MHttpUtil http;

	private static MHttpUtil getHttp() {
		if (http == null)
			http = new MHttpUtil();
		return http;
	}

	@Override
	public void login(String phoneNumber, String verifycode, double latitude,
			double longitude, MCallBack<String> callback) {
		MParams params = new MParams();
		params.addParam("phone", phoneNumber);
		params.addParam("verifyCode", verifycode);
		params.addParam("lat", String.valueOf(latitude));
		params.addParam("lon", String.valueOf(longitude));
		getHttp().post(ActionId.ACTION_TEST, params, callback);
	}

	@Override
	public void getWeather(String cityName, MCallBack<WeatherStatusVo> callBack) {
		getHttp().get(ActionId.WEATHER_DETAIL, null, cityName, callBack);
	}

}