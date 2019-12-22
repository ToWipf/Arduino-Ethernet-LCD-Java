package org.wipf.elcd.model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
	 * @param t
	 * @return
	 */
	public static String antworte(Telegram t) {

		// Anbindung an msg datenbank
		if (t.getMessageWord(0).equals("addamsgtodb")) {
			return addmsg(t);
		}
		if (t.getMessageWord(0).equals("addamotd")) {
			return addmotd(t);
		}
		t = get(t, 0);

		// optionen beachten
		if (t.getAntwort() != null) {
			return t.getAntwort();
		} else {
			return "Antwort nicht vorhanden";
		}

	}

	/**
	 * @param t
	 */
	private static String addmsg(Telegram t) {
		if (t.getChatID() == 798200105 || t.getChatID() == 522467648) {
			try {
				Statement stmt = MsqlLite.getDB();
				stmt.execute("INSERT OR REPLACE INTO telemsg (request, response, options, editby, date) VALUES " + "('"
						+ t.getMessageWord(1) + "','" + t.getMessageDataOnly() + "','" + null + "','" + t.getFrom()
						+ "','" + t.getDate() + "')");
				return "OK: " + t.getMessageWord(1);
			} catch (Exception e) {
				MLogger.warn("add telemsg " + e);
			}
		}
		return "Fehler";
	}

	/**
	 * @param t
	 */
	private static String addmotd(Telegram t) {
		if (t.getChatID() == 798200105 || t.getChatID() == 522467648) {
			try {
				Statement stmt = MsqlLite.getDB();
				stmt.execute("INSERT OR REPLACE INTO telemotd (text, editby, date) VALUES " + "('"
						+ t.getMessageDataOnly() + "','" + t.getFrom() + "','" + t.getDate() + "')");
				return "IN: " + t.getMessageWord(1);
			} catch (Exception e) {
				MLogger.warn("add telemotd " + e);
			}
		}
		return "Fehler";
	}

	/**
	 * @param t
	 * @param nStelle
	 * @return
	 */
	private static Telegram get(Telegram t, Integer nStelle) {
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

}
