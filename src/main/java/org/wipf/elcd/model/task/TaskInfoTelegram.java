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
		// Senden
		MTeleMsg.sendDaylyInfo();
		MTeleMsg.sendDaylyMotd();
	}
}
