package org.wipf.elcd.model;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class MConfig {
	public static String readConfigFile(String sPath) {
		try {
			return Resources.toString(Resources.getResource(sPath), Charsets.UTF_8);
			// return
			// Resources.toString(Resources.getResource("/src/main/resources/wipfConfig/" +
			// sPath), Charsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return "F";
		}
	}
}
