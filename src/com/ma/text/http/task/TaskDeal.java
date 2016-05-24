package com.ma.text.http.task;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.lidroid.xutils.http.ResponseInfo;
import com.ma.text.common.K;
import com.ma.text.tools.JsonUtil;
import com.ma.text.vo.Response;
import com.ma.text.vo.db.TestVo;

/**
 * @Description: 将responseString转换成指定类型Entity，仍处于异步线程中
 */
@SuppressWarnings("rawtypes")
public class TaskDeal {

	public static Response getTaskResult(int taskId, ResponseInfo response) {
		String result = response.responseString;
		try {
			Response res;
			switch (taskId) {
			case TaskId.LOGIN:
				res = JSON.parseObject(result,
						new TypeReference<Response<TestVo>>() {
						});
				break;
			default:
				res = JSON.parseObject(result,
						new TypeReference<Response<String>>() {
						});
				break;
			}
			res.setTaskId(taskId);
			return res;
		} catch (JSONException e) {
			e.printStackTrace();
			Response<String> res = new Response<String>();
			try {
				JSONObject obj = new JSONObject(result);
				res.setResult(JsonUtil.getInt(obj, K.param.HEAD_RESULT));
				res.setResponse(JsonUtil.getString(obj, K.param.HEAD_RESPONSE));
				res.setSequence(JsonUtil.getInt(obj, K.param.HEAD_SEQUENCE));
				res.setMessage(JsonUtil.getString(obj, K.param.HEAD_MESSAGE));
				res.setData(JsonUtil.getString(obj, K.param.DATA));
			} catch (org.json.JSONException e1) {
				res.setResponse(response.responseString);
				res.setData("");
				res.setMessage("");
				res.setResult(-2);
				e1.printStackTrace();
			}
			res.setTaskId(taskId);
			return res;
		}
	}
}