package org.wipf.elcd.model.elcd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.base.MWipf;

/**
 * @author wipf
 *
 */
public class M_Run {

	/**
	 * Start
	 */
	public static String startElcd() {
		if (MainApp.RunLock) {
			MLogger.info("Runlock is on");
			return "F";
		} else {
			MLogger.info("Set Runlock on");
			MainApp.RunLock = true;
		}
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			@Override
			public void run() {
				Integer sendCounter = 0;
				MLogger.info("Start send to Lcd");
				MWipf.sleep(1000);
				MainApp.FailCountElcd = 0;
				MelcdConnect.clear();
				displayLoopRare();
				if (MainApp.FailCountElcd > 0) {
					MWipf.sleep(500);
				}

				while (MainApp.FailCountElcd < 3) {
					displayLoop();
					if (sendCounter > 100) {
						displayLoopRare();
						sendCounter = 0;
					}
					MWipf.sleep(500);
					sendCounter++;
				}
				MLogger.info("Set Runlock off");
				MainApp.RunLock = false;
			}
		});
		return "K";
	}

	/**
	 * 
	 */
	public static void displayLoopRare() {
		String sDayname = MWipf.dayName();
		String sDate = MWipf.date();

		MelcdConnect.write(1, ((20 - sDayname.length()) / 2), sDayname);
		MelcdConnect.write(2, ((20 - sDate.length()) / 2), sDate);
	}

	/**
	 * 
	 */
	public static void displayLoop() {
		MelcdConnect.write(0, 6, MWipf.uhr());
	}

	/**
	 * @param sMsg
	 * @return
	 */
	public static Boolean sendMsg(String sMsg) {
		try {
			MelcdConnect.write(3, 0, sMsg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
