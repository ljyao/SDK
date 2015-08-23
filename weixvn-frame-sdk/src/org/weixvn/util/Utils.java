package org.weixvn.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Toast;

/**
 * 工具类，其中放有大量实用工具
 * 
 * @author weixvn
 */
@SuppressLint("DefaultLocale")
public class Utils {
	public static final String CONST_HMAC_SHA1 = "HmacSHA1";
	public static final String CONST_SIGNATURE_METHOD = "HMAC-SHA1";
	private static final String EMAIL_REG_EX = "^[_\\.0-9a-zA-Z+-]+@([0-9a-zA-Z]+[0-9a-zA-Z-]*\\.)+[a-zA-Z]{2,4}$";

	public Utils() {
	}

	public static char[] base64Encode(byte abyte0[]) {
		char ac[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
				.toCharArray();
		char ac1[] = new char[4 * ((2 + abyte0.length) / 3)];
		int i = 0;
		int j = 0;
		while (i < abyte0.length) {
			boolean flag = false;
			boolean flag1 = false;
			int k = (0xff & abyte0[i]) << 8;
			if (i + 1 < abyte0.length) {
				k |= 0xff & abyte0[i + 1];
				flag1 = true;
			}
			int l = k << 8;
			if (i + 2 < abyte0.length) {
				l |= 0xff & abyte0[i + 2];
				flag = true;
			}
			int i1 = j + 3;
			int j1;
			int k1;
			int l1;
			int i2;
			int j2;
			int k2;
			if (flag)
				j1 = l & 0x3f;
			else
				j1 = 64;
			ac1[i1] = ac[j1];
			k1 = l >> 6;
			l1 = j + 2;
			if (flag1)
				i2 = k1 & 0x3f;
			else
				i2 = 64;
			ac1[l1] = ac[i2];
			j2 = k1 >> 6;
			ac1[j + 1] = ac[j2 & 0x3f];
			k2 = j2 >> 6;
			ac1[j + 0] = ac[k2 & 0x3f];
			i += 3;
			j += 4;
		}
		return ac1;
	}

	public static String encode(String s) throws UnsupportedEncodingException {
		String s1 = null;
		String s2 = URLEncoder.encode(s, "UTF-8");
		s1 = s2;
		StringBuffer stringbuffer = new StringBuffer(s1.length());
		int i = 0;
		while (i < s1.length()) {
			char c = s1.charAt(i);
			if (c == '*')
				stringbuffer.append("%2A");
			else if (c == '+')
				stringbuffer.append("%20");
			else if (c == '%' && i + 1 < s1.length() && s1.charAt(i + 1) == '7'
					&& s1.charAt(i + 2) == 'E') {
				stringbuffer.append('~');
				i += 2;
			} else {
				stringbuffer.append(c);
			}
			i++;
		}
		return stringbuffer.toString();
	}

	@SuppressWarnings("deprecation")
	public static String encodeUrl(Bundle bundle) {
		String s;
		if (bundle == null) {
			s = "";
		} else {
			StringBuilder stringbuilder = new StringBuilder();
			boolean flag = true;
			Iterator<String> iterator = bundle.keySet().iterator();
			while (iterator.hasNext()) {
				String s1 = (String) iterator.next();
				if (flag)
					flag = false;
				else
					stringbuilder.append("&");
				stringbuilder.append((new StringBuilder()).append(s1)
						.append("=")
						.append(URLEncoder.encode(bundle.getString(s1)))
						.toString());
			}
			s = stringbuilder.toString();
		}
		return s;
	}

	private static String getMD5(byte abyte0[]) throws Exception {
		MessageDigest messagedigest = MessageDigest.getInstance("MD5");
		StringBuffer stringbuffer = new StringBuffer();
		byte abyte1[] = messagedigest.digest(abyte0);
		int i = abyte1.length;
		for (int j = 0; j < i; j++) {
			byte byte0 = abyte1[j];
			stringbuffer.append(Integer.toHexString((byte0 & 0xf0) >>> 4));
			stringbuffer.append(Integer.toHexString(byte0 & 0xf));
		}

		return stringbuffer.toString();
	}

	public static ProgressDialog makeGoingDialog(Context context, int i) {
		ProgressDialog progressdialog = new ProgressDialog(context);
		progressdialog.setMessage(context.getString(i));
		progressdialog.setIndeterminate(true);
		progressdialog.setCancelable(true);
		return progressdialog;
	}

	@SuppressLint("NewApi")
	public static CharSequence makeTimeString(int i) {
		return DateFormat.format("mm:ss", i);
	}

	public static String md5(String s) throws Exception {
		String s1;
		if (s == null || s.trim().length() < 1)
			s1 = null;
		else
			s1 = getMD5(s.getBytes("UTF-8"));
		return s1.toUpperCase();
	}

	public static void showToast(Context context, int i) {
		Toast.makeText(context, i, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, String s) {
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}

	public static void showToastLonger(Context context, int i) {
		Toast.makeText(context, i, Toast.LENGTH_LONG).show();
	}

	public static void showToastLonger(Context context, String s) {
		Toast.makeText(context, s, Toast.LENGTH_LONG).show();
	}

	public static boolean validateEmail(String s) {
		return Pattern.compile(EMAIL_REG_EX).matcher(s).find();
	}
}
