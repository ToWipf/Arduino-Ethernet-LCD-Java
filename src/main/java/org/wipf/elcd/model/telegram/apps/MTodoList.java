package org.wipf.elcd.model.telegram.apps;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.wipf.elcd.model.base.MLogger;
import org.wipf.elcd.model.base.MsqlLite;
import org.wipf.elcd.model.struct.Telegram;
import org.wipf.elcd.model.telegram.system.MTelegram;

/**
 * @author wipf
 *
 */
public class MTodoList {

	/**
	 * 
	 */
	public static void initDB() {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS todolist (id integer primary key autoincrement, data TEXT, remind TEXT, active TEXT, editby TEXT, date INTEGER);");
		} catch (Exception e) {
			MLogger.warn("initDB todolist " + e);
		}
	}

	/**
	 * @param t
	 * @return
	 */
	public static String menueTodoList(Telegram t) {

		// Admin Befehle
		if (MTelegram.isAdminUser(t)) {
			String sAction = t.getMessageStringPart(1);
			if (sAction == null) {
				return "add, del, list, count";
			}

			switch (sAction) {
			case "a":
			case "add":
				return add(t);
			case "d":
			case "del":
				return del(t);
			case "l":
			case "list":
				return getAll();
			case "lf":
			case "listfull":
				return getAllFull();
			case "c":
			case "count":
				return count();
			default:
				return "Hilfe mit: Todo Hilfe";
			}

		}
		return "Du kannst keine Todo-Liste anlegen";
	}

	/**
	 * @return
	 */
	public static String getAllFull() {
		try {
			StringBuilder slog = new StringBuilder();
			int n = 0;
			Statement stmt = MsqlLite.getDB();

			ResultSet rs = stmt.executeQuery("SELECT * FROM todolist");

			while (rs.next()) {
				n++;
				// Timestamp zu datum
				Date date = new Date(rs.getLong("date") * 1000);
				StringBuilder sb = new StringBuilder();

				sb.append(n + ":\n");
				sb.append("id:  \t" + rs.getString("id") + "\n");
				sb.append("data: \t" + rs.getString("data") + "\n");
				sb.append("remind:\t" + rs.getString("remind") + "\n");
				sb.append("active: \t" + rs.getString("active") + "\n");
				sb.append("editby:\t" + rs.getString("editby") + "\n");
				sb.append("date:\t" + date + "\n");
				sb.append("----------------\n\n");
				slog.insert(0, sb);
			}
			rs.close();
			return slog.toString();
		} catch (Exception e) {
			MLogger.warn("getAllFull todolist" + e);
			return "FAIL";
		}

	}

	/**
	 * @return
	 */
	private static String getAll() {
		try {
			StringBuilder sb = new StringBuilder();

			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("select * from todolist;");
			while (rs.next()) {
				sb.append(rs.getString("id") + "\t");
				sb.append(rs.getString("data") + "\n");
			}
			rs.close();
			return sb.toString();

		} catch (Exception e) {
			MLogger.warn("get all todolist" + e);
		}
		return "Fehler";
	}

	/**
	 * @param t
	 * @return
	 */
	private static String add(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			//@formatter:off
			stmt.execute("INSERT OR REPLACE INTO todolist (data, editby, date) VALUES " +
					"('" + t.getMessageStringSecond() +
					"','" + t.getFrom() +
					"','"+ t.getDate() +
					"')");
			//@formatter:on
			return "gespeichert";
		} catch (Exception e) {
			MLogger.warn("add todo " + e);
			return "Fehler";
		}
	}

	/**
	 * @return
	 */
	private static String count() {
		try {
			Statement stmt = MsqlLite.getDB();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM todolist;");
			return rs.getString("COUNT(*)") + " Einträge in der DB";
		} catch (Exception e) {
			MLogger.warn("count todolist " + e);
			return null;
		}
	}

	/**
	 * @param t
	 * @return
	 */
	private static String del(Telegram t) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("DELETE FROM todolist WHERE id = " + t.getMessageInt(2));
			return "DEL";
		} catch (Exception e) {
			MLogger.warn("delete todo" + e);
			return "Fehler";
		}
	}

}
