package org.wipf.elcd.app;

import java.util.Timer;

import org.wipf.elcd.model.task.TaskTelegram;

public class StartTasks {
	public static void main(String[] args) {

		Timer t = new Timer();
		TaskTelegram mTask = new TaskTelegram();
		// This task is scheduled to run every 10 seconds

		t.scheduleAtFixedRate(mTask, 0, 10000);
	}
}
