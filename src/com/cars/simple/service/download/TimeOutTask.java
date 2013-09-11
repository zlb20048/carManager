package com.cars.simple.service.download;

import java.util.TimerTask;

public class TimeOutTask extends TimerTask {

	private Task<?> task;

	public TimeOutTask(Task<?> task) {
		this.task = task;
	}

	@Override
	public void run() {
		task.timeout();
	}

}
