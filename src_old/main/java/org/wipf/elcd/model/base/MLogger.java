package org.wipf.elcd.model.base;

/**
 * @author wipf
 *
 */
public class MLogger {

	/**
	 * @param s
	 */
	public static void info(Object s) {
		System.out.println("INFO|" + MWipf.dateTime() + "| " + s);
	}

	/**
	 * @param s
	 */
	public static void warn(Object s) {
		System.err.println("WARN|" + MWipf.dateTime() + "| " + s);
	}
}
