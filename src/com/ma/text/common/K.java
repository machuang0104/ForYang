/**
 * Project Name:ParkingPass
 * File Name:Key.java
 * Package Name:cn.com.parkingpass.common
 * Date:2015-7-17下午3:07:53
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.common;

/**
 * ClassName: K Function: 定义各种常量
 */
public class K {
	
	public static final class cache{
		public static final String SHARED_NAME = "user_cache";
	}

	/**
	 * ClassName: param Function: 协议中参数的key
	 */
	public static final class param {
		public static final String DATA = "data";
		public static final String HEAD_SESSIONKEY = "sessionKey";
		public static final String HEAD_REQUEST = "request";
		public static final String HEAD_SEQUENCE = "sequence";
		public static final String HEAD_ISENCRYPT = "isEncrypted";
		public static final String HEAD_OSVERSION = "OSVersion";
		public static final String HEAD_OS = "OS";
		public static final String HEAD_OS_VALUE = "android";
		public static final String HEAD_CLIENT = "client";
		public static final String HEAD_CLIENT_VALUE = "parkManager";
		public static final String HEAD_VERSION = "version";
		public static final String HEAD_VERSION_VALUE = "1.0";
		public static final String HEAD_DEVICEID = "deviceID";
		public static final String HEAD_RESPONSE = "response";
		public static final String HEAD_RESULT = "result";
		public static final String HEAD_MESSAGE = "message";

	}

	/**
	 * ClassName: share Function: SharedPreferences使用的常量
	 */
	public static final class share {
		public static final String USER_IMG_STR = "headImg_bitMap_str";
		public static final String USER_IMG_URL = "headImg_url_str";
		public static final String NEED_REFRESH = "needRefresh";
	}

	/**
	 * ClassName: file Function: 文件名、路径之类的常量
	 */
	public static final class file {
		public static final String ENCODE_TYPE = "UTF-8";
		public static final String ROOT_NAME = "/mydarling";
		public static final String FILE_PATH = ROOT_NAME + "/file/";
		public static final String CACHE = ROOT_NAME + "/images/cache/";
		public static final String CRASHLOG = ROOT_NAME + "/crash/";
		public static final String SCREENSHOTS = ROOT_NAME
				+ "/images/screenshots/";
	}

	public static final class code {
		public static final int TRUE = 1;
		public static final int FALSE = 0;

	}

	public static final class intent {
		public static final String NEED_SHOW = "need_show";
		public static final String NEED_RESULT = "need_result_str";

	}
	public static final class TAG {
		/** 临时TAG */
		public static final String TAG_COMMON = "tag_common";

		/** activity生命周期 Tag */
		public static final String TAG_ACTIVITY = "tag_activity";

		/** fragment生命周期 TAG */
		public static final String TAG_FRAGMENT = "tag_fragment";

		/** 数据库 */
		public static final String TAG_DB = "tag_db";

		/** 广播接收器 */
		public static final String TAG_BROADCAST = "tag_broadcast";

		/** 所有http请求的跟踪 */
		public static final String TAG_HTTP = "tag_http";

		/** 所有socket请求的跟踪 */
		public static final String TAG_SOCKET = "tag_socket";

		/** 所有事件请求跟踪 */
		public static final String TAG_EVENT = "tag_event";

		/** 串口跟踪 */
		public static final String TAG_SER_PORT = "tag_ser_port";
	}
}