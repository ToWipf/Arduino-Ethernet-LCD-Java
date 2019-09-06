package org.wipf.elcd.model.show;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.wipf.elcd.model.MElcd;

public class MUhr {
	/**
	 * 
	 */
	public static void uhrOneLine() {
		MElcd.write(0, 0, "Uhr: "); // TODO init

		MWipf.sleep(1000);
		SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
		String datess = date2.format(new Date());

		MElcd.write(0, 5, datess);
	}

	/**
	 * 
	 */
	public static void uhrAndDate() {
		MElcd.write(0, 0, "Uhr: ");

		// SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
		String datess = date2.format(new Date());

		MElcd.write(0, 5, datess);

		MWipf.sleep(1000);
	}
}
