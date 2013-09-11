package com.cars.simple.service.baseData;

/******************************************************************
 * 文件名称	: SDUnavailableException.java
 * 作    者	: fushangbin
 * 创建时间	: 2010-5-18 下午03:34:35
 * 文件描述	: sd卡不可用时抛出的异常
 ******************************************************************/
@SuppressWarnings("serial")
public class SDUnavailableException extends Exception{
	private String msg = null;
	
	/**
	 * 构造方法
	 * @param msg    异常信息
	 */
	public SDUnavailableException(String msg){
		this.msg = msg;
	}

	/**
	 * 重写的方法，获取异常信息
	 * @return    异常信息
	 */
	@Override
	public String getMessage()	{
		return msg;
	}
}
