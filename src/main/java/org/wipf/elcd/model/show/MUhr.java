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

		// MWipf.sleep(1000);
		SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
		String datess = date.format(new Date());

		MElcd.write(0, 5, datess);
	}

	/**
	 * 
	 */
	public static void uhr() {
		SimpleDateFormat uhr = new SimpleDateFormat("HH:mm:ss");
		String sUhr = uhr.format(new Date());
		MElcd.write(0, 6, sUhr);
		// MWipf.sleep(1000);
	}

	/**
	 * 
	 */
	public static void date() {

		SimpleDateFormat date = new SimpleDateFormat("dd MM yyyy");
		String sDate = date.format(new Date());
		MElcd.write(2, 5, sDate);
	}
}
