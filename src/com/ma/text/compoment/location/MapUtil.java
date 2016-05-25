package com.ma.text.compoment.location;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

/**
 * 获取经纬度
 */
public class MapUtil {

	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;

	private static MapUtil client;

	public static MapUtil getIns() {
		if (client == null) {
			client = new MapUtil();
		}
		return client;
	}

	/**
	 * 启动定位
	 * 
	 * @param context
	 */
	public void start(Context context, AMapLocationListener listener) {
		// if (null==locationClient) {
		locationClient = new AMapLocationClient(context);
		// }
		if (null == locationOption) {
			locationOption = new AMapLocationClientOption();
		}
		locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		locationOption.setOnceLocation(false);
		locationOption.setMockEnable(false);
		locationOption.setNeedAddress(false);
		locationOption.setInterval(1000);
		locationOption.setOnceLocation(true);
		locationClient.setLocationOption(locationOption);
		locationClient.setLocationListener(listener);
		locationClient.startLocation();
	}

	/**
	 * 销毁定位
	 */
	public void destroy() {
		if (null != locationClient) {
			locationClient.stopLocation();
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}
}
