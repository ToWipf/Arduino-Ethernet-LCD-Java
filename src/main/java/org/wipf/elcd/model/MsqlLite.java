package org.wipf.elcd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.wipf.elcd.app.MainApp;
import org.wipf.elcd.model.struct.Telegram;

/**
 * @author wipf
 *
 */
public class MsqlLite {

	private static final MsqlLite dbcontroller = new MsqlLite();
	private static Connection connection;
	private static final String DB_PATH = System.getProperty("user.home") + "/" + "wipfapp.db";

	/**
	 * @return
	 */
	public static MsqlLite getInstance() {
		return dbcontroller;
	}

	/**
	 * 
	 */
	public static void startDB() {
		MsqlLite dbc = MsqlLite.getInstance();
		dbc.initDBConnection();
		dbc.createDBs();
		dbc.loadConfig();
	}

	/**
	 * @param sBot
	 * @return
	 */
	public static Boolean setbot(String sBot) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM settings WHERE id = 'telegrambot'");
			stmt.execute("INSERT INTO settings (id, val) VALUES ('telegrambot','" + sBot + "')");
			MainApp.BOTKEY = sBot;
			MLogger.info("Bot Key: " + MainApp.BOTKEY);

			return true;
		} catch (Exception e) {
			MLogger.warn("setbot " + e);
			return false;
		}
	}

	/**
	 * @param t
	 */
	public static void saveTelegramToDB(Telegram t) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO telegramlog (msgid, msg, antw, chatid, msgfrom, msgdate, type)" + " VALUES ('"
					+ t.getMid() + "','" + t.getMessage() + "','" + t.getAntwort() + "','" + t.getChatID() + "','"
					+ t.getFrom() + "','" + t.getDate() + "','" + t.getType() + "')");
		} catch (Exception e) {
			MLogger.warn("saveTelegramToDB " + e);
		}
	}

	/**
	 * Tabellen anlegen
	 */
	private void createDBs() {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS settings (id, val);");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS telegramlog (msgid, msg, antw, chatid, msgfrom, msgdate, type);");
			// stmt.executeUpdate(
			// "CREATE TABLE IF NOT EXISTS telegramlogic (restex, sendtxt, option1, option2,
			// editby);");

		} catch (Exception e) {
			MLogger.warn("createDBs " + e);
		}

	}

	/**
	 * 
	 */
	private void loadConfig() {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT val FROM settings WHERE id = 'telegrambot';");

			MainApp.BOTKEY = (rs.getString("val"));

			rs.close();
		} catch (Exception e) {
			MLogger.warn("telegrambot nicht in db gefunden."
					+ " Setzen mit 'curl -X POST localhost:8080/setbot/bot2343242:ABCDEF348590247354352343345'");
		}
	}

//	private void handleDBExample() {
//		try {
//			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("DROP TABLE IF EXISTS books;");
//			stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);");
//			stmt.execute(
//					"INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', '2001-05-06', '1234', '5.67')");
//
//			PreparedStatement ps = connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");
//
//			ps.setString(1, "Willi Winzig");
//			ps.setString(2, "Willi's Wille");
//			ps.setDate(3, Date.valueOf("2011-05-16"));
//			ps.setInt(4, 432);
//			ps.setDouble(5, 32.95);
//			ps.addBatch();
//
//			ps.setString(1, "Anton Antonius");
//			ps.setString(2, "Anton's Alarm");
//			ps.setDate(3, Date.valueOf("2009-10-01"));
//			ps.setInt(4, 123);
//			ps.setDouble(5, 98.76);
//			ps.addBatch();
//
//			connection.setAutoCommit(false);
//			ps.executeBatch();
//			connection.setAutoCommit(true);
//
//			ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
//			while (rs.next()) {
//				System.out.println("Autor = " + rs.getString("author"));
//				System.out.println("Titel = " + rs.getString("title"));
//				System.out.println("Erscheinungsdatum = " + rs.getDate("publication"));
//				System.out.println("Seiten = " + rs.getInt("pages"));
//				System.out.println("Preis = " + rs.getDouble("price"));
//			}
//			rs.close();
//			connection.close();
//		} catch (SQLException e) {
//			System.err.println("Couldn't handle DB-Query");
//			e.printStackTrace();
//		}
//	}

	/**
	 * @return log
	 */
	public static String getTelegramLog() {
		try {
			StringBuilder sb = new StringBuilder();
			int n = 0;
			Statement stmt = connection.createStatement();
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

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			MLogger.warn("Fehler beim Laden des JDBC-Treibers " + e);
		}
	}

	private void initDBConnection() {
		try {
			if (connection != null)
				return;
			MLogger.info("Connect to Database...");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
			if (!connection.isClosed())
				MLogger.info("...Connection OK");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					if (!connection.isClosed() && connection != null) {
						connection.close();
						if (connection.isClosed())
							MLogger.warn("Connection to Database closed");
						// TODO try to con angain
					}
				} catch (SQLException e) {
					MLogger.warn("initDBConnection" + e);
				}
			}
		});
	}

}
