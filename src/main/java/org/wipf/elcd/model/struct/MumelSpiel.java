package org.wipf.elcd.model.struct;

import java.util.ArrayList;
import java.util.List;

import org.wipf.elcd.model.base.MLogger;

/**
 * @author wipf
 *
 */
public class MumelSpiel extends Game {

	private List<MumelSpieler> lmsp = new ArrayList<>();
	private Integer nWerIstDran;

	public List<MumelSpieler> getMumelSpielerliste() {
		return lmsp;
	}

	public void setMumelSpielerliste(List<MumelSpieler> lmsp) {
		this.lmsp = lmsp;
	}

	public void setMumelSpielerliste(String sImportString) {
		try {

			// for (String part : sImportString.split(" ")) {

			// TODO find by id und add to list
//				MumelSpieler msp = new MumelSpieler();
//				this.lmsp.add(msp);

			// }
		} catch (Exception e) {
			MLogger.warn("sgetMessageWord " + e);
		}

	}

	public Integer getWerIstDran() {
		return nWerIstDran;
	}

	public void setWerIstDran(Integer nWerIstDran) {
		this.nWerIstDran = nWerIstDran;
	}

}
