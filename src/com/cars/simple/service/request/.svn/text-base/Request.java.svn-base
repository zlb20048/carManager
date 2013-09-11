package com.cars.simple.service.request;

import android.os.Handler;
import android.util.Log;

import com.cars.simple.fusion.FusionCode;
import com.cars.simple.logic.MainManager;
import com.cars.simple.service.http.ConnectionLogic;
import com.cars.simple.service.http.ConnectionTask;
import com.cars.simple.service.http.IHttpListener;
import com.cars.simple.service.http.IStatusListener;

/************************************************************************************************
 * 文件名称: Request.java 文件描述: http请求类的封装 作 者： lzx 创建时间: 2011-11-16下午12:36:23 备 注:
 ************************************************************************************************/
public class Request implements IStatusListener {
	private final String TAG = "=====Request====";

	public String interfaceUrl;

	/**
	 * 回调句柄
	 */
	private Handler handler;

	/**
	 * 请求任务对象
	 */
	public ConnectionTask ct;

	public Request() {

	}

	public Request(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	/****************************************************************************************
	 * 函数名称：sendPostRequest 函数描述：以post 方式发送请求，返回的数据格式是json 输入参数：@param body
	 * 消息体内容 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	public void sendPostRequest(String body, int time) {
		if (handler == null) {
			return;
		}
		ct = new ConnectionTask(this, FusionCode.MAX_CONNECTION_TIMEOUT,
				handler);
		ct.setTimer(MainManager.timer);
		ct.setHttpUrl(interfaceUrl);
		byte[] data = null;
		data = body.getBytes();
		ct.setRequestType(ConnectionTask.POST);
		ct.setDataBuf(data);
		ct.setTimeStamp(time);
		ConnectionLogic.getInstance().addRequest(ct);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param url
	 *            请求地址
	 */
	public void sendGetRequest(String url) {
		if (null == handler) {
			return;
		}
		ct = new ConnectionTask(this, FusionCode.MAX_CONNECTION_TIMEOUT,
				handler);
		ct.setTimer(MainManager.timer);
		ct.setHttpUrl(url);
		ct.setRequestType(ConnectionTask.GET);
		ConnectionLogic.getInstance().addRequest(ct);
	}

	/****************************************************************************************
	 * 函数名称：sendUpdateRequest 函数描述：发送检测版本信息的请求,返回的数据格式是byte数组 输入参数：@param data
	 * 输出参数： 返回 值：void 备 注：
	 ****************************************************************************************/
	public void sendUpdateRequest(byte[] data, int time) {
		if (handler == null) {
			return;
		}
		ct = new ConnectionTask(this, FusionCode.MAX_CONNECTION_TIMEOUT,
				handler, ConnectionTask.CONNECT_TYPE_BYTES);
		ct.setTimer(MainManager.timer);
		ct.setHttpUrl(interfaceUrl);
		ct.setRequestType(ConnectionTask.POST);
		ct.contentType = FusionCode.HTTP_CONTENT_TYPE_OCTET_STREAM;
		Log.i(TAG, "data" + new String(data));
		ct.setDataBuf(data);
		ct.setTimeStamp(time);
		ConnectionLogic.getInstance().addRequest(ct);
	}

	/**
	 * 获取到图片
	 * 
	 * @param url
	 *            URL 地址
	 */
	public void sendGetByteRequest(String url) {
		if (handler == null) {
			return;
		}
		ct = new ConnectionTask(this, FusionCode.MAX_CONNECTION_TIMEOUT,
				handler, ConnectionTask.CONNECT_TYPE_BYTES);
		ct.setTimer(MainManager.timer);
		ct.setHttpUrl(url);
		ct.setRequestType(ConnectionTask.GET);
		ct.contentType = FusionCode.HTTP_CONTENT_TYPE_OCTET_STREAM;
		ConnectionLogic.getInstance().addRequest(ct);
	}

	/**
	 * 获取值
	 * 
	 * @param url
	 *            地址
	 * @param body
	 *            post数据
	 */
	public void sendPostByteRequest(String url, String body) {
		if (handler == null) {
			return;
		}
		ct = new ConnectionTask(this, FusionCode.MAX_CONNECTION_TIMEOUT,
				handler, ConnectionTask.CONNECT_TYPE_BYTES);
		ct.setTimer(MainManager.timer);
		ct.setHttpUrl(url);
		byte[] data = null;
		data = body.getBytes();
		ct.setRequestType(ConnectionTask.POST);
		ct.setDataBuf(data);
		ConnectionLogic.getInstance().addRequest(ct);
	}

	/****************************************************************************************
	 * 函数名称：sendDownloadRequest 函数描述：发送下载文件的请求 输入参数：@param httpListener 输出参数： 返回
	 * 值：void 备 注：
	 ****************************************************************************************/
	public void sendDownloadRequest(IHttpListener httpListener, String savePath) {
		ct = new ConnectionTask(httpListener, interfaceUrl, 5 * 60 * 1000);
		ct.setTimer(MainManager.timer);
		ct.oldUrl = savePath;
		Log.i("sendDownloadRequest", "savePath=" + savePath);
		ConnectionLogic.getInstance().addRequest(ct);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void onConnError(int code, String message) {
		if (this.handler != null) {
			handler.sendMessage(handler.obtainMessage(FusionCode.NETWORK_ERROR,
					message));
		}
	}

	@Override
	public void onTimeOut(int code, String message) {
		if (this.handler != null) {
			handler.sendMessage(handler.obtainMessage(
					FusionCode.NETWORK_TIMEOUT_ERROR, message));
		}
	}

}
