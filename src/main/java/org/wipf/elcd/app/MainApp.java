package org.wipf.elcd.app;

import java.net.URI;

import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MTelegram;
import org.wipf.elcd.model.MsqlLite;

/**
 * @author wipf
 *
 */
public class MainApp {

	// TODO:
	/*
	 * alle confs in db
	 * 
	 * 4 gewinnt
	 * 
	 * getmy ID
	 * 
	 * add to motd for id
	 * 
	 * set a new admin ?
	 */

	public static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");
	public static final String VERSION = "1.41";
	public static final String DB_PATH = System.getProperty("user.home") + "/" + "wipfapp.db";
	public static final String ELCD_PATH = "http://192.168.2.242/";
	public static final String sKey = "superKey42";

	public static Integer FailCountElcd;
	public static Integer FailCountTelegram;
	public static Boolean RunLock;
	public static Integer TelegramOffsetID;
	public static String BOTKEY;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MLogger.info("Starte WipfApp " + VERSION);

		MsqlLite.startDB();
		Startup.initDBs();
		if (MTelegram.loadConfig()) {
			Startup.startTelegramTask();
		}
		Startup.startRestApi();
	}

}
