package com.ma.text.tools;

import com.ma.text.App;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * 
 * @author libin
 * 
 */
public class DeviceUtil {

	/**
	 * sdk 版本
	 * 
	 * @return
	 */
	public static int getSDKVersion() {
		return android.os.Build.VERSION.SDK_INT;
	}
	public static String getPhoneIMEI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

	}
	/**
	 * 获取手机名称
	 * 
	 * @return
	 */
	public static String getPhoneName() {
		 return android.os.Build.PRODUCT;
	}
	/**
	 * 获取序列号
	 */
	public static String getSerNumber() {
		TelephonyManager TelephonyMgr = (TelephonyManager) App
				.getApp().getSystemService(Context.TELEPHONY_SERVICE);
		String szImei = TelephonyMgr.getDeviceId();
		if (szImei != null) {
			return szImei;
		} else
			return "000000000000";
	}
}
