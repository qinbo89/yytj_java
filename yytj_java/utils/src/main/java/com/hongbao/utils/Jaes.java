package com.hongbao.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
 
public class Jaes
{
   
 
	public static String encodestr(String data, String key) {
		
		try {
			String iv = "0102030405060708"; // ��ֵ�����޸�

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			 
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			return new sun.misc.BASE64Encoder().encode(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static String decodestr(String data, String key) {
		try{
            String iv = "0102030405060708";// ��ֵ�����޸�
             
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
             
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
             
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
  
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

	
}