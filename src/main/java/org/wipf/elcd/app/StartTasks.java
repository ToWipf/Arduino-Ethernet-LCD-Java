package org.wipf.elcd.app;

import java.util.Timer;

import org.wipf.elcd.model.task.TaskTelegram;

public class StartTasks {
	public static void StartTask() {

		Timer t = new Timer();
		TaskTelegram mTask = new TaskTelegram();
		// This task is scheduled to run every 30 seconds

		t.scheduleAtFixedRate(mTask, 0, 30000);
	}
}
