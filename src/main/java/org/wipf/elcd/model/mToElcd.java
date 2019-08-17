package org.wipf.elcd.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class mToElcd {

	private static Boolean restLcd(String sCall) {
		HttpResponse<String> response;
		try {
			response = Unirest.put("http://192.168.2.242/" + sCall).asString();
			return (response.getBody().equals("{}"));
		} catch (UnirestException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * @param n
	 */
	public static void goTo(Integer nCol, Integer nRow) {

		if (nCol > 20 || nCol < 0 || nRow < 0 || nRow > 4) {
			return;
		}
		String sCol;
		if (nCol < 10) {
			sCol = '0' + nCol.toString();
		} else {
			sCol = nCol.toString();
		}

		restLcd("!~" + sCol + nRow);

		System.out.println("col: " + sCol + " row: " + nRow);
	}

	/**
	 * 
	 */
	public static void clear() {
		restLcd("cls");
		System.out.println("cls");
	}

	/**
	 * @param s
	 */
	public static void text(String s) {
		restLcd(s);
		System.out.println("text: " + s);
	}
}