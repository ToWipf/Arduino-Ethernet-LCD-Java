package org.wipf.elcd.model.task;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TimerTask;

import org.wipf.elcd.model.MTelegram;
import org.wipf.elcd.model.MWitz;

public class TaskTelegram extends TimerTask {
	public TaskTelegram() {
		// Some stuffs
	}

	@Override
	public void run() {
		System.out.println("Witz von Heute:");
		String sWitz;
		try {
			sWitz = URLEncoder.encode(MWitz.getWitz(), "UTF-8");
			MTelegram.sendeTele(sWitz);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
