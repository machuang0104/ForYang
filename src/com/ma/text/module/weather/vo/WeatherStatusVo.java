package com.ma.text.module.weather.vo;

/**
 * 天气json root
 * 
 * @author machuang
 * 
 */
public class WeatherStatusVo {
	/**
	 * OK
	 */
	private String desc;
	/**
	 * 1000
	 */
	private int status;
	private WeatherDataVo data;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public WeatherDataVo getData() {
		return data;
	}

	public void setData(WeatherDataVo data) {
		this.data = data;
	}

}