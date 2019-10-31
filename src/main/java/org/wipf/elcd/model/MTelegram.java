package org.wipf.elcd.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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
	 * 
	 */
	public static void initDB() {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS telegramlog (msgid INTEGER, msg TEXT, antw TEXT, chatid INTEGER, msgfrom TEXT, msgdate INTEGER, type TEXT);");

		} catch (Exception e) {
			MLogger.warn("initDB Telegram " + e);
		}
	}

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
					saveTelegramToDB(t);
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
		case "play":
		case "game":
			return MTicTacToe.input(t);
		case "time":
		case "date":
		case "datum":
		case "uhr":
		case "zeit":
		case "clock":
			return MTime.dateTime();
		case "witz":
		case "fun":
		case "w":
		case "joke":
			return MWitz.getWitz();
		default:
			return MTeleMsg.antworte(t);
		}
	}

	/**
	 * @param t
	 */
	private static void saveTelegramToDB(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("INSERT INTO telegramlog (msgid, msg, antw, chatid, msgfrom, msgdate, type)" + " VALUES ('"
					+ t.getMid() + "','" + t.getMessage() + "','" + t.getAntwort() + "','" + t.getChatID() + "','"
					+ t.getFrom() + "','" + t.getDate() + "','" + t.getType() + "')");

		} catch (Exception e) {
			MLogger.warn("saveTelegramToDB " + e);
		}
	}

	/**
	 * @return log
	 */
	public static String getTelegramLog() {
		try {
			StringBuilder sb = new StringBuilder();
			int n = 0;
			Statement stmt = MsqlLite.getDB();
			// ResultSet rs = stmt.executeQuery("SELECT * FROM telegrambot WHERE msgid = '"
			// + nID + "';");
			ResultSet rs = stmt.executeQuery("SELECT * FROM telegramlog ORDER BY msgdate DESC");

			while (rs.next()) {
				n++;
				Date date = new Date(rs.getLong("msgdate") * 1000);

				sb.append(n + ":\n");
				sb.append("msgid:  \t" + rs.getString("msgid") + "\n");
				sb.append("msg in: \t" + rs.getString("msg") + "\n");
				sb.append("msg out:\t" + rs.getString("antw") + "\n");
				sb.append("chatid: \t" + rs.getString("chatid") + "\n");
				sb.append("msgfrom:\t" + rs.getString("msgfrom") + "\n");
				sb.append("msgdate:\t" + date + "\n");
				sb.append("type:   \t" + rs.getString("type") + "\n");
				sb.append("----------------\n\n");
			}
			rs.close();
			return sb.toString();
		} catch (Exception e) {
			MLogger.warn("getTelegram" + e);
			return "FAIL";
		}

	}
}
