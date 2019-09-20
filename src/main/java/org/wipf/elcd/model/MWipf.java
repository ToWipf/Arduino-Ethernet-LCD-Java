package org.wipf.elcd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.glassfish.jersey.process.internal.RequestScoped;

/**
 * @author wipf
 *
 */
@RequestScoped
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
	 * @param nWuerfelBis
	 * @param nAnzahlWuerfel
	 * @return
	 */
	public static String zufall(Integer nWuerfelBis, Integer nAnzahlWuerfel) {

		if (nAnzahlWuerfel > 10000 || nWuerfelBis > 10000) {
			return "zu viel";
		}

		Random wuerfel = new Random();
		Integer nZahl;
		Integer nAnzahl = 0;
		Integer nSumme = 0;
		StringBuilder sb = new StringBuilder();

		List<Integer> li = new ArrayList<>();

		for (int i = 0; i < nAnzahlWuerfel; i++) {
			nZahl = wuerfel.nextInt(nWuerfelBis + 1);
			nSumme += nZahl;
			li.add(nZahl);
		}

		sb.append("Summe:" + nSumme + "\n");
		sb.append("Avg:" + nSumme / nAnzahlWuerfel + "\n");

		// Cont jede Zahl
		for (int i = 0; i <= nWuerfelBis; i++) {
			int nMerke = 0;
			for (Integer n : li) {
				if (n == i) {
					nMerke++;
				}
			}
			if (nMerke > 0) {
				sb.append("Anzahl Nr." + i + " :" + nMerke + "\n");
			}
		}

		// Alle ausgeben
		for (Integer n : li) {
			sb.append(nAnzahl + 1 + ": " + n.toString() + "\n");
			nAnzahl++;
		}

		return sb.toString();
	}

	/**
	 * @return
	 */
	public static String testRest() {
		return "WIPF";
	}

}
