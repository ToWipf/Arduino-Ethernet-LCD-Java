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
					}
					MWipf.sleep(100);
					sendCounter++;
				}
			}
		});
	}

	/**
	 * 
	 */
	public static void displayLoopRare() {
		MTime.date();
	}

	/**
	 * 
	 */
	public static void displayLoop() {
		MTime.uhr();

	}
}
