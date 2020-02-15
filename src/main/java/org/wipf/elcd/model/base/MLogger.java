package org.wipf.elcd.model.base;

import org.wipf.elcd.model.MTime;

/**
 * @author wipf
 *
 */
public class MLogger {
	
	/**
	 * @param s
	 */
	public static void info(Object s) {
		System.out.println("INFO|" + MTime.dateTime() + "| " + s);
	}

	/**
	 * @param s
	 */
	public static void warn(Object s) {
		System.err.println("WARN|" + MTime.dateTime() + "| " + s);
	}
}
