package org.wipf.elcd.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MsqlLite {

	private static final MsqlLite dbcontroller = new MsqlLite();
	private static Connection connection;
	private static final String DB_PATH = System.getProperty("user.home") + "/" + "testdb.db";

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("Fehler beim Laden des JDBC-Treibers");
			e.printStackTrace();
		}
	}

	private MsqlLite() {
	}

	public static MsqlLite getInstance() {
		return dbcontroller;
	}

	private void initDBConnection() {
		try {
			if (connection != null)
				return;
			System.out.println("Creating Connection to Database...");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
			if (!connection.isClosed())
				System.out.println("...Connection established");
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
							System.out.println("Connection to Database closed");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void createDBs() {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS telegramsettings(id, val);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS telegrambot(msgid, status);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS worte(txt);");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// todo set bot id + set chat id

	public static void toWorte(String s) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO worte (txt) VALUES ('" + s + "')");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void getWorte() {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM worte;");
			while (rs.next()) {
				System.out.println("Text = " + rs.getString("txt"));
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void handleDBExample() {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS books;");
			stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);");
			stmt.execute(
					"INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', '2001-05-06', '1234', '5.67')");

			PreparedStatement ps = connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");

			ps.setString(1, "Willi Winzig");
			ps.setString(2, "Willi's Wille");
			ps.setDate(3, Date.valueOf("2011-05-16"));
			ps.setInt(4, 432);
			ps.setDouble(5, 32.95);
			ps.addBatch();

			ps.setString(1, "Anton Antonius");
			ps.setString(2, "Anton's Alarm");
			ps.setDate(3, Date.valueOf("2009-10-01"));
			ps.setInt(4, 123);
			ps.setDouble(5, 98.76);
			ps.addBatch();

			connection.setAutoCommit(false);
			ps.executeBatch();
			connection.setAutoCommit(true);

			ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
			while (rs.next()) {
				System.out.println("Autor = " + rs.getString("author"));
				System.out.println("Titel = " + rs.getString("title"));
				System.out.println("Erscheinungsdatum = " + rs.getDate("publication"));
				System.out.println("Seiten = " + rs.getInt("pages"));
				System.out.println("Preis = " + rs.getDouble("price"));
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println("Couldn't handle DB-Query");
			e.printStackTrace();
		}
	}

	public static void startDB() {
		MsqlLite dbc = MsqlLite.getInstance();
		dbc.initDBConnection();
		dbc.createDBs();
	}
}
