package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.telegram.system.MTelegram;

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
		if (MainApp.FailCountTelegram > 6) {
			// Bei viele Fehlern länger warten aber erneut versuchen (2 Minuten fehlerhaft)
			MLogger.warn("Task Telegram wartet nun");
			if (MainApp.FailCountTelegram > 12) {
				// 2 Minuten warten
				MLogger.warn("Task Telegram erneuter Verbindungsversuch");
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
