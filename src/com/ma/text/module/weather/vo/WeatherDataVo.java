package com.ma.text.module.weather.vo;

import java.util.ArrayList;

import com.ma.text.base.BaseEntity;

@SuppressWarnings("serial")
public class WeatherDataVo extends BaseEntity {
	/**
	 * 温度数字:19
	 */
	private String wendu;
	/**
	 * 感冒指数，描述，提示
	 */
	private String ganmao;
	/**
	 * 昨日天气
	 */
	private WeatherYesterdayVo yesterday;
	
	private ArrayList<WeatherForecastVo> forecast;

	/**
	 * 不知道是啥，估计是城市代码
	 */
	private String aqi;

	/**
	 * 城市名称，苏州
	 */
	private String city;

	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public String getGanmao() {
		return ganmao;
	}

	public void setGanmao(String ganmao) {
		this.ganmao = ganmao;
	}

	public WeatherYesterdayVo getYesterday() {
		return yesterday;
	}

	public void setYesterday(WeatherYesterdayVo yesterday) {
		this.yesterday = yesterday;
	}

	public ArrayList<WeatherForecastVo> getForecast() {
		return forecast;
	}

	public void setForecast(ArrayList<WeatherForecastVo> forecast) {
		this.forecast = forecast;
	}

	public String getAqi() {
		return aqi;
	}

	public void setAqi(String aqi) {
		this.aqi = aqi;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}