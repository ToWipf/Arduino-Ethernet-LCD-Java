package org.wipf.elcd.model.task;

import java.util.TimerTask;

import org.wipf.elcd.model.telegram.apps.MEssen;
import org.wipf.elcd.model.telegram.system.MTeleMsg;

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
		MEssen.sendDaylyEssen();
	}
}
