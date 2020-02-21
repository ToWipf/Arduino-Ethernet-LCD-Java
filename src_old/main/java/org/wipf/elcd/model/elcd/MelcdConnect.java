package org.wipf.elcd.model.elcd;

import org.wipf.elcd.app.MainApp;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author wipf
 *
 */
public class MelcdConnect {

	/**
	 * @return
	 * 
	 */
	public static boolean clear() {
		try {
			restLcd("cls");
			return true;
		} catch (Exception e) {
			return false;
		}
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

	/**
	 * @param sCall
	 */
	private static void restLcd(String sCall) {
		HttpResponse<String> response;
		try {
			response = Unirest.put(MainApp.ELCD_PATH + sCall).asString();
			if (response.getBody().indexOf("0") == -1) {
				System.out.println(response.getBody());
			}
			// return (response.getBody().equals("{}"));
			// TODO: setze taster
			MainApp.FailCountElcd = 0;

		} catch (UnirestException e) {
			System.out.println("Sendefehler");
			MainApp.FailCountElcd++;
		}
	}

}