package com.ma.text.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ClassName: DateUtil date: 2015-7-19 下午1:10:39
 * 
 * @author machuang
 */
public class DateUtil {
	/**
	 * formatYHDMS: yyyy-MM-dd HH:mm:ss
	 */
	public static String formatYHDMS(String str) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
				.format(str);
	}
	public static String formatYHDMS(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
				.format(date);
	}
	public static Date parseYHDMS(String str) {
		Date s = new Date();
		try {
			s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
					.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return s;
	}

	/**
	 * formatYHD: yyyy-MM-dd
	 */
	public static String formatYMD(Object str) {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(str);
	}

	/**
	 * formatHM: HH:mm
	 */
	public static String formatHM(Object str) {
		SimpleDateFormat HM = new SimpleDateFormat("HH:mm", Locale.getDefault());
		return HM.format(str);
	}
	/**
	 * formatHM: HH:mm
	 */
	public static String formatHMS(Object str) {
		SimpleDateFormat HM = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
		return HM.format(str);
	}

	/**
	 * getAlarmTime: 获取预订时间至当前的时间（单位：秒）
	 * 
	 * @param startTime
	 *          ：预订开始时间
	 */
	public static long getAlarmTime(String startTime) {
		if (startTime.length() < 19) {
			LogUtil.e(TAG, "startTime.Length<19");
			return -1;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
				Locale.getDefault());
		try {
			long dateTime = format.parse(startTime.toString()).getTime();

			if (dateTime <= System.currentTimeMillis()) {
				LogUtil.e(TAG, "dateTime <currentTimeMillisTime ");
				return -1;
			}
			return dateTime;
		} catch (ParseException e) {
			LogUtil.e(TAG, "TimeException = " + e.getMessage());
			return -1;
		}
	}
	private final static String TAG = "DateUtil";
}
