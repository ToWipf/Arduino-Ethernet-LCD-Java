package org.wipf.elcd.app;

import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Timer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.jboss.logging.Logger;
import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.base.MsqlLite;
import org.wipf.elcd.model.task.TaskInfoTelegram;
import org.wipf.elcd.model.task.TaskTelegram;
import org.wipf.elcd.model.telegram.apps.MEssen;
import org.wipf.elcd.model.telegram.apps.MMumel;
import org.wipf.elcd.model.telegram.apps.MTicTacToe;
import org.wipf.elcd.model.telegram.apps.MTodoList;
import org.wipf.elcd.model.telegram.system.MTeleMsg;
import org.wipf.elcd.model.telegram.system.MTelegram;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

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

/**
 * @author wipf
 *
 */
@ApplicationScoped
public class Startup {

	private static final Logger LOGGER = Logger.getLogger("ListenerBean");
	public static final String VERSION = "2.05";
	public static final String DB_PATH = System.getProperty("user.home") + "/wipfapp/" + "wipfapp.db";
	public static final String ELCD_PATH = "http://192.168.2.242/";
	public static final String sKey = "superKey42";

	public static Integer FailCountElcd;
	public static Integer FailCountTelegram;
	public static Boolean RunLock;
	public static Integer TelegramOffsetID;
	public static String BOTKEY;

	/**
	 * @param ev
	 */
	void onStart(@Observes StartupEvent ev) {
		LOGGER.info("The application is starting...");
		MLogger.info("Starte WipfApp " + VERSION);

		MsqlLite.startDB();
		initDBs();
		if (MTelegram.loadConfig()) {
			Startup.startTelegramTask();
		}
	}

	/**
	 * @param ev
	 */
	void onStop(@Observes ShutdownEvent ev) {
		LOGGER.info("The application is stopping...");
	}

	/**
	 * 
	 */
	public static void startTelegramTask() {
		FailCountTelegram = 0;
		MLogger.info("Start Telegram Task");
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
			MLogger.warn("createDBs " + e);
		}
	}
}
