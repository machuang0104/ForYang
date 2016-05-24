/**
 * Project Name:ParkingPass
 * File Name:State.java
 * Package Name:cn.com.parkingpass.common
 * Date:2015-9-28下午2:04:34
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.common;
/**
 * ClassName:State <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2015-9-28 下午2:04:34 <br/>
 * 
 * @author machuang
 */
public class KState {

	/** 预定中 */
	public static final int ORDER_ING = 1;
	/** 超时 */
	public static final int ORDER_TIME_OUT = 2;
	/** 取消预定 */
	public static final int ORDER_CANCLE = 3;
	/** 停车中未支付 */
	public static final int STOP_PAY_NOT = 4;
	/** 停车中已支付 */
	public static final int STOP_PAYED = 5;
	/** 停车结束未支付 */
	public static final int FINISH_PAY_NOT = 6;
	/** 停车结束已支付(有欠款) */
	public static final int FINISH_PAYED = 7;
	/** 完成 */
	public static final int FINISH = 8;
	/**
	 * getOrderState: 1.预定中 2.超时 3.取消预定 4.停车中未支付 5.停车中已支付 6.停车结束未支付 7.停车结束已支付(有欠款)
	 * 8.完成
	 */
	public static String getOrderState(int sta) {
		switch (sta) {
		case ORDER_ING :
			return "预订中";
		case ORDER_TIME_OUT :
			return "已失效";
		case ORDER_CANCLE :
			return "已失效";
		case STOP_PAY_NOT :
			return "停车中未支付";
		case STOP_PAYED :
			return "停车中已支付";
		case FINISH_PAY_NOT :
			return "停车结束未支付";
		case FINISH_PAYED :
			return "停车结束已支付（需补缴）";
		case FINISH :
			return "已完成";
		default :
			return "--";
		}
	}

	public static boolean isOrder(int coState) {
		if (coState == 6 || coState == 7) {
			return true;
		}
		return false;
	}
	public static boolean isHeZuo(int coState) {
		if (coState == 6 || coState == 7) {
			return true;
		}
		return false;
	}

	public static boolean isCollect(int collectState) {
		return collectState == K.code.TRUE;
	}

}
