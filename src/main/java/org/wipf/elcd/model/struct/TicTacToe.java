package org.wipf.elcd.model.struct;

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
	public void setKordinate(int x, int y, Character c) {
		this.tttFeld[x][y] = c;

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

	public void cpuSetzen() {

	}

	public void auswertung() {

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
