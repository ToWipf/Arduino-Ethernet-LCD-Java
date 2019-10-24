package org.wipf.elcd.model;

import java.util.ArrayList;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.struct.Telegram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author wipf
 *
 */
public class MTelegram {

	/**
	 * @param t
	 * @return
	 */
	public static String sendToTelegram(Telegram t) {
		try {

			HttpResponse<String> res;
			res = Unirest.post("https://api.telegram.org/" + MainApp.BOTKEY + "/sendMessage?chat_id=" + t.getChatID()
					+ "&text=" + t.getAntwort()).asString();
			return res.getBody();
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 */
	public static void readUpdateFromTelegram() {
		try {
			String sJson;
			if (MainApp.TelegramOffsetID == 0) {
				sJson = Unirest.post("https://api.telegram.org/" + MainApp.BOTKEY + "/getUpdates").asString().getBody();
			} else {
				sJson = Unirest.post(
						"https://api.telegram.org/" + MainApp.BOTKEY + "/getUpdates?offset=" + MainApp.TelegramOffsetID)
						.asString().getBody();
			}

			// parse josn
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<Telegram> li = new ArrayList<>();

			JsonNode jn = mapper.readTree(sJson);

			for (JsonNode n : jn) {
				for (JsonNode nn : n) {
					Telegram t = new Telegram();
					try {
						MainApp.TelegramOffsetID = nn.get("update_id").asInt() + 1; // Nachricht gelesen -> löschen
						JsonNode msg = nn.get("message");
						t.setMid(msg.get("message_id").asInt());
						t.setMessage(msg.get("text").asText());
						t.setChatID(msg.get("chat").get("id").asInt());
						li.add(t);
					} catch (Exception e) {
						// weiter da sticker oder ähnliches
					}
				}
			}
			// ids zu db
			for (Telegram t : li) {
				bearbeiteMsg(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param t
	 */
	private static void bearbeiteMsg(Telegram t) {
		switch (t.getMessage().toLowerCase().replace("/", "").replace(".", "").replace("?", "")) {
		case "wipf":
		case "wipfe":
			t.setAntwort("Wipfe sind sehr schön.");
			break;
		case "hi":
		case "hallo":
		case "hello":
		case "hey":
			t.setAntwort("Hallo, ich bin ein Wipf.");
			break;
		case "help":
		case "hilfe":
		case "info":
			t.setAntwort("Ist ein Wipf gesucht?");
			break;
		case "rnd":
		case "zufall":
			t.setAntwort(MWipf.zufall(60, 10));
			break;
		// TODO: action bei bestimmten txt
		default:
			t.setAntwort("Antwort auf '" + t.getMessage() + "' ist nicht vorhanden.");
			break;
		}

		MsqlLite.saveTelegramToDB(t);
		sendToTelegram(t);
	}

}
