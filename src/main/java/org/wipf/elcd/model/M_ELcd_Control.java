package org.wipf.elcd.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wipf.elcd.app.App;

public class M_ELcd_Control {

	/**
	 * Start ///TODO Telegramm
	 */
	public static void startElcd() {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				Integer sendCounter = 0;
				System.out.println("Start send to Lcd");
				MWipf.sleep(1000);
				App.FailCont = 0;
				MSendToELcd.clear();
				displayLoopRare();

				while (App.FailCont < 3) {
					displayLoop();
					if (sendCounter > 100) {
						displayLoopRare();
						sendCounter = 0;
					}
					MWipf.sleep(500);
					sendCounter++;
				}
			}
		});
	}

	/**
	 * 
	 */
	public static void displayLoopRare() {
		String sDayname = MTime.dayName();
		String sDate = MTime.date();
		MSendToELcd.write(1, ((20 - sDayname.length()) / 2), sDayname);
		MSendToELcd.write(2, ((20 - sDate.length()) / 2), sDate);
	}

	/**
	 * 
	 */
	public static void displayLoop() {
		MSendToELcd.write(0, 6, MTime.uhr());
	}
}
