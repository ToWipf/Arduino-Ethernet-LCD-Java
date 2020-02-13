package org.wipf.elcd.model;

import java.sql.ResultSet;
import java.sql.Statement;

import org.wipf.elcd.model.struct.Telegram;

/**
 * @author wipf
 *
 */
public class MEssen {

	/**
	 * 
	 */
	public static void initDB() {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS essen (id integer primary key autoincrement, type TEXT, name TEXT, options TEXT, editby TEXT, date INTEGER);");
		} catch (Exception e) {
			MLogger.warn("initDB essen " + e);
		}
	}

	/**
	 * 
	 */
	public static void sendDaylyEssen() {
		Telegram t = new Telegram();
		t.setAntwort("Vorschlag für heute:" + "\n" + getEssenRnd());
		t.setChatID(-385659721);

		MTelegram.saveTelegramToDB(t);
		MTelegram.sendToTelegram(t);
	}

	/**
	 * @param t
	 * @return
	 */
	private static String addEssen(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			//@formatter:off
			stmt.execute("INSERT OR REPLACE INTO essen (name, editby, date) VALUES " +
					"('" + t.getMessageFullDataOnly() +
					"','" + t.getFrom() +
					"','"+ t.getDate() +
					"')");
			//@formatter:on
			return "gespeichert";
		} catch (Exception e) {
			MLogger.warn("add essen " + e);
			return "Fehler";
		}
	}

	/**
	 * @return
	 */
	private static String contEssen() {
		try {
			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM essen;");
			return rs.getString("COUNT(*)") + " Einträge in der DB";
		} catch (Exception e) {
			MLogger.warn("count Telegram " + e);
			return null;
		}
	}

	/**
	 * @param t
	 * @return
	 */
	private static String delEssen(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("DELETE FROM essen WHERE id = " + t.getMessageInt(1));
			return "DEL";
		} catch (Exception e) {
			MLogger.warn("delete essen" + e);
			return "Fehler";
		}
	}

	/**
	 * @param t
	 * @return
	 */
	private static String getAllEssen() {
		try {
			StringBuilder sb = new StringBuilder();

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("select * from essen;");
			while (rs.next()) {
				sb.append(rs.getString("id") + "\t");
				sb.append(rs.getString("name") + "\n");
			}
			rs.close();
			return sb.toString();

		} catch (Exception e) {
			MLogger.warn("get all essen" + e);
		}
		return "Fehler";
	}

	/**
	 * @return
	 */
	private static String getEssenRnd() {
		try {
			String s = null;

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("select * from essen ORDER BY RANDOM() LIMIT 1");
			while (rs.next()) {
				// Es gibt nur einen Eintrag
				s = (rs.getString("name"));
			}
			rs.close();
			// return "Nachricht des Tages\n " + MTime.date() + "\n\n" + s;
			return s;

		} catch (Exception e) {
			MLogger.warn("get essen rnd " + e);
			return "Fehler";
		}
	}

	/**
	 * @param t
	 * @return
	 */
	public static String menueEssen(Telegram t) {
		// Admin Befehle
		if (MTelegram.isAdminUser(t)) {
			switch (t.getMessageWord(0)) {
			case "addessen":
				return addEssen(t);
			case "delessen":
				return delEssen(t);
			case "listessen":
				return getAllEssen();
			case "sendessen":
				sendDaylyEssen();
				return "OK";
			case "countessen":
				return contEssen();
			}

		}

		// Alle festen Antworten
		switch (t.getMessageWord(0)) {
		case "getessen":
			return getEssenRnd();
		case "essen":
			//@formatter:off
			return
					"AddEssen: Essen hinzufügen\n" +
					"DelEssen: id löschen\n" + 
					"ListEssen: alles auflisten\n" +
					"GetEssen: Zufallsessen\n" +
					"CountEssen: Anzahl der Einträge\n" +
					"SendEssen: Zufallsessen senden\n";
			//@formatter:on
		default:
			return null;
		}
	}
}
