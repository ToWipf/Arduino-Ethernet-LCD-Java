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
		case "setze":
		case "set":
		case "s":
			Character win;
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
			win = ttt.auswertung();
			if (win != null && win != 'F') {
				return "Du hast gewonnen:\n\n" + ttt.tttToNiceString();
			}

			if (!ttt.cpuSetzen('O')) {
				return "CPU konnte nicht setzen";
			} else {
				// save game
				MsqlLite.saveTicTacToe(ttt);
			}

			win = ttt.auswertung();
			if (win != null && win != 'F') {
				return "Leider verloren\n\n" + ttt.tttToNiceString();
			}
			MsqlLite.saveTicTacToe(ttt);

			return ttt.tttToNiceString();
		case "new":
		case "neu":
		case "n":
			ttt = new TicTacToe("FFFFFFFFF");
			ttt.setByTelegram(t);
			MsqlLite.saveTicTacToe(ttt);
			return ttt.tttToString();
		case "sh":
		case "show":
			ttt = MsqlLite.loadTicTacToe(t.getChatID());
			if (ttt == null) {
				return "Es wurde noch kein Spiel gestartet"; // Diesen fall gibt es nicht wenn autocreate new game
			}
			return ttt.tttToNiceString();
		default:
			return "Anleitung:\n\nttt neu: Neues Spiel\nttt setze NR: Setzen\nttt show: Zeige feld";
		}

	}

}
