package org.wipf.elcd.app;

import java.util.Timer;

import org.wipf.elcd.model.task.TaskTelegram;

/**
 * @author wipf
 *
 */
public class StartTasks {

	/**
	 * 
	 */
	public static void StartTask() {

		Timer t = new Timer();
		TaskTelegram mTask = new TaskTelegram();
		// This task is scheduled to run every 20 seconds

		t.scheduleAtFixedRate(mTask, 0, 20000);
	}
}
