package com.cars.simple.service.threadpool;
/*******************************************************************
 * 文件名称	: TaskHandle.java
 * 作	者	: fushangbin
 * 创建时间	: 
 * 文件描述	: 任务控制接口
 ******************************************************************/
public interface TaskHandle{
	/**
	 * 任务的初始状态
	 */
	public static final int TASK_STATE_INITIALIZE = 0;
	
	/**
	 * 任务正在任务队列中等待执行
	 */
	public static final int TASK_STATE_WAITING = 1;
	
	/**
	 * 任务正在执行
	 */
	public static final int TASK_STATE_RUNNING = 2;
	
	/**
	 * 任务执行完毕
	 */
	public static final int TASK_STATE_FINISHED = 3;
	
	/**
	 * 任务在任务队列中还没有被执行时被取消
	 */
	public static final int TASK_STATE_CANCEL = 4;
	
	/**
	 * 任务在任务队列中已经被执行时被取消
	 */
	public static final int TASK_STATE_CANCELRUNNING = 5;

	
	/*******************************************************************
	 * 函数名称	: cancel
	 * 函数描述	: 取消任务
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public boolean cancel();

	/*******************************************************************
	 * 函数名称	: obtainTaskObject
	 * 函数描述	: 获取任务对象
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: TaskObject
	 * 备注		: N/A
	 ******************************************************************/
	public TaskObject getTaskObject();
	
	/*******************************************************************
	 * 函数名称	: getState
	 * 函数描述	: 获取任务的状态
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: int
	 * 备注		: N/A
	 ******************************************************************/
	public int getState();

}
