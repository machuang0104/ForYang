package com.ma.text.httpclient.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.ma.text.httpclient.global.ActionId;
import com.ma.text.httpclient.http.MResponseInfo;
import com.ma.text.httpclient.http.Response;
import com.ma.text.module.weather.vo.WeatherStatusVo;

/**
 * @Description: 将responseString转换成指定类型Entity，仍处于异步线程中
 */
@SuppressWarnings("rawtypes")
public class ActionDeal {

	public static Response getTaskResult(int actionId, MResponseInfo response) {
		String result = "{\"data\":" + response.responseString + "}";
		try {
			Response res;
			switch (actionId) {
			case ActionId.ACTION_TEST: {
				res = JSON.parseObject(result,
						new TypeReference<Response<String>>() {
						});
				break;
			}
			case ActionId.WEATHER_DETAIL: {
				res = JSON.parseObject(result,
						new TypeReference<Response<WeatherStatusVo>>() {
						});
				break;
			}
			default:
				res = JSON.parseObject(result,
						new TypeReference<Response<String>>() {
						});
				break;
			}
			res.setActionId(actionId);
			return res;
		} catch (JSONException e) {
			e.printStackTrace();
			Response<String> res = new Response<String>();
			// try {
			// JSONObject obj = new JSONObject(result);
			// res.setResponse(JsonUtil.getString(obj, K.req.RESPONSE));
			// res.setIsEncrypted(JsonUtil.getInt(obj, K.req.ISENCRYPT));
			// res.setMessage(JsonUtil.getString(obj, K.req.MESSAGE));
			// res.setResult(JsonUtil.getInt(obj, K.req.RESULT));
			// res.setSequence(JsonUtil.getInt(obj, K.req.SEQUENCE));
			// res.setData(JsonUtil.getString(obj, K.req.DATA));
			// } catch (org.json.JSONException e1) {
			// res.setResponse(response.responseString);
			// res.setData("");
			// res.setMessage("");
			// res.setResult(-2);
			// e1.printStackTrace();
			// }
			res.setActionId(actionId);
			return res;
		}
	}
}