package org.wipf.elcd.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wipf.elcd.model.MElcd;
import org.wipf.elcd.model.show.MUhr;

public class ELcd {

	/**
	 * Start ///TODO Telegramm
	 */
	public static void startElcd() {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				System.out.println("Start send to Lcd");
				// Start
				App.FailCont = 0;
				// MWipf.sleep(1000);
				MElcd.clear();
				// MWipf.sleep(1000);
				MUhr.date();
				// while (App.FailCont < 3) {
				// Loop
				// MUhr.uhr();
				// }
			}
		});

	}
}
