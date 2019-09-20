package org.wipf.elcd.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MTime{

	/**
	 * 
	 */
	public static void uhr() {
		SimpleDateFormat uhr = new SimpleDateFormat("HH:mm:ss");
		String sUhr = uhr.format(new Date());
		MSendToELcd.write(0, 6, sUhr);
	}

	/**
	 * 
	 */
	public static void date() {
		SimpleDateFormat date = new SimpleDateFormat("dd MM yyyy");
		String sDate = date.format(new Date());
		MSendToELcd.write(2, 5, sDate);
	}
}
