package org.wipf.elcd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MWipf {

	/**
	 * @param i
	 */
	public static void sleep(Integer i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public static String testRest() {
		return "Wipf";
	}

	/**
	 * @return
	 */
	public static String zufall() {
		Random wuerfel = new Random();
		Integer nZahl;
		Integer nAnzahl = 0;

		List<Integer> li = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			nZahl = 1 + wuerfel.nextInt(60);
			li.add(nZahl);
		}

		StringBuilder sb = new StringBuilder();

		for (Integer n : li) {
			sb.append(nAnzahl + ": " + n.toString() + "\n");
			nAnzahl++;
		}

		return sb.toString();
	}

}
