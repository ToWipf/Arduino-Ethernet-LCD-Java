package org.wipf.elcd.model.task;

//import java.net.URLEncoder;
import java.util.TimerTask;

import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MTelegram;

public class TaskTelegram extends TimerTask {

	/**
	 *
	 */
	@Override
	public void run() {
		try {
			MTelegram.readUpdateFromTelegram();
		} catch (Exception e) {
			MLogger.warn("TaskTelegram " + e);
		}
	}
}
