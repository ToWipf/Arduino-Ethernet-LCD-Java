package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.model.MTeleMsg;

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
		// InfoMsg Senden
		MTeleMsg.sendDaylyInfo();

		// Warte bis 6 Uhr früh und sende motd
		try {
			Thread.sleep(21600000); // Warte 6 Stunden
			MTeleMsg.sendDaylyMotd();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
