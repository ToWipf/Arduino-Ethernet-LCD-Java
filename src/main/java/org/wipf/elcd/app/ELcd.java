package org.wipf.elcd.app;

import org.wipf.elcd.model.MElcd;
import org.wipf.elcd.model.MUhr;

public class ELcd extends Thread {

	public void start() {
		ELcd myThread = new ELcd();
		myThread.run();
	}

	public void run() {
		System.out.println("Start send to Lcd");
		MElcd.clear();
		MUhr.uhr();
	}
}
