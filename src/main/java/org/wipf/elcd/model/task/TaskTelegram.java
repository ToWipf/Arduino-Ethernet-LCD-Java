package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.app.MainApp;
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
		if (MainApp.FailCountTelegram > 100) {
			// Bei viele Fehlern länger warten aber erneut versuchen
			if (MainApp.FailCountTelegram > 1000) {
				MainApp.FailCountTelegram = 5;
			}
			return;
		}
		try {
			MTelegram.readUpdateFromTelegram();

		} catch (Exception e) {
			MainApp.FailCountTelegram++;
			MLogger.warn("TaskTelegram " + e);
		}
	}
}
