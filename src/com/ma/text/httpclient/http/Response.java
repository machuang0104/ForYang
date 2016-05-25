package com.ma.text.httpclient.http;

import com.ma.text.base.BaseEntity;

/**
 * ClassName:Response <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2016-5-16 下午5:00:26 <br/>
 * 
 * @author machuang
 * 
 */
public class Response<T> extends BaseEntity {
	private static final long serialVersionUID = -6819971229033753411L;

	private T data;
	private int actionId;

	public int getAciontId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Response [response=" + data + ", taskId=" + actionId + "]";
	}

}