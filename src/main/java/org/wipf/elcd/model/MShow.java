package org.wipf.elcd.model;

public class MShow {

	/**
	 * @param args
	 */
	public static void start() {
		System.out.println("Send to Lcd");
		MElcd.clear();
		MUhr.uhr();
	}

}