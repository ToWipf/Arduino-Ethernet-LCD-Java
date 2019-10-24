package org.wipf.elcd.model.task;

//import java.net.URLEncoder;
import java.util.TimerTask;

import org.wipf.elcd.model.MTelegram;

public class TaskTelegram extends TimerTask {
	public TaskTelegram() {
		// Some stuffs
	}

	@Override
	public void run() {
		try {
			System.out.println("Suche nach neuen Nachrichten:");
			// String sWitz;
			// sWitz = URLEncoder.encode(MWitz.getWitz(), "UTF-8");

			System.out.println(MTelegram.leseTele());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
