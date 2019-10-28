package org.wipf.elcd.app;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.wipf.elcd.model.MLogger;
import org.wipf.elcd.model.MsqlLite;
import org.wipf.elcd.rest.Rest;

import com.mashape.unirest.http.Unirest;

/*
 * 
 * TODO: 
 * Telegramm
 * Taster
 * Wetter
 * Ping
 * 
 */

/**
 * @author wipf
 *
 */
public class MainApp {

	// TODO: alle in db:
	public static String VERSION = "0.1";
	private static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");
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

}
