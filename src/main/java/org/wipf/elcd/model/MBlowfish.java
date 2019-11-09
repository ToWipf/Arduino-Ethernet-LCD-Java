package org.wipf.elcd.model;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.wipf.elcd.app.MainApp;

/**
 * @author wipf
 *
 */
public class MBlowfish {

	/**
	 * @param sIn
	 * @return
	 */
	public static String encrypt(String sIn) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec((MainApp.sKey).getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] hasil = cipher.doFinal(sIn.getBytes());
			return Base64.getEncoder().encodeToString(hasil);
		} catch (Exception e) {
			return "fail";
		}
	}

	/**
	 * @param sIn
	 * @return
	 */
	public static String decrypt(String sIn) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec((MainApp.sKey).getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] hasil = cipher.doFinal(Base64.getDecoder().decode(sIn));
			return new String(hasil);
		} catch (Exception e) {
			return "fail";
		}
	}
}