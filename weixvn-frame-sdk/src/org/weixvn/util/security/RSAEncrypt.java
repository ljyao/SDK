package org.weixvn.util.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Decoder.BASE64Decoder;

public class RSAEncrypt {
	private static final String CipherMode = "RSA/ECB/PKCS1Padding";

	/**
	 * 绉侀挜
	 */
	public static RSAPrivateKey privateKey;

	/**
	 * 鍏挜
	 */
	public static RSAPublicKey publicKey;

	/**
	 * 瀛楄妭鏁版嵁杞瓧绗︿覆涓撶敤闆嗗悎
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 鑾峰彇绉侀挜
	 * 
	 * @return 褰撳墠鐨勭閽ュ璞�
	 */
	public RSAPrivateKey getPrivateKey() {

		return privateKey;
	}

	/**
	 * 鑾峰彇鍏挜
	 * 
	 * @return 褰撳墠鐨勫叕閽ュ璞�
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 闅忔満鐢熸垚瀵嗛挜瀵�
	 */
	public static void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		privateKey = (RSAPrivateKey) keyPair.getPrivate();
		publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 浠庢枃浠朵腑杈撳叆娴佷腑鍔犺浇鍏挜
	 * 
	 * @param in
	 *            鍏挜杈撳叆娴�
	 * @throws Exception
	 *             鍔犺浇鍏挜鏃朵骇鐢熺殑寮傚父
	 */
	public static void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("鍏挜鏁版嵁娴佽鍙栭敊璇�");
		} catch (NullPointerException e) {
			throw new Exception("鍏挜杈撳叆娴佷负绌�");
		}
	}

	/**
	 * 浠庡瓧绗︿覆涓姞杞藉叕閽�
	 * 
	 * @param publicKeyStr
	 *            鍏挜鏁版嵁瀛楃涓�
	 * @throws Exception
	 *             鍔犺浇鍏挜鏃朵骇鐢熺殑寮傚父
	 */
	public static void loadPublicKey(String publicKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("鏃犳绠楁硶");
		} catch (InvalidKeySpecException e) {
			throw new Exception("鍏挜闈炴硶");
		} catch (IOException e) {
			throw new Exception("鍏挜鏁版嵁鍐呭璇诲彇閿欒");
		} catch (NullPointerException e) {
			throw new Exception("鍏挜鏁版嵁涓虹┖");
		}
	}

	/**
	 * 浠庢枃浠朵腑鍔犺浇绉侀挜
	 * 
	 * @param keyFileName
	 *            绉侀挜鏂囦欢鍚�
	 * @return 鏄惁鎴愬姛
	 * @throws Exception
	 */
	public static void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("绉侀挜鏁版嵁璇诲彇閿欒");
		} catch (NullPointerException e) {
			throw new Exception("绉侀挜杈撳叆娴佷负绌�");
		}
	}

	public static void loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("鏃犳绠楁硶");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("绉侀挜闈炴硶");
		} catch (IOException e) {
			throw new Exception("绉侀挜鏁版嵁鍐呭璇诲彇閿欒");
		} catch (NullPointerException e) {
			throw new Exception("绉侀挜鏁版嵁涓虹┖");
		}
	}

	/**
	 * 鍔犲瘑杩囩▼
	 * 
	 * @param publicKey
	 *            鍏挜
	 * @param plainTextData
	 *            鏄庢枃鏁版嵁
	 * @return
	 * @throws Exception
	 *             鍔犲瘑杩囩▼涓殑寮傚父淇℃伅
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("鍔犲瘑鍏挜涓虹┖, 璇疯缃�");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(CipherMode);// , new
													// BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("鏃犳鍔犲瘑绠楁硶");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("鍔犲瘑鍏挜闈炴硶,璇锋鏌�");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("鏄庢枃闀垮害闈炴硶");
		} catch (BadPaddingException e) {
			throw new Exception("鏄庢枃鏁版嵁宸叉崯鍧�");
		}
	}

	/**
	 * 瑙ｅ瘑杩囩▼
	 * 
	 * @param privateKey
	 *            绉侀挜
	 * @param cipherData
	 *            瀵嗘枃鏁版嵁
	 * @return 鏄庢枃
	 * @throws Exception
	 *             瑙ｅ瘑杩囩▼涓殑寮傚父淇℃伅
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("瑙ｅ瘑绉侀挜涓虹┖, 璇疯缃�");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(CipherMode);// , new
													// BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("鏃犳瑙ｅ瘑绠楁硶");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("瑙ｅ瘑绉侀挜闈炴硶,璇锋鏌�");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("瀵嗘枃闀垮害闈炴硶");
		} catch (BadPaddingException e) {
			throw new Exception("瀵嗘枃鏁版嵁宸叉崯鍧�");
		}
	}

	/**
	 * 瀛楄妭鏁版嵁杞崄鍏繘鍒跺瓧绗︿覆
	 * 
	 * @param data
	 *            杈撳叆鏁版嵁
	 * @return 鍗佸叚杩涘埗鍐呭
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 鍙栧嚭瀛楄妭鐨勯珮鍥涗綅 浣滀负绱㈠紩寰楀埌鐩稿簲鐨勫崄鍏繘鍒舵爣璇嗙 娉ㄦ剰鏃犵鍙峰彸绉�
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 鍙栧嚭瀛楄妭鐨勪綆鍥涗綅 浣滀负绱㈠紩寰楀埌鐩稿簲鐨勫崄鍏繘鍒舵爣璇嗙
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * RSAEncrypt rsaEncrypt = new RSAEncrypt(); // rsaEncrypt.genKeyPair(); //
	 * 鍔犺浇鍏挜 try { rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
	 * System.out.println("鍔犺浇鍏挜鎴愬姛"); } catch (Exception e) {
	 * System.err.println(e.getMessage()); System.err.println("鍔犺浇鍏挜澶辫触"); }
	 * 
	 * // 鍔犺浇绉侀挜 try {
	 * rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);
	 * System.out.println("鍔犺浇绉侀挜鎴愬姛"); } catch (Exception e) {
	 * System.err.println(e.getMessage()); System.err.println("鍔犺浇绉侀挜澶辫触"); }
	 * 
	 * RSAPrivateKey privateKey = rsaEncrypt.privateKey;
	 * System.out.println("privateKey   :" + privateKey); // 娴嬭瘯瀛楃涓� String
	 * encryptStr = "123"; System.out.println("绉侀挜闀垮害锛�" +
	 * rsaEncrypt.getPrivateKey().toString().length());
	 * System.out.println("鍏挜闀垮害锛�" +
	 * rsaEncrypt.getPublicKey().toString().length()); try {
	 * 
	 * // 鍔犲瘑 BASE64Decoder base64Decoder = new BASE64Decoder(); BASE64Encoder
	 * base64Encoder = new BASE64Encoder();
	 * 
	 * byte[] cip_her = { 75, -60, 54, -7, -114, -123, -50, -117, 76, -39, -123,
	 * 56, 10, 45, -70, 27, 109, 98, -44, 58, -3, 115, 29, 87, 41, 95, -108, -8,
	 * -35, -25, -32, -74, 55, -62, -37, -56, -107, 92, -20, 35, 21, 23, -32,
	 * -115, 31, -79, -53, -74, -39, 2, -48, 69, -36, -40, 8, 2, -11, -26, -81,
	 * 124, -91, -99, 109, -111, -97, -61, -70, 108, 28, 1, -75, -90, 97, -27,
	 * -89, 59, 67, 66, 11, -61, -109, -54, 34, 108, 69, 118, -54, 35, -52, 85,
	 * 100, 98, 80, -51, -119, -41, -46, 48, -55, 21, 9, 124, 53, -23, -23, -18,
	 * 102, -12, 18, 117, -19, 110, 60, 91, 39, -117, -48, 70, 25, -87, 121,
	 * -87, -116, 89, 65, -16, 66, 12 };
	 * 
	 * @SuppressWarnings("static-access") byte[] plainText =
	 * rsaEncrypt.decrypt(privateKey, cip_her); System.out.println(new
	 * String(plainText));
	 * 
	 * } catch (Exception e) { System.err.println(e.getMessage()); } }
	 */
}
