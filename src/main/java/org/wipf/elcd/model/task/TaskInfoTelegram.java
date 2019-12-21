package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MTelegram;
import org.wipf.elcd.model.MTime;
import org.wipf.elcd.model.struct.Telegram;

/**
 * @author wipf
 *
 */
public class TaskInfoTelegram extends TimerTask {

	/**
	 *
	 */
	@Override
	public void run() {
		try {
			Telegram t = new Telegram();
			t.setAntwort(MTime.dateTime() + "\n" + MTelegram.contMsg() + "\n" + MTelegram.contSend() + "\n\nVersion:"
					+ MainApp.VERSION);
			t.setChatID(-385659721);

			MTelegram.saveTelegramToDB(t);
			MTelegram.sendToTelegram(t);
		} catch (Exception e) {
			MLogger.warn("TaskInfoTelegram " + e);
		}
	}
}
