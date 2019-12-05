package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MTelegram;

/**
 * @author wipf
 *
 */
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
