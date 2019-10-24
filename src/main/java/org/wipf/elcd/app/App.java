package org.wipf.elcd.app;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
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
public class App {

	private static final URI BASE_URI = URI.create("http://0.0.0.0:8080/");
	public static Integer FailCount;
	public static Boolean RunLock;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Starte Wipf App 24.10.2019");
		StartTasks.StartTask();
		MsqlLite.startDB();
		MsqlLite.toWorte("x");
		MsqlLite.getWorte();

		try {
			RunLock = false;
			Unirest.setTimeouts(3000, 5000);

			final ResourceConfig resourceConfig = new ResourceConfig(Rest.class);
			final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					server.shutdownNow();
				}
			}));
			server.start();

			System.out.println(String.format("Server aktiv: %s", BASE_URI));
			Thread.currentThread().join();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
