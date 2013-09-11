package com.cars.simple.service.http;

/*******************************************************************
 * 文件名称 : HttpListener.java
 * 作          者 : fushangbin
 * 创建时间 : 
 * 文件描述 : 联网回调接口
 ******************************************************************/
public interface IHttpListener{

	/**
	 * 下载失败回调函数
	 * @param code     状态码
	 * @param message  错误消息
	 */
	public void onError(int code, String message);

	/**
	 * 设置进度变化回调函数
	 * @param getLength      下载大小
	 * @param totalLength    文件大小
	 */
	public void onProgressChanged(long getLength, long totalLength);

	/**
	 * 用户取消回调 函数
	 */
	public void canceledCallback();

	/**
	 * 用户取消回调 函数
	 */
	public void pausedCallback();

	/**
	 * 开始下载任务通知回调
	 */
	public void startDownloadCallback();

	/**
	 * 下载完成回调通知接口
	 */
	public void successDownloadCallback();

}