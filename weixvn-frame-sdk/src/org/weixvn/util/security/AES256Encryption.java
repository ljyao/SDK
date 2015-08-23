package org.weixvn.util.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES256Encryption {

	/**
	 * 密钥算法 java6支持56位密钥，bouncycastle支持64位
	 * */
	public static final String KEY_ALGORITHM = "AES";

	/**
	 * 加密/解密算法/工作模式/填充方式
	 * 
	 * JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
	 * */
	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

	/**
	 * 
	 * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
	 * 
	 * @return byte[] 二进制密钥
	 * */
	public static byte[] initkey() throws Exception {

		return new byte[] { 0x73, 0x6d, 0x41, 0x77, 0x2e, 0x60, 0x2f, 0x3f,
				0x45, 0x53, 0x2d, 0x71, 0x49, 0x2f, 0x39, 0x26, 0x73, 0x6d,
				0x41, 0x77, 0x2e, 0x60, 0x2f, 0x3f, 0x45, 0x53, 0x2d, 0x71,
				0x49, 0x2f, 0x39, 0x26 };

	}

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * */
	public static SecretKey toKey(byte[] key) throws Exception {
		// 实例化DES密钥
		// 生成密钥
		SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
		return secretKey;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密后的数据
	 * */
	public static byte[] encrypt(byte[] data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedData = cipher.doFinal(data);

		return encryptedData;
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密后的数据
	 * */
	public static byte[] decrypt(byte[] data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] originalByteArray = cipher.doFinal(data);

		return originalByteArray;

	}

}
