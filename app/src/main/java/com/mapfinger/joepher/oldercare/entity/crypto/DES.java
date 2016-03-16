package com.mapfinger.joepher.oldercare.entity.crypto;

import android.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2016/1/10.
 */
public class DES {
	private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

	public static String encryptDES(String encryptText, String encryptKey) throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptText.getBytes());

		return Base64.encode(encryptedData);
	}

	public static String decryptDES(String decryptText, String decryptKey) throws Exception {
		byte[] byteMi = Base64.decode(decryptText);

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("/DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte[] decryptedData = cipher.doFinal(byteMi);

		return new String(decryptedData);
	}
}
