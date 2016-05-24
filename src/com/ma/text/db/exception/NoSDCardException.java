package com.ma.text.db.exception;
/**
 * @Description: 无SD卡异常
 * @author: ethan.qiu@sosino.com
 * @date: 2013-7-30
 */
public class NoSDCardException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoSDCardException(){
		
	}
	
	public NoSDCardException(String detailMessage){
		super(detailMessage);
	}
	
	
}
