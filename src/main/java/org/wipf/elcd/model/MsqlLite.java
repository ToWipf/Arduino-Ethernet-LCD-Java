package org.wipf.elcd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.wipf.elcd.app.MainApp;

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
			MLogger.info("Connect to Database...");
			connection = DriverManager.getConnection("jdbc:sqlite:" + MainApp.DB_PATH);
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
					}
				} catch (SQLException e) {
					MLogger.warn("initDBConnection" + e);
				}
			}
		});
	}
}

//private void handleDBExample() {
//try {
//	Statement stmt = connection.createStatement();
//	stmt.executeUpdate("DROP TABLE IF EXISTS books;");
//	stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);");
//	stmt.execute(
//			"INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', '2001-05-06', '1234', '5.67')");
//
//	PreparedStatement ps = connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");
//
//	ps.setString(1, "Willi Winzig");
//	ps.setString(2, "Willi's Wille");
//	ps.setDate(3, Date.valueOf("2011-05-16"));
//	ps.setInt(4, 432);
//	ps.setDouble(5, 32.95);
//	ps.addBatch();
//
//	ps.setString(1, "Anton Antonius");
//	ps.setString(2, "Anton's Alarm");
//	ps.setDate(3, Date.valueOf("2009-10-01"));
//	ps.setInt(4, 123);
//	ps.setDouble(5, 98.76);
//	ps.addBatch();
//
//	connection.setAutoCommit(false);
//	ps.executeBatch();
//	connection.setAutoCommit(true);
//
//	ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
//	while (rs.next()) {
//		System.out.println("Autor = " + rs.getString("author"));
//		System.out.println("Titel = " + rs.getString("title"));
//		System.out.println("Erscheinungsdatum = " + rs.getDate("publication"));
//		System.out.println("Seiten = " + rs.getInt("pages"));
//		System.out.println("Preis = " + rs.getDouble("price"));
//	}
//	rs.close();
//	connection.close();
//} catch (SQLException e) {
//	System.err.println("Couldn't handle DB-Query");
//	e.printStackTrace();
//}
//}