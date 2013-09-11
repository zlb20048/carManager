package com.cars.simple.service.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.cars.simple.service.request.Request;
import com.cars.simple.service.threadpool.TaskQueue;

/************************************************************************************************
 * 文件名称: ConnectionLogic.java
 * 文件描述: 网络请求的统一入口，由线程池来完成请求的发送和管理
 * 作          者： fushangbin
 * 创建时间: 2011-11-16下午12:34:41
 * 备          注:
 ************************************************************************************************/
public class ConnectionLogic {
	/**
	 * 单实例连接处理对象
	 */
	private static ConnectionLogic instance;

	/**
	 * 连接队列
	 */
	private TaskQueue requestQueue;

	/**
	 * 最大任务请求数
	 */
	private static final int maxCount = 4;

	/**
	 * 私有构造函数
	 */
	private ConnectionLogic(){
		requestQueue = new TaskQueue(maxCount);
	}

	/**
	 * 单实例对象
	 * @return      单实例连接处理对象
	 */
	public synchronized static ConnectionLogic getInstance(){
		if (instance == null){
			instance = new ConnectionLogic();
		}
		return instance;
	}

	/**
	 * 增加请求任务
	 * @param req      请求任务
	 */
	public void addRequest(ConnectionTask req){
		if (requestQueue != null){
			requestQueue.addTask(req);
		}
	}
	
	/****************************************************************************************
	 * 函数名称：cancelRequest
	 * 函数描述：取消页面相关的所有请求
	 * 输入参数：@param map
	 * 输出参数：
	 * 返回    值：void
	 * 备         注：
	 ****************************************************************************************/
	public void cancelRequest(HashMap<String, Request> map){
		if (requestQueue != null){
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext()){
				String key = it.next();
				Request request = map.get(key);
				request.ct.onCancelTask();
			}
			requestQueue.cancelTaskByManual(map);
			map.clear();
		}
	}
}
