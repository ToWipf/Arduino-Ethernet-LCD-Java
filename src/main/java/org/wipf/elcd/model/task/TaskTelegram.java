package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.main.Wipfapp;
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
		if (Wipfapp.FailCountTelegram > 6) {
			// Bei viele Fehlern länger warten aber erneut versuchen (2 Minuten fehlerhaft)
			Wipfapp.FailCountTelegram++;
			MLogger.warn("Task Telegram wartet nun " + Wipfapp.FailCountTelegram + "/12");
			if (Wipfapp.FailCountTelegram > 12) {
				// 4 Minuten warten
				MLogger.warn("Task Telegram erneuter Verbindungsversuch");
				Wipfapp.FailCountTelegram = 1;
			}
			return;
		}
		try {
			MTelegram.readUpdateFromTelegram();

		} catch (Exception e) {
			Wipfapp.FailCountTelegram++;
			MLogger.warn("TaskTelegram " + e);
		}
	}
}
