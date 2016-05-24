/**
 * Project Name:ParkingPass
 * File Name:Response.java
 * Package Name:cn.com.parkingpass.entity
 * Date:2015-6-16下午5:00:26
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.vo;

import com.ma.text.base.BaseEntity;

/**
 * ClassName:Response <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2015-6-16 下午5:00:26 <br/>
 * 
 */
public class Response<T> extends BaseEntity {
	private static final long serialVersionUID = -6819971229033753411L;

	/** * response:requestTag */
	private String response;

	/** * result:result status */
	private int result;

	/** * sequence: i no know */
	private int sequence;

	/** * isEncrypted: */
	private int isEncrypted;

	private String message;

	private T data;

	private int taskId;

	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getIsEncrypted() {
		return isEncrypted;
	}
	public void setIsEncrypted(int isEncrypted) {
		this.isEncrypted = isEncrypted;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getTaskId() {
		return taskId;
	}
	@Override
	public String toString() {
		return "Response [response=" + response + ", result=" + result
				+ ", isEncrypted=" + isEncrypted + ", message=" + message + ", data="
				+ data + "]";
	}

}
