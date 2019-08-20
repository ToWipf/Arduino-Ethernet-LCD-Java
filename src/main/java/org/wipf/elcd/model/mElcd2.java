package org.wipf.elcd.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class mElcd2 {

	/**
	 * @param args
	 */
	public static void start() {
		System.out.println("Es Startet!");
		mToElcd.clear();
		mToElcd.clear();

		// testLcd();
		testuhr();
		// testSens();
		// testSensCpu();

		System.exit(0);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void testLcd() {
		// mToElcd.testRest();
		mToElcd.clear();
		mToElcd.goTo(0, 0);
		mToElcd.text("Hallo1");
		mToElcd.goTo(1, 1);
		mToElcd.text("Hallo2");
		mToElcd.goTo(2, 2);
		mToElcd.text("Hallo3");
		mToElcd.goTo(3, 3);
		mToElcd.text("Hallo4");
		mToElcd.goTo(19, 2);
		mToElcd.text("p");
//		mToElcd.goTo(0, 1);
//		mToElcd.text("  W I P F");
//		mToElcd.goTo(0, 2);
//		mToElcd.text("1234567890abcdefge");
//		mToElcd.goTo(5, 3);
//		mToElcd.text("W I P F");

		for (Integer i = 0; i < 100; i++) {
			// mToElcd.goToLine(2);
			mToElcd.text("Wipf Nr." + i + "!");
			System.out.println(i);
		}

	}

	private static void testuhr() {
		mToElcd.goTo(2, 3);
		mToElcd.text("Uhr:");
		for (Integer n = 0; n < 1000; n++) {
			mToElcd.goTo(6, 3);
			// SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
			String datess = date2.format(new Date());

			mToElcd.text(datess);
			System.out.println(n);
		}
	}

}