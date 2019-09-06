package org.wipf.elcd.model;

import org.wipf.elcd.app.App;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MElcd {

	/**
	 * @param sCall
	 */
	private static void restLcd(String sCall) {
		HttpResponse<String> response;
		try {
			response = Unirest.put("http://192.168.2.242/" + sCall).asString();
			if (response.getBody().indexOf("0") == -1) {
				System.out.println(response.getBody());
			}
			// return (response.getBody().equals("{}"));
			// TODO: sezte taster
			App.FailCont = 0;

		} catch (UnirestException e) {
			// e.printStackTrace();
			System.out.println("Sendefehler");
			App.FailCont++;
		}
	}

	/**
	 * 
	 */
	public static void clear() {
		System.out.println("cls");
		restLcd("cls");
	}

	/**
	 * @param nCol
	 * @param nRow
	 * @param Text
	 */
	public static void write(Integer nRow, Integer nCol, String sText) {

		if (nCol > 20 || nCol < 0 || nRow < 0 || nRow > 4 || sText.length() > 20 || sText.indexOf(' ') == 0) {
			return;
		}

		String sCol;
		if (nCol < 10) {
			sCol = '0' + nCol.toString();
		} else {
			sCol = nCol.toString();
		}

		restLcd(nRow + sCol + sText);
	}

}