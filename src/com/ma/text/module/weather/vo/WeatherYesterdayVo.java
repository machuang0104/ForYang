package com.ma.text.module.weather.vo;

import com.ma.text.base.BaseEntity;

/**
 * 昨日天气
 * @author  machuang
 *
 */
@SuppressWarnings("serial")
public class WeatherYesterdayVo extends BaseEntity{
	
	/**
	 * 风力
	 */
	private String fl;
	
	/**
	 * 风向
	 */
	private String fx;
	
	/**
	 * 下雨情况
	 */
	private String type;
	/**
	 * 高温
	 */
	private String high;
	
	/**
	 * 低温
	 */
	private String low;
	
	/**
	 * 日期
	 */
	private String date;

	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public String getFx() {
		return fx;
	}

	public void setFx(String fx) {
		this.fx = fx;
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
	
}