package org.wipf.elcd.model;

import org.wipf.elcd.model.struct.Telegram;
import org.wipf.elcd.model.struct.TicTacToe;

/**
 * @author wipf
 *
 */
public class MTicTacToe {

	/**
	 * @param sTelegramSetTo
	 * @return
	 */
	public static String input(Telegram t) {
		TicTacToe ttt = null;
		String sAction = t.getMessageWord(1);
		if (sAction == null) {
			return "Anleitung mit TicTacToe help";
		}

		switch (sAction) {
		case "setzen":
		case "setze":
		case "set":
		case "s":
			String sHelpAuswertung;
			ttt = MsqlLite.loadTicTacToe(t.getChatID());
			if (ttt == null) {
				return "Es wurde noch kein Spiel gestartet";
			}
			ttt.setByTelegram(t);
			if (!ttt.setByNummer(t.getMessageInt(2), 'X')) {
				return "Feld konnte nicht gesetzt werden";
			} else {
				// save game
				MsqlLite.saveTicTacToe(ttt);
			}
			sHelpAuswertung = helpAuswertung(ttt);
			if (sHelpAuswertung != null) {
				return sHelpAuswertung;
			}

			if (!ttt.cpuSetzen('O')) {
				return "CPU konnte nicht setzen";
			} else {
				// save game
				MsqlLite.saveTicTacToe(ttt);
			}
			sHelpAuswertung = helpAuswertung(ttt);
			if (sHelpAuswertung != null) {
				return sHelpAuswertung;
			}

			MsqlLite.saveTicTacToe(ttt);

			return ttt.tttToNiceString();
		case "new":
		case "neu":
		case "n":
			ttt = new TicTacToe("FFFFFFFFF");
			ttt.setByTelegram(t);
			MsqlLite.saveTicTacToe(ttt);
			return "Setzen mit 'ttt se NR\n\n" + ttt.tttToNiceString();
		case "show":
		case "sh":
			ttt = MsqlLite.loadTicTacToe(t.getChatID());
			if (ttt == null) {
				return "Es wurde noch kein Spiel gestartet"; // Diesen fall gibt es nicht wenn autocreate new game
			}
			return helpAuswertung(ttt);
		default:
			return "Anleitung:\n\nttt neu: Neues Spiel\nttt setze NR: Setzen\nttt show: Zeige feld";
		}
	}

	/**
	 * @param ttt
	 * @return
	 */
	private static String helpAuswertung(TicTacToe ttt) {
		Character win = ttt.auswertung();
		if (win != null) {
			if (win == 'U') {
				return "Unentschieden\n\n" + ttt.tttToNiceString();
			} else if (win == 'X') {
				return "Du hat gewonnen\n\n" + ttt.tttToNiceString();
			} else if (win == 'O') {
				return "Du hat verloren\n\n" + ttt.tttToNiceString();
			}
		}
		return null;
	}

}
