package org.wipf.elcd.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MUhr {
	public static void uhr() {
		Integer failcount = 0;
		MElcd.write(0, 0, "Uhr: ");
		while (true) {
			// SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
			String datess = date2.format(new Date());

			if (!MElcd.write(0, 5, datess)) {
				failcount++;
				if (failcount > 10) {
					System.out.println("Stoppe Uhr -> 10x fail");
					return;
				}

			} else {
				failcount = 0;
			}
			MWipf.sleep(1000);
		}
	}
}
