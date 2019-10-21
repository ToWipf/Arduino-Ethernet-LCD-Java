package org.wipf.elcd.model;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MTelegram {

	public static void sendeTele(String sMsg) {
		try {

			Unirest.post("https://api.telegram.org/" + sKey + "&text=" + sMsg).asString();

		} catch (UnirestException e) {
			System.out.println("Telefehler");
		}
	}

}
