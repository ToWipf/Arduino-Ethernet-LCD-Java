package org.wipf.elcd.model.elcd;

import javax.enterprise.context.RequestScoped;

import org.wipf.elcd.app.Startup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author wipf
 *
 */
@RequestScoped
public class MelcdConnect {

	/**
	 * @return
	 * 
	 */
	public boolean clear() {
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
	public void write(Integer nRow, Integer nCol, String sText) {
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
	private void restLcd(String sCall) {
		HttpResponse<String> response;
		try {
			response = Unirest.put(Startup.ELCD_PATH + sCall).asString();
			if (response.getBody().indexOf("0") == -1) {
				System.out.println(response.getBody());
			}
			// return (response.getBody().equals("{}"));
			// TODO: setze taster
			Startup.FailCountElcd = 0;

		} catch (UnirestException e) {
			System.out.println("Sendefehler");
			Startup.FailCountElcd++;
		}
	}

}