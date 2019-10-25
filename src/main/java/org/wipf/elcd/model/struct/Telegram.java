package org.wipf.elcd.model.struct;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.wipf.elcd.model.MLogger;

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

	public String getMessageWord(int nWort) {
		int n = 0;
		for (String part : sMessage.split(" ")) {
			if (n == nWort) {
				return part;
			}
			n++;
		}
		return null;
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
		try {
			this.sAntwort = URLEncoder.encode(sAntwort, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.sAntwort = "FAIL";
			MLogger.err("setAntwort" + e);
		}
	}

	public void setAntwortPlain(String sAntwort) {
		this.sAntwort = sAntwort;
	}
}
