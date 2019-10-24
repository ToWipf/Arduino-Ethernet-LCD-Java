package org.wipf.elcd.model.struct;

public class Telegram {
	private Integer nMid;
	private String sMessage;

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
}
