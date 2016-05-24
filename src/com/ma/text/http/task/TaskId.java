package com.ma.text.http.task;

/**
 * @Description: 接口Id
 */
public class TaskId {

	public static final int LOGIN = 1;

	public static String getTaskReq(int taskId) {
		switch (taskId) {
		case LOGIN:
			return "parkManagerLogin";
		default:
			return "testGetCode";
		}
	}

}