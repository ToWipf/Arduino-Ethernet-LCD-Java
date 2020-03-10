package org.wipf.wipfapp.model.telegram.task;

import java.util.TimerTask;

import org.wipf.wipfapp.model.telegram.apps.MEssen;
import org.wipf.wipfapp.model.telegram.system.MTeleMsg;

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
