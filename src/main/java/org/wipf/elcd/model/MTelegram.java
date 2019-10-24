package org.wipf.elcd.model;

import org.wipf.elcd.model.task.TaskTelegram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MTelegram {

	/**
	 * @param sMsg
	 * @param sChatId
	 * @return
	 */
	public static String sendeTele(String sMsg, String sChatId) {
		try {
			HttpResponse<String> res;
			res = Unirest.post("https://api.telegram.org/" + TaskTelegram.BOTKEY + "sendMessage?chat_id=" + sChatId
					+ "&text=" + sMsg).asString();
			return res.getBody();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return
	 */
	public static String leseTele() {
		try {
			String sJson = Unirest.post("https://api.telegram.org/" + TaskTelegram.BOTKEY + "getUpdates").asString()
					.getBody();

			// parse josn
			ObjectMapper mapper = new ObjectMapper();

			JsonNode jn = mapper.readTree(sJson);
			for (JsonNode n : jn) {
				for (JsonNode nn : n) {
					System.out.println("xxx: " + nn);
				}
			}

			// ids zu db

			// ob in db

			// action

			return "OK";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
