package org.wipf.elcd.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wipf
 *
 */
public class MTime {

	/**
	 * @return
	 */
	public static String uhr() {
		SimpleDateFormat uhr = new SimpleDateFormat("HH:mm:ss");
		return uhr.format(new Date());
	}

	/**
	 * @return
	 */
	public static String date() {
		SimpleDateFormat date = new SimpleDateFormat("dd MMMM yyyy");
		return date.format(new Date());

	}

	/**
	 * @return
	 */
	public static String dayName() {
		SimpleDateFormat date = new SimpleDateFormat("EEEE");
		return date.format(new Date());

	}
}
