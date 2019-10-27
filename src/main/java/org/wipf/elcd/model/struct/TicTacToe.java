package org.wipf.elcd.model.struct;

import java.util.Random;

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
	public Boolean setKordinate(int x, int y, Character c) {
		if (this.tttFeld[x][y] == 'F') // frei char
		{
			this.tttFeld[x][y] = c;
			return true;
		}
		return false;
	}

	public Boolean setByNummer(int n, Character c) {
		switch (n) {
		case 1:
			this.tttFeld[0][0] = c;
			break;
		case 2:
			this.tttFeld[0][1] = c;
			break;
		case 3:
			this.tttFeld[0][2] = c;
			break;
		case 4:
			this.tttFeld[1][0] = c;
			break;
		case 5:
			this.tttFeld[1][1] = c;
			break;
		case 6:
			this.tttFeld[1][2] = c;
			break;
		case 7:
			this.tttFeld[2][0] = c;
			break;
		case 8:
			this.tttFeld[2][1] = c;
			break;
		case 9:
			this.tttFeld[2][2] = c;
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * @param c
	 * @return
	 */
	public Boolean cpuSetzen(Character c) {
		Random zufall = new Random();
		for (int x = 0; x < 3; x++) {
			if (tttFeld[x][0] == c && c == tttFeld[x][1]) {
				return setKordinate(x, 2, c);
			}
			if (tttFeld[x][1] == c && c == tttFeld[x][2]) {
				return setKordinate(x, 0, c);
			}
			if (tttFeld[x][0] == c && c == tttFeld[x][2]) {
				return setKordinate(x, 1, c);
			}
		}
		for (int y = 0; y < 3; y++) {
			if (tttFeld[0][y] == c && c == tttFeld[1][y]) {
				return setKordinate(2, y, c);
			}
			if (tttFeld[1][y] == c && c == tttFeld[2][y]) {
				return setKordinate(0, y, c);
			}
			if (tttFeld[0][y] == c && c == tttFeld[2][y]) {
				return setKordinate(1, y, c);
			}
		}
		if (tttFeld[0][0] == c && c == tttFeld[1][1]) {
			return setKordinate(2, 2, c);
		}
		if (tttFeld[1][1] == c && c == tttFeld[2][2]) {
			return setKordinate(0, 0, c);
		}
		if (tttFeld[2][2] == c && c == tttFeld[1][1]) {
			return setKordinate(1, 1, c);
		}
		return setKordinate(zufall.nextInt(3), zufall.nextInt(3), c);
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
