package com.cars.simple.service.threadpool;
import java.util.TimerTask;

/*******************************************************************
 * 文件名称	: TimeoutTask.java
 * 作	 者	: fushangbin
 * 创建时间	: 
 * 文件描述	: 用来处理任务请求超时的定时器任务类
 ******************************************************************/
public class TimeoutTask extends TimerTask{
	/**
	 * 当前的线程管理对象
	 */
	private TaskQueue taskQueue = null;
	
	/**
	 * 定时器所监控的线程任务请求
	 */
	private TaskHandleImpl taskHandle = null;
	
	/*******************************************************************
	 * 函数名称	: TaskTimeOut
	 * 函数描述	: 构造函数
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: N/A
	 * 备注		: N/A
	 ******************************************************************/
	protected TimeoutTask(TaskQueue taskQueue, TaskHandleImpl taskHandle) {
		super();
		this.taskQueue = taskQueue;
		this.taskHandle = taskHandle;
	}
	
	/*******************************************************************
	 * 函数名称	: run
	 * 函数描述	: 实现定时器任务执行体方法
	 * 输入参数	: N/A 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void run(){
		if (taskHandle == null || taskQueue == null){
			return;
		}
		switch (taskHandle.getState()) {
			case TaskHandleImpl.TASK_STATE_WAITING:{
				// 从任务队列中删除任务
				taskQueue.removeTask(taskHandle);
				// 将任务超时消息通知任务请求者
				taskHandle.getTaskObject().onTaskResponse(TaskObject.RESPONSE_TIMEOUT_ONRUN);
				break;
			}	
			case TaskHandleImpl.TASK_STATE_RUNNING:{
				// 终止执行任务的线程
				taskQueue.terminateTaskRunning(taskHandle.getTaskThread(), taskHandle);
				// 将任务超时消息通知任务请求者
				taskHandle.getTaskObject().onTaskResponse(TaskObject.RESPONSE_TIMEOUT_RUNNING);
				break;
			}
			default:{
				break;
			}
		}
	}

}
