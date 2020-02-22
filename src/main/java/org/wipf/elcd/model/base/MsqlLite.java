package org.wipf.elcd.model.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.wipf.elcd.app.Startup;

/**
 * @author wipf
 *
 */
public class MsqlLite {

	private static final MsqlLite dbcontroller = new MsqlLite();
	private static Connection connection;

	/**
	 * 
	 */
	public static void startDB() {
		MsqlLite dbc = MsqlLite.getInstance();
		dbc.initDBConnection();
	}

	/**
	 * @return
	 */
	public static MsqlLite getInstance() {
		return dbcontroller;
	}

	/**
	 * @return
	 */
	public static Statement getDB() {
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			MLogger.warn("getDB " + e);
			return null;
		}
	}

	/**
	 * 
	 */
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			MLogger.warn("Fehler beim Laden des JDBC-Treibers " + e);
		}
	}

	/**
	 * 
	 */
	private void initDBConnection() {
		try {
			if (connection != null)
				return;
			MLogger.info("Connect to Database '" + Startup.DB_PATH + "'");
			connection = DriverManager.getConnection("jdbc:sqlite:" + Startup.DB_PATH);
			if (!connection.isClosed())
				MLogger.info("Connection OK");
		} catch (SQLException e) {
			MLogger.warn("initDBConnectionA " + e);
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
					}
				} catch (SQLException e) {
					MLogger.warn("initDBConnectionB " + e);
				}
			}
		});
	}
}
