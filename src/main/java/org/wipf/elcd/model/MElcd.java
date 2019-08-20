package org.wipf.elcd.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MElcd {

	/**
	 * @param args
	 */
	public static void start() {
		System.out.println("Es Startet!");
		MToElcd.clear();
		MToElcd.clear();

		// testLcd();
		testuhr();
		// testSens();
		// testSensCpu();

		// System.exit(0);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void testLcd() {
		// mToElcd.testRest();
		MToElcd.clear();
		MToElcd.goTo(0, 0);
		MToElcd.text("Hallo1");
		MToElcd.goTo(1, 1);
		MToElcd.text("Hallo2");
		MToElcd.goTo(2, 2);
		MToElcd.text("Hallo3");
		MToElcd.goTo(3, 3);
		MToElcd.text("Hallo4");
		MToElcd.goTo(19, 2);
		MToElcd.text("p");
//		mToElcd.goTo(0, 1);
//		mToElcd.text("  W I P F");
//		mToElcd.goTo(0, 2);
//		mToElcd.text("1234567890abcdefge");
//		mToElcd.goTo(5, 3);
//		mToElcd.text("W I P F");

		for (Integer i = 0; i < 100; i++) {
			// mToElcd.goToLine(2);
			MToElcd.text("Wipf Nr." + i + "!");
			System.out.println(i);
		}

	}

	private static void testuhr() {
		MToElcd.goTo(2, 3);
		MToElcd.text("Uhr:");
		for (Integer n = 0; n < 1000; n++) {
			MToElcd.goTo(6, 3);
			// SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
			String datess = date2.format(new Date());

			MToElcd.text(datess);
			System.out.println(n);
		}
	}

}