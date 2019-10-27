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
		switch (t.getMessageWord(1)) {
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
				return "Feld ist bereits belegt";
			}
			win = ttt.auswertung();
			if (win != null && win != 'F') {
				return "Gewonnen hat: " + win;
			}
			for (int n = 0; n < 15; n++) {
				if (ttt.cpuSetzen('O')) {
					break;
				}
				if (n > 14) {
					return "CPU konnte nicht setzen";
				}
			}
			win = ttt.auswertung();
			if (win != null && win != 'F') {
				return "Gewonnen hat: " + win;
			}
			MsqlLite.saveTicTacToe(ttt);
			break;
		case "new":
		case "neu":
		case "n":
			ttt = new TicTacToe("FFFFFFFFF");
			ttt.setByTelegram(t);
			MsqlLite.saveTicTacToe(ttt);
			break;

		default:
			return "Fehler:\n\n ttt neu Neues Spiel\nttt setze FELDNUMMER\n";
		}

		return ttt.tttToString();

	}

	/**
	 * @return
	 */
	private static TicTacToe setzen(TicTacToe tttFeld, int x, int y, Character c) {
		tttFeld.setKordinate(x, y, c);
		return tttFeld;
	}
}
