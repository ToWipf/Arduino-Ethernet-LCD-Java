package org.wipf.elcd.model;

import java.net.URLEncoder;

import com.mashape.unirest.http.Unirest;

/**
 * @author wipf
 *
 */
public class MWitz {

	/**
	 * @return
	 */
	public static String getWitz() {
		try {
			String xml = Unirest.get("http://witze.net/witze.rss?cfg=000000410").asString().getBody();
			// return URLEncoder.encode(parse(xml), "UTF-8");
			return URLEncoder.encode(parse(xml), "UTF-8");

		} catch (Exception e) {
			MLogger.warn("Witzfehler " + e);
		}
		return "Fail";
	}

	/**
	 * @param xml
	 * @return
	 */
	private static String parse(String xml) {
		return xml.substring(xml.lastIndexOf("<description>") + 13, xml.lastIndexOf("</description>"))
				.replaceAll("&lt;br&gt;", "\n");
	}

}
