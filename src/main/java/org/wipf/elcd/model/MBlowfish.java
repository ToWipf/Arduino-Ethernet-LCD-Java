package org.wipf.elcd.model;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MBlowfish {

	public static String encrypt(String sIn) throws Exception {
		byte[] keyData = ("superKey42").getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] hasil = cipher.doFinal(sIn.getBytes());
		return Base64.getEncoder().encodeToString(hasil);
	}

	public static String decrypt(String string) throws Exception {
		byte[] keyData = ("superKey42").getBytes();
		SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] hasil = cipher.doFinal(Base64.getDecoder().decode(string));
		return new String(hasil);
	}
}