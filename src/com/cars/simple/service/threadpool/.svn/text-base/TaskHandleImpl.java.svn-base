package com.cars.simple.service.threadpool;

import android.util.Log;

/*******************************************************************
 * 文件名称	: TaskHandleImpl.java
 * 作	者	: fushangbin
 * 创建时间	: 
 * 文件描述	: 任务控制接口实现类，类似于任务的句柄
 ******************************************************************/
public class TaskHandleImpl implements TaskHandle{
	
	private final String TAG = "==TaskHandleImpl==";
	/**
	 * 任务执行线程组管理对象
	 */
	private TaskQueue taskQueue = null;
	
	/**
	 * 任务控制对象所对应的任务对象
	 */
	private TaskObject taskObject = null;
	
	/**
	 * 执行当前任务的线程对象
	 */
	private TaskThread taskThread = null;
	
	/**
	 * 存储任务的当前状态变量
	 */
	private int state = TASK_STATE_INITIALIZE;
	
	/*******************************************************************
	 * 函数名称	: TaskHandleImpl
	 * 函数描述	: 构造函数
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: N/A
	 * 备注		: N/A
	 ******************************************************************/
	protected TaskHandleImpl(TaskQueue taskQueue, TaskObject taskObject) {
		super();
		this.taskQueue = taskQueue;
		this.taskObject = taskObject;
	}
	
	/*******************************************************************
	 * 函数名称	: cancel
	 * 函数描述	: 取消任务
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public boolean cancel()	{
		boolean ret = false;
		if (taskQueue == null){
			Log.i(TAG, "In TaskHandleImpl the taskQueue is  null.");
			return ret;
		}
		// 停止任务超时定时器，不然widgetadd任务会有泄漏
		if (taskObject != null){
			taskObject.stopTimeoutTimer();
		}
		Log.i(TAG, "the task state is " + state + "------------------");
		switch (state) {
			case TASK_STATE_WAITING: { // 任务还在排队等待执行
				ret = taskQueue.removeTask(this);
				taskObject.onCancelTask();
				break;
			}	
			case TASK_STATE_RUNNING: {// 任务已经获取线程资源正在执行
				ret = taskQueue.terminateTaskRunning(taskThread, this);
				taskObject.onCancelTask();
				ret = true;
				break;
			}
			case TASK_STATE_CANCEL: {// 任务在任务队列中时已经被取消
				ret = true;
				break;
			}
			case TASK_STATE_FINISHED: {// 任务已经执行完毕
				break;	
			}
			default:{
				break;
			}
		}
		// 释放任务对象，不然StartWidget会有内存泄漏
		taskObject = null;
		return ret;
	}
	
	/*******************************************************************
	 * 函数名称	: obtainTaskObject
	 * 函数描述	: 获取任务对象
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: TaskObject
	 * 备注		: N/A
	 ******************************************************************/
	public TaskObject getTaskObject(){
		return taskObject;
	}
	
	/*******************************************************************
	 * 函数名称	: getTaskThread
	 * 函数描述	: 获取执行任务的线程
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: TaskThread --- 当前正在执行该任务的线程
	 * 备注		: 只有在任务执行期间调用该方法有效
	 ******************************************************************/
	protected TaskThread getTaskThread(){
		return taskThread;
	}

	/*******************************************************************
	 * 函数名称	: setTaskThread
	 * 函数描述	: 设置用来执行该任务的线程
	 * 输入参数	: @param taskThread  ---- 执行该任务的线程对象
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	protected void setTaskThread(TaskThread taskThread) {
		this.taskThread = taskThread;
	}

	/*******************************************************************
	 * 函数名称	: setState
	 * 函数描述	: 设置任务的状态
	 * 输入参数	: @param state  ---- 要设置的状态码 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	protected void setState(int state) {
		this.state = state;
	}

	/*******************************************************************
	 * 函数名称	: getState
	 * 函数描述	: 获取任务的状态
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: int
	 * 备注		: N/A
	 ******************************************************************/
	public int getState() {
		return state;
	}

}
