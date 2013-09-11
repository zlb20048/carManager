package com.cars.simple.service.threadpool;

import java.util.HashMap;
import java.util.Vector;

import android.util.Log;

import com.cars.simple.service.request.Request;
import com.cars.simple.util.List;

/*******************************************************************
 * 文件名称	: TaskQueue.java
 * 作	者	: fushangbin
 * 创建时间	: 
 * 文件描述	: 线程池实现类
 * 修改历史	: 
 ******************************************************************/
public class TaskQueue{
	
	private final String TAG = "==TaskQueue==";
	
	/**
	 * 当前的任务队列
	 */
	private List<TaskHandleImpl> tasks = null;

	/**
	 * 存储线程池中的所有的线程
	 */
	private List<TaskThread> threads = null;

	/**
	 * 记录当前所有线程的数目
	 */
	private int threadCount = 0;

	/**
	 * 记录当前空闲线程的数目
	 */
	private int idleCount = 0;

	/**
	 * 线程池中允许的最大线程数
	 */
	private int maxCount = 10;

	/**
	 * 线程队列中的线程最长的空闲等待时间
	 */
	private int maxIdleTime = 100000;

	/**
	 * 线程同步对象
	 */
	private Object syncObject = null;

	/*******************************************************************
	 * 函数名称	: ThreadPool
	 * 函数描述	: 构造函数
	 * 输入参数	: maxCount  ----  最大线程数
	 * 输入参数	: taskTimer ----  任务超时管理的定时器对象
	 * 输出参数	: void
	 * 返回值  	: N/A
	 * 备注		: N/A
	 ******************************************************************/
	public TaskQueue(int maxCount){
		super();
		this.tasks = new List<TaskHandleImpl>();
		this.threads = new List<TaskThread>();
		this.syncObject = new Object();
		this.maxCount = maxCount;
	}

	/*******************************************************************
	 * 函数名称	: addTask
	 * 函数描述	: 添加任务请求
	 * 输入参数	: @param task 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: 该函数执行添加任务请求，主要是向线程池请求执行任务的线程资源，如果
	 *            当前没有空闲的线程且线程的总数没有达到上限，则创建一个新的线程；如
	 *            果当前有空闲的线程则唤醒空闲的线程，执行任务请求
	 ******************************************************************/
	public TaskHandle addTask(TaskObject task){
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		TaskHandleImpl taskHandle = null;
		if (task == null){
			return taskHandle;
		}
		// 创建相应的任务控制类对象
		taskHandle = new TaskHandleImpl(this, task);
		// 创建针对该任务的定时器超时任务对象
		TimeoutTask timeoutTask = new TimeoutTask(this, taskHandle);
		task.setTimeoutTask(timeoutTask);
		// 是否需要新建线程标志
		boolean needNewThread = false;
		synchronized (tasks){
			// 向任务队列中添加任务
			tasks.addElement(taskHandle);
		}
		// 设置任务的状态为等待执行状态
		taskHandle.setState(TaskHandleImpl.TASK_STATE_WAITING);
		synchronized (syncObject){
			// 判读是否需要新建线程
			if (idleCount < 1 && threadCount < maxCount){
				threadCount++;
				needNewThread = true;
			}
		}
		// 调用任务回调接口启动超时定时器
		task.startTimeoutTimer();
		// 如果需要创建新线程则创建一个新的线程
		if (needNewThread) {
			TaskThread taskThread = new TaskThread(this);
			synchronized (threads){
				threads.addElement(taskThread);
			}
			taskThread.start();
		}else{
			synchronized (tasks){
				tasks.notify();
			}
		}
		return taskHandle;
	}

	/*******************************************************************
	 * 函数名称	: terminateAllThread
	 * 函数描述	: 终止所有线程
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	public void terminateAllThread(){
		TaskThread thread = null;
		TaskHandleImpl taskHandle = null;
		synchronized (threads){
			thread = (TaskThread) threads.firstElement();
			while (thread != null){
				thread.setTerminate(true);
				thread.interrupt();
				thread = (TaskThread) threads.getNext();
			}
		}
		synchronized (tasks){
			taskHandle = (TaskHandleImpl)tasks.firstElement();
			while (taskHandle != null){
				taskHandle.cancel();
				taskHandle = (TaskHandleImpl)tasks.getNext();
			}
		}
//		System.gc();
	}

	/*******************************************************************
	 * 函数名称	: setTimeout
	 * 函数描述	: 设置线程池中的线程的最长空闲时间
	 * 输入参数	: @param timeout 
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: 如果一个线程空闲等待超时后将强行终止该线程，释放线程占用的资源
	 ******************************************************************/
	public void setMaxIdleTime(int maxIdleTime){
		this.maxIdleTime = maxIdleTime;
	}

	/*******************************************************************
	 * 函数名称	: increaseIdleCount
	 * 函数描述	: 增加空闲线程计数
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	protected void increaseIdleCount(){
		synchronized (syncObject){
			idleCount++;
		}
	}

	/*******************************************************************
	 * 函数名称	: decreaseIdleCount
	 * 函数描述	: 减小空闲线程的计数
	 * 输入参数	: N/A
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: N/A
	 ******************************************************************/
	protected void decreaseIdleCount(){
		synchronized (syncObject){
			idleCount--;
		}
	}

	/*******************************************************************
	 * 函数名称	: obtainTask
	 * 函数描述	: 从任务队列中获取任务
	 * 输入参数	: thread    ---- 调用该方法获取任务的线程
	 * 输出参数	: void
	 * 返回值  	: TaskObject ---- 返回获取到的任务对象
	 * 备注		: @throws InterruptedException  ----  当调用interrupt()终止
	 *            等待的线程时会抛出该异常
	 ******************************************************************/
	protected TaskHandleImpl obtainTask(TaskThread thread)
			throws InterruptedException{
		TaskHandleImpl task = null;
		synchronized (tasks){
			if (tasks.isEmpty()){
				tasks.wait(maxIdleTime);
			}
			if (!tasks.isEmpty()){
				task = (TaskHandleImpl) tasks.delFromFront();
			}
		}
		if (task != null){
			// 保存执行任务的线程对象
			task.setTaskThread(thread);
			// 设置任务的状态为正在执行态
			task.setState(TaskHandleImpl.TASK_STATE_RUNNING);
		}
		return task;
	}

	/*******************************************************************
	 * 函数名称	: removeTask
	 * 函数描述	: 从任务队列中将任务移除，放弃该任务的执行
	 * 输入参数	: @param taskHandle  ---- 需要移除的任务
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: 任务超时后，如果任务还没有执行，即还在任务队列中则从任务队列中删除；
	 *            任务在任务队列中还没有被执行时，用户取消任务也将从任务队列中删除任务
	 ******************************************************************/
	protected boolean removeTask(TaskHandleImpl taskHandle){
		boolean ret = false;
		if (taskHandle == null){
			return ret;
		}
		synchronized (tasks){
			if (!tasks.isEmpty()){
				ret = tasks.removeElement(taskHandle);
			}
		}
		taskHandle.setState(TaskHandleImpl.TASK_STATE_CANCEL);
		return ret;
	}

	/*******************************************************************
	 * 函数名称	: terminateTaskRunning
	 * 函数描述	: 终止任务执行
	 * 输入参数	: @param taskThread  ---- 当前要终止的正在执行任务的线程
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: 任务超时或者用户取消了任务，如果任务正在执行，则终止该任务的执行，
	 *            此时线程不终止，继续执行下一个任务
	 ******************************************************************/
	protected boolean terminateTaskRunning(TaskThread taskThread, TaskHandleImpl taskHandle){
		boolean ret = false;
		if (taskThread == null){
			return ret;
		}
		Log.i("TaskQueue", "Queue terminateTaskRunning ");
		if (taskHandle != null){
			taskHandle.setState(TaskHandle.TASK_STATE_CANCELRUNNING);
		}
		taskThread.setTerminate(false);
		taskThread.interrupt();
		ret = true;
		return ret;
	}

	/*******************************************************************
	 * 函数名称	: deleteThread
	 * 函数描述	: 删除线程
	 * 输入参数	: taskThread  ----  要删除的线程
	 * 输出参数	: void
	 * 返回值  	: void
	 * 备注		: 线程结束后需要将线程计数减一，同时从线程队列中移除
	 ******************************************************************/
	protected void deleteThread(TaskThread taskThread){
		synchronized (syncObject){
			threadCount--;
		}
		synchronized (threads){
			threads.removeElement(taskThread);
		}
		taskThread = null;
//		System.gc();
	}

	/*******************************************************************
	 * 函数名称	: getThreadCount
	 * 函数描述	: 获取线程总数
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: int
	 * 备注		: N/A
	 ******************************************************************/
	public int getThreadCount(){
		return threadCount;
	}

	/*******************************************************************
	 * 函数名称	: getIdleCount
	 * 函数描述	: 获取空闲线程的个数
	 * 输入参数	: @return 
	 * 输出参数	: void
	 * 返回值  	: int
	 * 备注		: N/A
	 ******************************************************************/
	public int getIdleCount(){
		return idleCount;
	}
	
	/****************************************************************************************
	 * 函数名称：cancelTaskByManual
	 * 函数描述：用于执行与页面相关任务的取消操作，这个方法可能没有实际起效果
	 * 输入参数：@param map
	 * 输出参数：
	 * 返回    值：void
	 * 备         注：
	 ****************************************************************************************/
	public void cancelTaskByManual(HashMap<String, Request> map){
		synchronized (tasks){
			Log.i(TAG, "cancelTaskByUser");
			if(!tasks.isEmpty()){
				Vector<?> items = tasks.toVector();
				for(int j = items.size() - 1;j >= 0;j--){
					TaskHandleImpl taskHandle = (TaskHandleImpl) items.elementAt(j);
					if(map.containsKey("" + taskHandle.getTaskObject().getTimeStamp())){
						Log.i(TAG, "cancelTaskByUser.time=" + taskHandle.getTaskObject().getTimeStamp());
						taskHandle.cancel();
					}
				}
			}
		}
	}

}
