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

		if (isAdminUser(t)) {
			// Admin Befehle

			// Anbindung an msg datenbank
			if (t.getMessageWord(0).equals("addamsgtodb")) {
				return addmsg(t);
			}
			if (t.getMessageWord(0).equals("getallmsg")) {
				return getallmsg(t);
			}

			// Anbindung an motd datenbank
			if (t.getMessageWord(0).equals("addamotd")) {
				return addmotd(t);
			}
			if (t.getMessageWord(0).equals("getallmotd")) {
				return getallmotd(t);
			}

			// Auto Msg Tests
			if (t.getMessageWord(0).equals("sendmotd")) {
				sendDaylyMotd();
				return "OK";
			}
			if (t.getMessageWord(0).equals("sendinfo")) {
				sendDaylyInfo();
				return "OK";
			}
		}
		t = getmsg(t, 0);

		// optionen beachten
		if (t.getAntwort() != null) {
			return t.getAntwort();
		} else {
			return "Antwort nicht vorhanden";
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
			return "Nachricht des Tages\n " + MTime.date() + "\n\n" + s;

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
		// TODO Auto-generated method stub
		return "TODO";
	}

}
