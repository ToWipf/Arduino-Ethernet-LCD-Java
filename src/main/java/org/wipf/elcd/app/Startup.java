package org.wipf.elcd.app;

import java.sql.Statement;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MMumel;
import org.wipf.elcd.model.MTeleMsg;
import org.wipf.elcd.model.MTelegram;
import org.wipf.elcd.model.MTicTacToe;
import org.wipf.elcd.model.MsqlLite;
import org.wipf.elcd.model.task.TaskTelegram;
import org.wipf.elcd.rest.Rest;

import com.mashape.unirest.http.Unirest;

/**
 * @author wipf
 *
 */
public class Startup {

	/**
	 * 
	 */
	public static void startRestApi() {
		try {
			MainApp.RunLock = false;
			Unirest.setTimeouts(3000, 5000);

			final ResourceConfig resourceConfig = new ResourceConfig(Rest.class);
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(MainApp.BASE_URI, resourceConfig,
					false);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					MLogger.warn(String.format("api beenden"));
					server.shutdownNow();
				}
			}));
			server.start();

			MLogger.info(String.format("api aktiv: %s", MainApp.BASE_URI));
			Thread.currentThread().join();
		} catch (Exception ex) {
			Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * 
	 */
	public static void startTelegramTask() {
		MLogger.info("Start Telegram Task");
		Timer t = new Timer();
		TaskTelegram mTask = new TaskTelegram();
		// This task is scheduled to run every 20 seconds

		t.scheduleAtFixedRate(mTask, 0, 20000);
	}

	/**
	 * Tabellen anlegen
	 */
	public static void initDBs() {
		MTicTacToe.initDB();
		MTelegram.initDB();
		MTeleMsg.initDB();
		MMumel.initDB();
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS settings (id, val);");

		} catch (Exception e) {
			MLogger.warn("createDBs " + e);
		}
	}
}
