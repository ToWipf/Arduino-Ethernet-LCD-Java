package wipf.elcd;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 *
 */

public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		// dlcd.testRest();
		dlcd.clear();
		dlcd.goToLine(0);
		dlcd.text(" W I P F");
		dlcd.goToLine(1);
		dlcd.text("  W I P F");
		dlcd.goToLine(2);
		dlcd.text("   W I P F");
		dlcd.goToLine(3);
		dlcd.text("    W I P F");

		SimpleDateFormat date = new SimpleDateFormat("HH:mm");
		String date1 = date.format(new Date());
		System.out.println(date1);

		dlcd.goToLine(0);
		dlcd.text(date1);

		for (Integer n = 0; n < 100; n++) {
			// SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			SimpleDateFormat date2 = new SimpleDateFormat("HH:mm:ss");
			String datess = date2.format(new Date());
			System.out.println(datess);

			dlcd.goToLine(1);
			dlcd.text("Uhr:." + datess + " bei " + n);
			System.out.println(n);
		}

		for (Integer i = 0; i < 100; i++) {
			dlcd.goToLine(2);
			dlcd.text("Wipf Nr." + i + "!");
			System.out.println(i);
		}
	}

}
