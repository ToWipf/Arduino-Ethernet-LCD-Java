package org.wipf.elcd.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.wipf.elcd.model.MElcd;
import org.wipf.elcd.model.MUhr;
import org.wipf.elcd.model.MWipf;

public class ELcd {

	/**
	 * Start
	 */
	public static void startElcd() {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				System.out.println("Start send to Lcd");
				MWipf.sleep(1000);
				MElcd.clear();
				MWipf.sleep(1000);
				MUhr.uhr();
			}
		});

	}
}
