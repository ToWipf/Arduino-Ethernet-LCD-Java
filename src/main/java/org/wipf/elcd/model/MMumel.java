package org.wipf.elcd.model;

import java.sql.Statement;

import org.wipf.elcd.model.struct.Mumel;
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
			stmt.executeUpdate(
					"CREATE TABLE IF NOT EXISTS m (chatid INTEGER UNIQUE, feld TEXT, msgdate INTEGER, type TEXT);");

		} catch (Exception e) {
			MLogger.warn("initDB tictactoe " + e);
		}
	}

	/**
	 * @param sTelegramSetTo
	 * @return
	 */
	public static String input(Telegram t) {
		return null;
//		TicTacToe ttt = null;
//		String sAction = t.getMessageWord(1);
//		if (sAction == null) {
//			return "Anleitung mit TicTacToe help";
//		}
//
//		switch (sAction) {
//		case "setzen":
//		case "setze":
//		case "set":
//		case "se":
//		case "s":
//			String sHelpAuswertung;
//			// Lade spiel
//			ttt = loadTicTacToe(t.getChatID());
//			if (ttt == null) {
//				return "Es wurde noch kein Spiel gestartet";
//			}
//			// auswertung
//			sHelpAuswertung = helpAuswertung(ttt);
//			if (sHelpAuswertung != null) {
//				return sHelpAuswertung;
//			}
//			ttt.setByTelegram(t);
//			// setze feld
//			if (!ttt.setByNummer(t.getMessageInt(2), 'X')) {
//				return "Feld konnte nicht gesetzt werden";
//			} else {
//				saveTicTacToe(ttt); // save game
//			}
//			// auswertung
//			sHelpAuswertung = helpAuswertung(ttt);
//			if (sHelpAuswertung != null) {
//				return sHelpAuswertung;
//			}
//			// set cpu
//			if (!ttt.cpuSetzen('O')) {
//				return "CPU konnte nicht MsqlLite.setzen";
//			} else {
//				saveTicTacToe(ttt); // save game
//			}
//			// auswertung
//			sHelpAuswertung = helpAuswertung(ttt);
//			if (sHelpAuswertung != null) {
//				return sHelpAuswertung;
//			}
//			// Spielfeld ausgeben
//			return ttt.tttToNiceString();
//		case "new":
//		case "neu":
//		case "n":
//			ttt = new TicTacToe("FFFFFFFFF");
//			ttt.setByTelegram(t);
//			saveTicTacToe(ttt);
//			return "Setzen mit 'ttt se NR'\n\n" + ttt.tttToNiceString();
//		case "show":
//		case "sh":
//			ttt = loadTicTacToe(t.getChatID());
//			if (ttt == null) {
//				return "Es wurde noch kein Spiel gestartet"; // Diesen fall gibt es nicht wenn autocreate new game
//			}
//			return ttt.tttToNiceString();
//		default:
//			return "Anleitung:\n\nttt neu: Neues Spiel\nttt setze NR: Setzen\nttt show: Zeige feld";
//		}
	}

	/**
	 * @param ttt
	 * @return
	 */
	private static Boolean save(Mumel m) {
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
	private static Mumel load(Integer nChatid) {
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
