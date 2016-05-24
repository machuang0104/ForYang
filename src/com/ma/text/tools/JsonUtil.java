package com.ma.text.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static double getDouble(JSONObject o, String keyName) {
		double value = 0;
		if (o != null && o.has(keyName)) {
			try {
				value = o.getDouble(keyName);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public static String getString(JSONObject o, String keyName) {
		String value = "";
		if (o != null && o.has(keyName)) {

			try {
				String v = o.getString(keyName);
				if (v != null && !"null".equals(v)) {
					return v;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return value;
			}
		}
		return value;
	}
	public static int getInt(JSONObject o, String keyName) {
		int value = -1;
		if (o != null && o.has(keyName)) {
			try {
				value = o.getInt(keyName);
			} catch (JSONException e) {
				e.printStackTrace();
				return value;
			}
		}
		return value;
	}

}
