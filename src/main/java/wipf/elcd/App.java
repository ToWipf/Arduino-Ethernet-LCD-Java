package wipf.elcd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Fan;
import com.profesorfalken.jsensors.model.sensors.Temperature;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello World!");

		// testLcd();
		// testSens();
		testSensCpu();

		System.exit(0);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void testSens() {
		Components components = JSensors.get.components();

		List<Cpu> cpus = components.cpus;
		if (cpus != null) {
			for (final Cpu cpu : cpus) {
				System.out.println("Found CPU component: " + cpu.name);
				if (cpu.sensors != null) {
					System.out.println("Sensors: ");

					// Print temperatures
					List<Temperature> temps = cpu.sensors.temperatures;
					for (final Temperature temp : temps) {
						System.out.println(temp.name + ": " + temp.value + " C");
					}

					// Print fan speed
					List<Fan> fans = cpu.sensors.fans;
					for (final Fan fan : fans) {
						System.out.println(fan.name + ": " + fan.value + " RPM");
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private static void testSensCpu() {
		Components components = JSensors.get.components();

		List<Cpu> cpus = components.cpus;
		if (cpus != null) {
			for (final Cpu cpu : cpus) {
				System.out.println("Found CPU component: " + cpu.name);
				if (cpu.sensors != null) {
					System.out.println("Sensors: ");

					// Print temperatures
					List<Temperature> temps = cpu.sensors.temperatures;

					if (temps.size() != 0) {
						for (Temperature temp : temps) {
							System.out.println(temp.name + ": " + temp.value + " C");
						}

					}

				}
			}
		}
		System.out.println(components);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void testLcd() {
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
