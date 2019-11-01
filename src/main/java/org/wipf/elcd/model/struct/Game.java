package org.wipf.elcd.model.struct;

/**
 * @author wipf
 *
 */
public class Game {
	private Integer nChatID;
	private Integer nDate;
	private String sType;

	public Integer getChatID() {
		return nChatID;
	}

	public void setChatID(Integer nChatID) {
		this.nChatID = nChatID;
	}

	public Integer getDate() {
		return nDate;
	}

	public void setDate(Integer nDate) {
		this.nDate = nDate;
	}

	public String getType() {
		return sType;
	}

	public void setType(String sType) {
		this.sType = sType;
	}
}
