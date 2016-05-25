package com.ma.text.module.weather.vo;

import com.ma.text.base.BaseEntity;

/**
 * 未来天气
 * @author  machuang
 *
 */
@SuppressWarnings("serial")
public class WeatherForecastVo extends BaseEntity{
	
	/**
	 * 东南风
	 */
	private String fengxiang;
	/**
	 * 3-4级
	 */
	private String fengli;
	/**
	 * 下雨情况：阵雨
	 */
	private String type;
	/**
	 * 高温 23℃
	 */
	private String high;
	/**
	 * 低温 19℃
	 */
	private String low;
	/**
	 * 28日星期六
	 */
	private String date;
	public String getFengxiang() {
		return fengxiang;
	}
	public void setFengxiang(String fengxiang) {
		this.fengxiang = fengxiang;
	}
	public String getFengli() {
		return fengli;
	}
	public void setFengli(String fengli) {
		this.fengli = fengli;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "WeatherItem [fengxiang=" + fengxiang + ", fengli=" + fengli
				+ ", type=" + type + ", high=" + high + ", low=" + low
				+ ", date=" + date + "]";
	}
	
}