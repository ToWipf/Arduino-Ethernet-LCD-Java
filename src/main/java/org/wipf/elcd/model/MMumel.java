package org.wipf.elcd.model;

import java.sql.Statement;

import org.wipf.elcd.model.struct.MumelSpiel;

/**
 * @author wipf
 *
 */
public class MMumel {

	/**
	 * 
	 */
	public static void initDB() {
		try {
			Statement stmt = MsqlLite.getDB();

			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS mumelSpieler (" + " chatid INTEGER," + " playerid INTEGER,"
					+ " leben INTEGER," + " feuer INTEGER," + " blitz INTEGER," + " wasser INTEGER,"
					+ " doppelfeuer INTEGER," + " schwarzerstein INTEGER," + " geld INTEGER," + " hausbrennen INTEGER,"
					+ " bunterstein INTEGER);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS mumelSpiel (" + " chatid INTEGER UNIQUE," + " playerids TEXT"
					+ " weristdran INTEGER);");

		} catch (Exception e) {
			MLogger.warn("initDB mumel " + e);
		}
	}

	/**
	 * @param ttt
	 * @return
	 */
	private static Boolean saveGame(MumelSpiel m) {
		return null;
//		try {
//			Statement stmt = MsqlLite.getDB();
//			stmt.execute(
//					"INSERT OR REPLACE INTO tictactoe (chatid, feld, msgdate, type) VALUES " + "('" + ttt.getChatID()
//							+ "','" + ttt.getFieldString() + "','" + ttt.getDate() + "','" + ttt.getType() + "')");
//			return true;
//		} catch (Exception e) {
//			MLogger.warn("setTicTacToe " + e);
//			return false;
//		}
	}

	/**
	 * @param sChatid
	 * @return
	 */
	private static MumelSpiel loadGame(Integer nChatid) {
		return null;
//		try {
//			Statement stmt = MsqlLite.getDB();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM tictactoe WHERE chatid = '" + nChatid + "';");
//			TicTacToe ttt = new TicTacToe(rs.getString("feld"));
//			// ttt.setChatID(rs.getInt("chatid")); weitere felder sind nicht nötig -> werden
//			// neu befüllt
//			rs.close();
//			return ttt;
//		} catch (Exception e) {
//			// Kann vorkommen wenn kein spiel aktiv ist
//			// MLogger.warn("getTicTacToe " + e);
//		}
//		return null;
	}
}
