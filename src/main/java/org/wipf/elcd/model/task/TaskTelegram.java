package org.wipf.elcd.model.task;

//import java.net.URLEncoder;
import java.util.TimerTask;

import org.wipf.elcd.model.MTelegram;

public class TaskTelegram extends TimerTask {

	@Override
	public void run() {
		try {
			MTelegram.readUpdateFromTelegram();
			// String sWitz;
			// sWitz = URLEncoder.encode(MWitz.getWitz(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
