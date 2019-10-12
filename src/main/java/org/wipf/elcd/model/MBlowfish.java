package org.wipf.elcd.model;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MBlowfish {

	public static String sKey = "superKey42";

	/**
	 * @param sIn
	 * @return
	 */
	public static String encrypt(String sIn) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec((sKey).getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] hasil = cipher.doFinal(sIn.getBytes());
			return Base64.getEncoder().encodeToString(hasil);
		} catch (Exception e) {
			return "fail";
		}
	}

	/**
	 * @param string
	 * @return
	 */
	public static String decrypt(String string) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec((sKey).getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] hasil = cipher.doFinal(Base64.getDecoder().decode(string));
			return new String(hasil);
		} catch (Exception e) {
			return "fail";
		}
	}
}