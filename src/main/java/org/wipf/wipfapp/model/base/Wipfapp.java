package org.wipf.wipfapp.model.base;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Timer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.logging.Logger;
import org.wipf.wipfapp.model.task.TaskInfoTelegram;
import org.wipf.wipfapp.model.task.TaskTelegram;
import org.wipf.wipfapp.model.telegram.apps.MEssen;
import org.wipf.wipfapp.model.telegram.apps.MMumel;
import org.wipf.wipfapp.model.telegram.apps.MTicTacToe;
import org.wipf.wipfapp.model.telegram.apps.MTodoList;
import org.wipf.wipfapp.model.telegram.system.MTeleMsg;
import org.wipf.wipfapp.model.telegram.system.MTelegram;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

// TODO:
/*
 * //@formatter:off
 * stringclass
 * alle confs in db
 * 4 gewinnt
 * add to motd for id
 * set a new admin ?
 * sende in Stunden nachricht
 * rechner tage in stunden
 * zeitgeplante nachrichten z.B send 10m Hallo Test
 * motd für bestimmte Tage
 * admin tabelle (Telegram ids nicht in code)
 * sammelen aller user in tabelle mit rechten
 * shell raw
 * //@formatter:on
 */

/**
 * @author wipf
 *
 */
@ApplicationScoped
public class Wipfapp {

	private static final Logger LOGGER = Logger.getLogger("wipfapp");
	public static final String VERSION = "2.57";
	public static final String DB_PATH = System.getProperty("user.home") + "/wipfapp/" + "wipfapp.db";
	public static final String ELCD_PATH = "http://192.168.2.242/";
	public static final String sKey = "superKey42";

	public static Integer FailCountElcd;
	public static Integer FailCountTelegram;
	public static Boolean RunLock = false;
	public static Integer TelegramOffsetID;
	public static String BOTKEY;

	/**
	 * @param ev
	 */
	void onStart(@Observes StartupEvent ev) {
		System.out.println("_________________________");
		LOGGER.info("Starte WipfApp " + VERSION);

		MsqlLite.startDB();
		initDBs();
		if (MTelegram.loadConfig()) {
			startTelegramTask();
		}
		System.gc();
		LOGGER.info("Wipfapp ist gestartet");
	}

	/**
	 * @param ev
	 */
	void onStop(@Observes ShutdownEvent ev) {
		LOGGER.info("The application is stopping...");
		// System.exit(0);
		// TODO funktioniert nicht
	}

	/**
	 * 
	 */
	public static void startTelegramTask() {
		FailCountTelegram = 0;
		LOGGER.info("Start Telegram Task");
		Timer t = new Timer();
		TaskTelegram mTask = new TaskTelegram();
		TaskInfoTelegram mInfoTask = new TaskInfoTelegram();

		LocalDateTime localDateTime = LocalDateTime.now();

		Integer nSekundenBisMitternacht = (86400
				- (localDateTime.getHour() * 60 * 60 + localDateTime.getMinute() * 60 + localDateTime.getSecond()));

		// This task is scheduled to run every 20 seconds
		t.scheduleAtFixedRate(mTask, 0, 20000);
		// This task is scheduled to run every 1 day at 00:00
		t.scheduleAtFixedRate(mInfoTask, nSekundenBisMitternacht * 1000, 86400000);
	}

	/**
	 * Tabellen anlegen
	 */
	private static void initDBs() {
		MTicTacToe.initDB();
		MTelegram.initDB();
		MTeleMsg.initDB();
		MMumel.initDB();
		MEssen.initDB();
		MTodoList.initDB();

		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS settings (id, val);");

		} catch (Exception e) {
			LOGGER.warn("createDBs " + e);
		}
	}
}
