package org.wipf.elcd.model.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wipf
 *
 */
public class MumelSpiel extends Game {

	private List<MumelSpieler> lmsp = new ArrayList<>();
	private Integer nWerIstDran;

	public List<MumelSpieler> getLmsp() {
		return lmsp;
	}

	public void setLmsp(List<MumelSpieler> lmsp) {
		this.lmsp = lmsp;
	}

	public Integer getWerIstDran() {
		return nWerIstDran;
	}

	public void setWerIstDran(Integer nWerIstDran) {
		this.nWerIstDran = nWerIstDran;
	}

}
