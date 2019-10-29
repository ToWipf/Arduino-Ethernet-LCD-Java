package org.wipf.elcd.app;

import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MsqlLite;
import org.wipf.elcd.rest.Rest;

import com.mashape.unirest.http.Unirest;

/**
 * @author wipf
 *
 */
public class MainApp {

	// TODO: alle in db:
	private static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");
	public static final String VERSION = "0.02";
	public static final String DB_PATH = System.getProperty("user.home") + "/" + "wipfapp.db";
	public static final String ELCD_PATH = "http://192.168.2.242/";

	public static Integer FailCount;
	public static Boolean RunLock;
	public static Integer TelegramOffsetID;
	public static String BOTKEY;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MLogger.info("Starte WipfApp");
		TelegramOffsetID = 0;
		MsqlLite.startDB();
		createDBs();
		loadConfig();

		StartTasks.StartTask();

		try {
			RunLock = false;
			Unirest.setTimeouts(3000, 5000);

			final ResourceConfig resourceConfig = new ResourceConfig(Rest.class);
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					MLogger.warn(String.format("Server beenden"));
					server.shutdownNow();
				}
			}));
			server.start();

			MLogger.info(String.format("Server aktiv: %s", BASE_URI));
			Thread.currentThread().join();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 
	 */
	private static void loadConfig() {
		try {
			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("SELECT val FROM settings WHERE id = 'telegrambot';");

			MainApp.BOTKEY = (rs.getString("val"));

			rs.close();
		} catch (Exception e) {
			MLogger.warn("telegrambot nicht in db gefunden."
					+ " Setzen mit 'curl -X POST localhost:8080/setbot/bot2343242:ABCDEF348590247354352343345'");
		}
	}

	/**
	 * @param sBot
	 * @return
	 */
	public static Boolean setbot(String sBot) {
		try {
			Statement stmt = MsqlLite.getDB();
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
	 * Tabellen anlegen
	 */
	private static void createDBs() {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS settings (id, val);");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS telegramlog (msgid, msg, antw, chatid, msgfrom, msgdate, type);");
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS ttt (chatid INTEGER UNIQUE, feld TEXT, msgdate INTEGER, type TEXT);");
			// stmt.executeUpdate(
			// "CREATE TABLE IF NOT EXISTS telegramlogic (restex, sendtxt, option1, option2,
			// editby);");

		} catch (Exception e) {
			MLogger.warn("createDBs " + e);
		}

	}

}
