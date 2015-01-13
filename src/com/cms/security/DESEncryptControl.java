package com.cms.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class DESEncryptControl {

	public static byte[] Decrypt(byte key[], byte data[]) {
		try {
			byte[] k = new byte[24];

			int len = data.length;
			if (data.length % 8 != 0) {
				len = data.length - data.length % 8 + 8;
			}
			byte[] needData = null;
			if (len != 0)
				needData = new byte[len];

			for (int i = 0; i < len; i++) {
				needData[i] = 0x00;
			}

			System.arraycopy(data, 0, needData, 0, data.length);

			if (key.length == 16) {
				System.arraycopy(key, 0, k, 0, key.length);
				System.arraycopy(key, 0, k, 16, 8);
			} else {
				System.arraycopy(key, 0, k, 0, 24);
			}
			DESedeKeySpec ks = new DESedeKeySpec(k);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
			SecretKey ky = kf.generateSecret(ks);

			Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
			c.init(Cipher.DECRYPT_MODE, ky);
			return c.doFinal(needData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static byte[] Encrypt(byte[] key, byte[] data)
	{
		try {
			byte[] k = new byte[24];

			int len = data.length;
			if (data.length % 8 != 0) {
				len = data.length - data.length % 8 + 8;
			}
			byte[] needData = null;
			if (len != 0)
				needData = new byte[len];

			for (int i = 0; i < len; i++) {
				needData[i] = 0x00;
			}

			System.arraycopy(data, 0, needData, 0, data.length);

			if (key.length == 16) {
				System.arraycopy(key, 0, k, 0, key.length);
				System.arraycopy(key, 0, k, 16, 8);
			} else {
				System.arraycopy(key, 0, k, 0, 24);
			}
			DESedeKeySpec ks = new DESedeKeySpec(k);
			SecretKeyFactory kf = SecretKeyFactory.getInstance("DESede");
			SecretKey ky = kf.generateSecret(ks);

			Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
			c.init(Cipher.ENCRYPT_MODE, ky);
			return c.doFinal(needData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
}
