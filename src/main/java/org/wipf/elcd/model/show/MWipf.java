package org.wipf.elcd.model.show;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.glassfish.jersey.process.internal.RequestScoped;

@RequestScoped
public class MWipf {

	/**
	 * @param i
	 */
	public void sleep(Integer i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String testRest() {
		return getFile("web/index.html");
		// return "wipf";
	}

	/**
	 * TODO ungetestet
	 * 
	 * @param sFile
	 * @return
	 */
	private String getFile(String sFile) {
		try {
			return new String(Files.readAllBytes(Paths.get(getClass().getResource(sFile).toURI())));
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return "fehler";
		}

	}

	/**
	 * @return
	 */
	public String zufall() {
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
