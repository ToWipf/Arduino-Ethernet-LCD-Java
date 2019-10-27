package org.wipf.elcd.model.struct;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.wipf.elcd.model.MLogger;

/**
 * @author wipf
 *
 */
public class Telegram {
	private Integer nMid;
	private String sMessage;
	private String sAntwort;
	private Integer nChatID;
	private Integer nDate;
	private String sType;
	private String sFrom;

	public Telegram() {
		this.nMid = 0;
		this.sMessage = "";
	}

	public String getMessage() {
		return sMessage;
	}

	/**
	 * @param nStelle
	 * @return
	 */
	public String getMessageWord(int nStelle) {
		try {
			int n = 0;
			for (String part : sMessage.split(" ")) {
				if (n == nStelle) {
					return part;
				}
				n++;
			}
		} catch (Exception e) {
			MLogger.warn("sgetMessageWord" + e);
		}
		return null;
	}

	/**
	 * @param nStelle
	 * @return
	 */
	public Integer getMessageInt(int nStelle) {
		try {
			return Integer.parseInt(getMessageWord(nStelle));
		} catch (Exception e) {
			return null;
		}
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

	/**
	 * @param sAntwort
	 */
	public void setAntwort(String sAntwort) {
		try {
			this.sAntwort = URLEncoder.encode(sAntwort, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.sAntwort = "FAIL";
			MLogger.warn("setAntwort" + e);
		}
	}

	public void setAntwortPlain(String sAntwort) {
		this.sAntwort = sAntwort.replace("+", "%2B");
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

	public String getFrom() {
		return sFrom;
	}

	public void setFrom(String sFrom) {
		this.sFrom = sFrom;
	}

}
