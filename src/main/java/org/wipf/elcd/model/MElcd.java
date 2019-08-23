package org.wipf.elcd.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MElcd {

	private static Boolean restLcd(String sCall) {
		HttpResponse<String> response;
		// TODO Timeoutzeit auf minimal stellen
		try {
			response = Unirest.put("http://192.168.2.242/" + sCall).asString();
			System.out.println(response.getBody());
			// return (response.getBody().equals("{}"));
			// TODO: sezte taster
			return true;
		} catch (UnirestException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 */
	public static Boolean clear() {
		System.out.println("cls");
		return restLcd("cls");
	}

	/**
	 * @param nCol
	 * @param nRow
	 * @param Text
	 */
	public static Boolean write(Integer nRow, Integer nCol, String sText) {
		if (nCol > 20 || nCol < 0 || nRow < 0 || nRow > 4 || sText.length() > 20 || sText.indexOf(' ') == 0) {
			return null;
		}
		String sCol;
		if (nCol < 10) {
			sCol = '0' + nCol.toString();
		} else {
			sCol = nCol.toString();
		}

		System.out.println("Zeile: " + nRow + " Zeichen: " + sCol + " Text: " + sText);
		return restLcd(nRow + sCol + sText);
	}

}