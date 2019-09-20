package org.wipf.elcd.model;

import java.net.InetAddress;

/**
 * @author wipf
 *
 */
public class MPing {

	/**
	 * @param sIP
	 * @return
	 */
	public static Boolean ping(String sIP) {
		try {
			InetAddress address = InetAddress.getByName(sIP);
			return address.isReachable(10000);

		} catch (Exception e) {
			return false;
		}
	}

}
