package org.wipf.elcd.app;

import java.net.URI;

import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.base.MsqlLite;
import org.wipf.elcd.model.telegram.system.MTelegram;

/**
 * @author wipf
 *
 */
public class MainApp {

	// TODO:
	/*
	 * //@formatter:off
	 * stringclass
	 * alle confs in db
	 * 4 gewinnt
	 * getmy ID
	 * add to motd for id
	 * set a new admin ?
	 * rm form db
	 * sende in Stunden nachricht
	 * rechner tage in stunden
	 * zeitgeplante nachrichten z.B send 10m Hallo Test
	 * motd für bestimmte Tage
	 * todo tabelle
	 * millisec in dayly msg
	 * rnd starten mit 1 nicht mit 0
	 * admin tabelle (Telegram ids nicht in code)
	 * morsecode
	 * sammelen aller user in tabelle mit rechten
	 * shell raw
	 * 
	 * //@formatter:on
	 */

	public static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");
	public static final String VERSION = "1.79";
	public static final String DB_PATH = System.getProperty("user.home") + "/wipfapp/" + "wipfapp.db";
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
		Startup.runRestApi();
	}

}