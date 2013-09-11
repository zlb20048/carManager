package com.cars.simple.service.threadpool;

import android.util.Log;

/*******************************************************************
 * 文件名称	: TaskThread.java
 * 作	者	: fushangbin
 * 创建时间	: 
 * 文件描述	: 线程对象实现类
 * 修改历史	: 
 ******************************************************************/
public class TaskThread extends Thread{
	
	private final String TAG = "==TaskThread==";
	/**
	 * 在线程中执行的任务对象
	 */
	private TaskQueue taskQueue = null;
	
	/**
	 * 调用interrupt()时是否需要终止线程，如果用户取消任务或任务超时不需要终止线程
	 */
	private boolean isTerminate = false;

	/*******************************************************************
	 * 函数名称	: TaskThread
	 * 函数描述	: 构造函数
	 * 输入参数	: taskQueue  ---- 线程所在的线程池对象 
	 * 输出参数	: void
	 * 返回值  	: N/A
	 * 备注		: N/A
	 ******************************************************************/
	protected TaskThread(TaskQueue taskQueue)	{
		super();
		this.taskQueue = taskQueue;
	}
	
	/*******************************************************************
	 * 函数名称	: run
	 * 函数描述	: 线程执行体函数
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void run()	{
		// 增加空闲线程的计数
		taskQueue.increaseIdleCount();
		while (true){
			TaskHandleImpl task = null;
			try {
				task = taskQueue.obtainTask(this);
			}catch (InterruptedException e) {
				Log.e(TAG, "Exception occur while obtainTask ：" + e.toString());
				break;
			}
			if (task != null){
				// 减小空闲线程的计数
				taskQueue.decreaseIdleCount();
				// 执行任务
				try{
					TaskObject object = task.getTaskObject();
					if (object != null){
						object.runTask();
						// 设置任务的状态为执行完毕状态
						task.setState(TaskHandleImpl.TASK_STATE_FINISHED);
						// 增加空闲线程的计数
						taskQueue.increaseIdleCount();
						// 通知任务请求者任务执行完毕
						object.onTaskResponse(TaskObject.RESPONSE_SUCCESS);
						task.cancel();
						task = null;
					}
				}catch (InterruptedException e){
					Log.e(TAG, "While task running , user terminate the task ：" + e.toString());
					if (isTerminate()){
						break;
					}else{
						// 增加空闲线程的计数
						taskQueue.increaseIdleCount();
						continue;
					}
				}
			}else {
				break;
			}
		}
		// 减小空闲线程的计数
		taskQueue.decreaseIdleCount();
		// 从线程队列中删除线程
		taskQueue.deleteThread(this);
		Log.i(TAG, "Thread is terminate ！");
	}

	/*******************************************************************
	 * 函数名称	: setTerminate
	 * 函数描述	: 调用interrupt()时是否需要终止线程
	 * 输入参数	: @param isTerminate 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void setTerminate(boolean isTerminate){
		synchronized (this){
			this.isTerminate = isTerminate;
		}
	}
	
	/*******************************************************************
	 * 函数名称	: isTerminate
	 * 函数描述	: 判断是否需要终止线程
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: boolean
	 * 备注		: N/A
	 ******************************************************************/
	public synchronized boolean isTerminate(){
		return isTerminate;
	}
}
