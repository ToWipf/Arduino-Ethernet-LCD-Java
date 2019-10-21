package org.wipf.elcd.model;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MWitz {
//

	public static String getWitz() {
		try {
			return Unirest.get("http://witze.net/witze.rss?cfg=000000410").asString().getBody();

		} catch (UnirestException e) {
			System.out.println("Witzfehler");
			return null;
		}
	}
}
