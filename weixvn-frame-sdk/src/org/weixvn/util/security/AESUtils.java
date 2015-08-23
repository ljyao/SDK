package org.weixvn.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	public static final String TAG = "AESUtils";
	public static final String VIPARA = "0102030405060708";
	private static final String CipherMode = "AES/CBC/PKCS5Padding";
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int KEY_LENGTH = 16;
	public static final String ENCODING = "UTF-8";

	/**
	 * AES加密数据
	 * 
	 * @param aesKey
	 *            aes加密秘钥
	 * @param plainText
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static String encrypt(String aesKey, String plainText)
			throws Exception {
		byte[] data = null;
		try {
			data = plainText.getBytes(ENCODING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data = encrypt(aesKey, data);
		String result = byte2hex(data);
		return result;
	}

	/**
	 * AES加密数据
	 * 
	 * @param aesKey
	 *            aes加密秘钥
	 * @param plainText
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encrypt(String aesKey, byte[] plainText) {
		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
		SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(CipherMode);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		try {
			return cipher.doFinal(plainText);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * AES解密数据
	 * 
	 * @param aesKey
	 *            aes加密秘钥
	 * @param cipherText
	 *            Base64编码的密文
	 * @return 解密的明文
	 * @throws Exception
	 */
	public static String decrypt(String aesKey, String cipherText)
			throws Exception {
		byte[] data = null;
		try {
			data = hex2byte(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data = decrypt(aesKey, data);
		if (data == null)
			return null;
		String result = null;
		try {
			result = new String(data, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * AES解密数据
	 * 
	 * @param aesKey
	 *            aes加密秘钥
	 * @param cipherText
	 *            密文
	 * @return 解密的明文
	 * @throws Exception
	 */
	public static byte[] decrypt(String aesKey, byte[] cipherText)
			throws Exception {
		IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
		SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance(CipherMode);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		try {
			return cipher.doFinal(cipherText);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 随机产生16位的秘钥
	 * 
	 * @return 16位的秘钥
	 */
	public static String genRandomKey() {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < KEY_LENGTH; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/** 字节数组转成16进制字符串 **/
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				sb.append("0");
			}
			sb.append(tmp);
		}
		return sb.toString().toUpperCase(); // 转成大写
	}

	/** 将hex字符串转换成字节数组 **/
	private static byte[] hex2byte(String inputString) {
		if (inputString == null || inputString.length() < 2) {
			return new byte[0];
		}
		inputString = inputString.toLowerCase();
		int l = inputString.length() / 2;
		byte[] result = new byte[l];
		for (int i = 0; i < l; ++i) {
			String tmp = inputString.substring(2 * i, 2 * i + 2);
			result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
		}
		return result;
	}
}