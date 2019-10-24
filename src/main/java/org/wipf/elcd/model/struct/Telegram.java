package org.wipf.elcd.model.struct;

import java.net.URLEncoder;

public class Telegram {
	private Integer nMid;
	private String sMessage;
	private String sAntwort;
	private Integer nChatID;

	public Telegram() {
		this.nMid = 0;
		this.sMessage = "";
	}

	public String getMessage() {
		return sMessage;
	}

	public void setMessage(String sMessage) {
		this.sMessage = sMessage;
	}

	public Integer getMid() {
		return nMid;
	}

	public void setMid(Integer nMid) {
		this.nMid = nMid;
	}

	public Integer getChatID() {
		return nChatID;
	}

	public void setChatID(Integer nChatID) {
		this.nChatID = nChatID;
	}

	public String getAntwort() {
		return sAntwort;
	}

	public void setAntwort(String sAntwort) {
		this.sAntwort = URLEncoder.encode(sAntwort);
	}
}
