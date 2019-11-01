package org.wipf.elcd.model;

import java.sql.Statement;

import org.wipf.elcd.model.struct.MumelSpiel;
import org.wipf.elcd.model.struct.MumelSpieler;
import org.wipf.elcd.model.struct.Telegram;

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
					+ " doppelfeuer INTEGER," + " doppelwasser INTEGER," + " schwarzerstein INTEGER," + " geld INTEGER,"
					+ " hausbrennen INTEGER," + " bunterstein INTEGER, UNIQUE(chatid,playerid));");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS mumelSpiel (" + " chatid INTEGER UNIQUE," + " playerids TEXT"
					+ " weristdran INTEGER);");

		} catch (Exception e) {
			MLogger.warn("initDB mumel " + e);
		}
	}

	/**
	 * @return
	 */
	public static String playMumel(Telegram t) {
		try {
			MumelSpieler mspl = new MumelSpieler();
			mspl.setByTelegram(t);
			mspl.newFill();
			savePlayer(mspl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "MUMEL";
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
	 * @param mspl
	 * @return
	 */
	private static Boolean savePlayer(MumelSpieler mspl) {
		try {
			Statement stmt = MsqlLite.getDB();
			stmt.execute("INSERT OR REPLACE INTO mumelSpieler "
					+ "(chatid, playerid, leben, feuer, blitz, wasser, doppelfeuer, doppelwasser, schwarzerstein, geld,  hausbrennen, bunterstein )"
					+ " VALUES " + "('" + mspl.getChatID() + "','" + mspl.getPlayerId() + "','" + mspl.getLeben()
					+ "','" + mspl.getFeuer() + "','" + mspl.getBlitz() + "','" + mspl.getWasser() + "','"
					+ mspl.getDoppelFeuer() + "','" + mspl.getDoppelWasser() + "','" + mspl.getSchwarzerStein() + "','"
					+ mspl.getGeld() + "','" + mspl.getHausBrennen() + "','" + mspl.getBunterStein() + "')");
			return true;
		} catch (Exception e) {
			MLogger.warn("mumel save " + e);
			return false;
		}
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
