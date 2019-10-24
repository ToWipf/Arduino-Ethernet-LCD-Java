package org.wipf.elcd.model;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.wipf.elcd.model.struct.Telegram;
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
					+ "&text=" + URLEncoder.encode(sMsg)).asString();
			return res.getBody();
		} catch (UnirestException e) {
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

			ArrayList<Telegram> li = new ArrayList<>();

			JsonNode jn = mapper.readTree(sJson);
			for (JsonNode n : jn) {
				for (JsonNode nn : n) {
					Telegram t = new Telegram();
					try {
						t.setMid(nn.get("message").get("message_id").asInt());
						t.setMessage(nn.get("message").get("text").asText());
						li.add(t);
					} catch (Exception e) {
						// weiter da sticker oder ähnliches
					}
				}
			}
			// ids zu db
			for (Telegram t : li) {
				if (!MsqlLite.getTelegram(t.getMid())) {
					// noch nicht in db
					System.out.println("new:" + t.getMid() + t.getMessage());
					MsqlLite.writeTelegram(t);
					String sAntw;

					switch (t.getMessage()) {
					case "Wipf":
					case "wipf":
					case "wipfe":
					case "Wipfe":
						sAntw = "Wipfe sind sehr schön";
						break;

					default:
						sAntw = "Antwort auf '" + t.getMessage() + "' ist nicht vorhanden";
						break;
					}
					System.out.println("snd:" + t.getMid() + t.getMessage());
					sendeTele(sAntw, TaskTelegram.CHATID);
				} else {
					System.out.println("not:" + t.getMid() + t.getMessage());
				}
			}

			// action bei bestimmten txt

			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
