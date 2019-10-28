package org.wipf.elcd.model;

import java.util.ArrayList;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.struct.Telegram;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	public static void sendToTelegram(Telegram t) {
		try {
			// HttpResponse<String> res;
			// res =
			Unirest.post("https://api.telegram.org/" + MainApp.BOTKEY + "/sendMessage?chat_id=" + t.getChatID()
					+ "&text=" + t.getAntwort()).asString();
			// MLogger.info(res.getBody());
		} catch (UnirestException e) {
			MLogger.warn("Telegram senden " + e);
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
						t.setType(msg.get("chat").get("type").asText());
						t.setDate(msg.get("date").asInt());
						t.setFrom(msg.get("from").toString());
						li.add(t);
					} catch (Exception e) {
						// weiter da sticker oder ähnliches
					}
				}
			}
			// ids zu db
			for (Telegram t : li) {
				try {
					t.setAntwort(bearbeiteMsg(new Telegram(t)));
					MsqlLite.saveTelegramToDB(t);
					sendToTelegram(t);
				} catch (Exception e) {
					MLogger.warn("bearbeiteMsg " + e);
				}
			}

		} catch (Exception e) {
			MLogger.warn("readUpdateFromTelegram " + e);
		}
	}

	/**
	 * @param t
	 */
	private static String bearbeiteMsg(Telegram t) {
		switch (t.getMessageWord(0)) {
		case "start":
			return "Wipfbot ist bereit\nInfos per 'info'";
		case "wipf":
		case "wipfe":
			return "Wipfe sind sehr schön.";
		case "hi":
		case "hallo":
		case "hello":
		case "hey":
			return "Hallo, ich bin ein Wipf.";
		case "wipfbot":
		case "help":
		case "hlp":
		case "ver":
		case "version":
		case "hilfe":
		case "info":
		case "about":
			return "Wipfbot\nVersion " + MainApp.VERSION + "\nCreated by Tobias Fritsch\nwipf2@web.de";
		case "rnd":
		case "zufall":
			return MWipf.zufall(t.getMessageWord(1), t.getMessageWord(2));
		case "ping":
			return "Pong";
		case "pong":
			return "Ping";
		case "pingu":
		case "pingui":
		case "pinguin":
		case "pinguine":
		case "🐧":
			return "%F0%9F%90%A7"; // 🐧
		case "test":
			return "👻+🍕";
		case "c":
		case "cr":
		case "en":
		case "encrypt":
			return MBlowfish.encrypt(t.getMessageRaw(1));
		case "d":
		case "de":
		case "dc":
		case "decrypt":
			return MBlowfish.decrypt(t.getMessageRaw(1));
		case "t":
		case "ttt":
		case "tictactoe":
			return MTicTacToe.input(t);
		case "time":
		case "date":
		case "datum":
		case "uhr":
		case "zeit":
		case "clock":
			return MTime.dateTime();
		case "42":
			return "Der Sinn des lebens";
		default:
			return "Antwort auf '" + t.getMessage() + "' ist nicht vorhanden.";
		}
	}

}
