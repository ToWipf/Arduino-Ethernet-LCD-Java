package org.wipf.elcd.model.struct;

import java.util.Random;

import org.wipf.elcd.model.MWipf;

/**
 * @author wipf
 *
 */
public class TicTacToe {

	private Character[][] tttFeld = new Character[3][3];
	private Integer nChatID;
	private Integer nDate;
	private String sType;

	/**
	 * 
	 */
	public TicTacToe() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				tttFeld[x][y] = ' ';
			}
		}
	}

	/**
	 * @param sFeld xxxoooxxx x o x o o
	 * 
	 */
	public TicTacToe(String sFeld) {
		int n = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {

				tttFeld[x][y] = sFeld.charAt(n);
				n++;
			}
		}
	}

	/**
	 * @return
	 */
	public String tttToString() {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				sb.append(tttFeld[x][y]);
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * @return
	 */
	public String tttToNiceString() {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (tttFeld[x][y].equals('X')) {
					sb.append("🐧\t");
				}
				if (tttFeld[x][y].equals('O')) {
					sb.append("👻\t");
				}
				if (tttFeld[x][y].equals('F')) {
					sb.append(MWipf.numberToSymbol(koordinateToNumber(x, y)) + "\t");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * @return
	 */
	public String getFieldString() {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				sb.append(tttFeld[x][y]);
			}
		}
		return sb.toString();
	}

	/**
	 * @param tttFeld
	 */
	public void setTttFeld(Character[][] tttFeld) {
		this.tttFeld = tttFeld;
	}

	/**
	 * @param x
	 * @param y
	 * @param s
	 */
	public Boolean setkoordinate(int x, int y, Character c) {
		if (this.tttFeld[x][y] != 'F') {
			return false;
		} else {
			this.tttFeld[x][y] = c;
			return true;
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	private Integer koordinateToNumber(Integer x, Integer y) {
		if (x == 0 && y == 0) {
			return 1;
		}
		if (x == 0 && y == 1) {
			return 2;
		}
		if (x == 0 && y == 2) {
			return 3;
		}
		if (x == 1 && y == 0) {
			return 4;
		}
		if (x == 1 && y == 1) {
			return 5;
		}
		if (x == 1 && y == 2) {
			return 6;
		}
		if (x == 2 && y == 0) {
			return 7;
		}
		if (x == 2 && y == 1) {
			return 8;
		}
		if (x == 2 && y == 2) {
			return 9;
		}
		return null;
	}

	public Boolean setByNummer(Integer n, Character c) {
		if (n == null) {
			return false;
		}
		switch (n) {
		case 1:
			return setkoordinate(0, 0, c);
		case 2:
			return setkoordinate(0, 1, c);
		case 3:
			return setkoordinate(0, 2, c);
		case 4:
			return setkoordinate(1, 0, c);
		case 5:
			return setkoordinate(1, 1, c);
		case 6:
			return setkoordinate(1, 2, c);
		case 7:
			return setkoordinate(2, 0, c);
		case 8:
			return setkoordinate(2, 1, c);
		case 9:
			return setkoordinate(2, 2, c);
		default:
			return false;
		}
	}

	/**
	 * @param c
	 * @return
	 */
	public Boolean cpuSetzen(Character c) {
		Random zufall = new Random();
		// Versuche zu Gewinnen
		for (int x = 0; x < 3; x++) {
			if (tttFeld[x][0] == c && c == tttFeld[x][1]) {
				if (setkoordinate(x, 2, c)) {
					return true;
				}
			}
			if (tttFeld[x][1] == c && c == tttFeld[x][2]) {
				if (setkoordinate(x, 0, c)) {
					return true;
				}
			}
			if (tttFeld[x][0] == c && c == tttFeld[x][2]) {
				if (setkoordinate(x, 1, c)) {
					return true;
				}
			}
		}
		for (int y = 0; y < 3; y++) {
			if (tttFeld[0][y] == c && c == tttFeld[1][y]) {
				if (setkoordinate(2, y, c)) {
					return true;
				}
			}
			if (tttFeld[1][y] == c && c == tttFeld[2][y]) {
				if (setkoordinate(0, y, c)) {
					return true;
				}
			}
			if (tttFeld[0][y] == c && c == tttFeld[2][y]) {
				if (setkoordinate(1, y, c)) {
					return true;
				}
			}
		}
		if (tttFeld[0][0] == c && c == tttFeld[1][1]) {
			if (setkoordinate(2, 2, c)) {
				return true;
			}
		}
		if (tttFeld[1][1] == c && c == tttFeld[2][2]) {
			if (setkoordinate(0, 0, c)) {
				return true;
			}
		}
		if (tttFeld[2][2] == c && c == tttFeld[1][1]) {
			if (setkoordinate(1, 1, c)) {
				return true;
			}
		}

		for (int n = 0; n < 15; n++) {
			if (setkoordinate(zufall.nextInt(3), zufall.nextInt(3), c)) {
				return true;
			}
		}
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (tttFeld[x][y] == 'F') {
					return setkoordinate(x, y, c);
				}
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public Character auswertung() {
		for (int x = 0; x < 3; x++) {
			if (tttFeld[x][0] == tttFeld[x][1] && tttFeld[x][0] == tttFeld[x][2]) {
				return tttFeld[x][0];
			}
		}
		for (int y = 0; y < 3; y++) {
			if (tttFeld[0][y] == tttFeld[1][y] && tttFeld[0][y] == tttFeld[2][y]) {
				return tttFeld[0][y];
			}
		}
		if (tttFeld[0][0] == tttFeld[1][1] && tttFeld[0][0] == tttFeld[2][2]) {
			return tttFeld[0][0];
		}
		if (tttFeld[0][2] == tttFeld[1][1] && tttFeld[0][2] == tttFeld[2][0]) {
			return tttFeld[0][2];
		}
		// Kein sieg
		return null;
	}

	public String getType() {
		return sType;
	}

	public void setType(String sType) {
		this.sType = sType;
	}

	public Integer getDate() {
		return nDate;
	}

	public void setDate(Integer nDate) {
		this.nDate = nDate;
	}

	public Integer getChatID() {
		return nChatID;
	}

	public void setChatID(Integer nChatID) {
		this.nChatID = nChatID;
	}

	public void setByTelegram(Telegram t) {
		this.nChatID = t.getChatID();
		this.nDate = t.getDate();
		this.sType = t.getType();
	}

}
