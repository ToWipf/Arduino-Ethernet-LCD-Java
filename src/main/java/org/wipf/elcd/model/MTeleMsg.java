package org.wipf.elcd.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.struct.Telegram;

/**
 * @author wipf
 *
 */
public class MTeleMsg {

	/**
	 * 
	 */
	public static void initDB() {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS telemsg (id integer primary key autoincrement, request TEXT, response TEXT, options TEXT, editby TEXT, date INTEGER);");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS telemotd (id integer primary key autoincrement, text TEXT, editby TEXT, date INTEGER);");
		} catch (Exception e) {
			MLogger.warn("initDB telemsg " + e);
		}
	}

	/**
	 * 
	 */
	public static void sendDaylyInfo() {
		Telegram t = new Telegram();
		t.setAntwort(MTime.dateTime() + "\n" + MTeleMsg.contMsg() + "\n" + MTeleMsg.contMotd() + "\n"
				+ MTelegram.contSend() + "\n\nVersion:" + MainApp.VERSION);
		t.setChatID(798200105);

		MTelegram.saveTelegramToDB(t);
		MTelegram.sendToTelegram(t);
	}

	/**
	 * 
	 */
	public static void sendDaylyMotd() {
		Telegram t = new Telegram();
		t.setAntwort(MTeleMsg.getMotd());
		t.setChatID(-387871959);

		MTelegram.saveTelegramToDB(t);
		MTelegram.sendToTelegram(t);
	}

	/**
	 * @param t
	 * @return
	 */
	public static String antworte(Telegram t) {
		// Admin Befehle
		if (isAdminUser(t)) {
			switch (t.getMessageWord(0)) {
			// Anbindung an msg datenbank
			case "addamsgtodb":
				return addmsg(t);
			case "getallmsg":
				return getallmsg(t);
			// Anbindung an motd datenbank
			case "addamotd":
				return addmotd(t);
			case "getallmotd":
				return getallmotd(t);
			// Auto Msg Tests
			case "sendmotd":
				sendDaylyMotd();
				return "OK";
			case "sendinfo":
				sendDaylyInfo();
				return "OK";
			case "getmotd":
				return MTeleMsg.getMotd();
			case "delmotd":
				return delmotd(t);
			case "delmsg":
				return delmsg(t);
			default:
				break;
			}
		}

		// Alle festen Antworten
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
			return MBlowfish.encrypt(t.getMessageFullDataOnly());
		case "d":
		case "de":
		case "dc":
		case "decrypt":
			return MBlowfish.decrypt(t.getMessageFullDataOnly());
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
		case "z":
			return MTime.dateTime();
		case "witz":
		case "fun":
		case "w":
		case "joke":
		case "witze":
			return MWitz.getWitz();
		case "m":
		case "mummel":
		case "mumel":
		case "ml":
			return MMumel.playMumel(t);
		case "countmsg":
			return MTeleMsg.contMsg();
		case "countsend":
			return MTelegram.contSend();
		case "telestats":
			return MTime.dateTime() + "\n" + MTeleMsg.contMsg() + "\n" + MTelegram.contSend();
		default:
			// Alle db aktionen
			t = getmsg(t, 0);
			// ob keine Antwort in db gefunden
			if (t.getAntwort() != null) {
				return t.getAntwort();
			} else {
				return "Antwort nicht vorhanden";
			}
		}
	}

	/**
	 * @param t
	 * @return
	 */
	private static String delmsg(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("DELETE FROM telemsg WHERE id = " + t.getMessageInt(1));
			return "DEL";
		} catch (Exception e) {
			MLogger.warn("delete telemsg" + e);
			return "Fehler";
		}
	}

	/**
	 * @param t
	 * @return
	 */
	private static String delmotd(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("DELETE FROM telemotd WHERE id = " + t.getMessageInt(1));
			return "DEL";
		} catch (Exception e) {
			MLogger.warn("delete telemotd " + e);
			return "Fehler";
		}
	}

	/**
	 * TODO ids zu db
	 * 
	 * @param t
	 * @return
	 */
	private static Boolean isAdminUser(Telegram t) {
		return (t.getChatID() == 798200105 || t.getChatID() == 522467648);
	}

	/**
	 * @param t
	 */
	private static String addmsg(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("INSERT OR REPLACE INTO telemsg (request, response, options, editby, date) VALUES " + "('"
					+ t.getMessageWord(1) + "','" + t.getMessageDataOnly() + "','" + null + "','" + t.getFrom() + "','"
					+ t.getDate() + "')");
			return "OK: " + t.getMessageWord(1);
		} catch (Exception e) {
			MLogger.warn("add telemsg " + e);
			return "Fehler";
		}

	}

	/**
	 * @param t
	 */
	private static String addmotd(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("INSERT OR REPLACE INTO telemotd (text, editby, date) VALUES " + "('"
					+ t.getMessageFullDataOnly() + "','" + t.getFrom() + "','" + t.getDate() + "')");
			return "IN";
		} catch (Exception e) {
			MLogger.warn("add telemotd " + e);
			return "Fehler";
		}
	}

	/**
	 * @return
	 */
	public static String contMsg() {
		try {
			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM telemsg;");
			return rs.getString("COUNT(*)") + " Antworten in der DB";
		} catch (Exception e) {
			MLogger.warn("count Telegram " + e);
			return null;
		}
	}

	/**
	 * @return
	 */
	public static String contMotd() {
		try {
			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM telemotd;");
			return rs.getString("COUNT(*)") + " Motds in der DB";
		} catch (Exception e) {
			MLogger.warn("count Telegram " + e);
			return null;
		}
	}

	/**
	 * @return
	 */
	public static String getMotd() {
		try {
			String s = null;

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("select * from telemotd ORDER BY RANDOM() LIMIT 1");
			while (rs.next()) {
				// Es gibt nur einen Eintrag
				s = (rs.getString("text"));
			}
			rs.close();
			// return "Nachricht des Tages\n " + MTime.date() + "\n\n" + s;
			return s;

		} catch (Exception e) {
			MLogger.warn("get telemotd " + e);
			return "Fehler";
		}
	}

	/**
	 * @param t
	 * @param nStelle
	 * @return
	 */
	private static Telegram getmsg(Telegram t, Integer nStelle) {
		try {
			Map<String, String> mapS = new HashMap<>();

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt
					.executeQuery("select * from telemsg where request = '" + t.getMessageWord(nStelle) + "';");
			while (rs.next()) {
				mapS.put(rs.getString("response"), rs.getString("options"));
			}
			rs.close();
			if (mapS.size() != 0) {
				int nZufallsElement = MWipf.rnd(mapS.size());
				int n = 0;
				for (Map.Entry<String, String> entry : mapS.entrySet()) {
					if (n == nZufallsElement) {
						t.setAntwort(entry.getKey());
						t.setOptions(entry.getValue());
					}
					n++;
				}
			}

		} catch (Exception e) {
			MLogger.warn("get telemsg " + e);
		}
		return t;
	}

	/**
	 * @param t
	 * @return
	 */
	private static String getallmotd(Telegram t) {
		try {
			StringBuilder sb = new StringBuilder();

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("select * from telemotd;");
			while (rs.next()) {
				sb.append(rs.getString("id") + "\t");
				sb.append(rs.getString("text") + "\n");
			}
			rs.close();
			return sb.toString();

		} catch (Exception e) {
			MLogger.warn("get all telemotd" + e);
		}
		return "Fehler";
	}

	/**
	 * @param t
	 * @return
	 */
	private static String getallmsg(Telegram t) {
		try {
			StringBuilder sb = new StringBuilder();

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("select * from telemsg;");
			while (rs.next()) {
				sb.append(rs.getString("id") + "\t");
				sb.append(rs.getString("request") + "\n");
			}
			rs.close();
			return sb.toString();

		} catch (Exception e) {
			MLogger.warn("get all telemotd" + e);
		}
		return "Fehler";
	}

}
