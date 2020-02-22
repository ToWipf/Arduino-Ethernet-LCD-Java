package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.app.Startup;
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
		if (Startup.FailCountTelegram > 6) {
			// Bei viele Fehlern länger warten aber erneut versuchen (2 Minuten fehlerhaft)
			MLogger.warn("Task Telegram wartet nun");
			if (Startup.FailCountTelegram > 12) {
				// 2 Minuten warten
				MLogger.warn("Task Telegram erneuter Verbindungsversuch");
				Startup.FailCountTelegram = 5;
			}
			return;
		}
		try {
			MTelegram.readUpdateFromTelegram();

		} catch (Exception e) {
			Startup.FailCountTelegram++;
			MLogger.warn("TaskTelegram " + e);
		}
	}
}
