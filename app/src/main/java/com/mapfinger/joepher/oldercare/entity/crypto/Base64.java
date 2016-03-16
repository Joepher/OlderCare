package com.mapfinger.joepher.oldercare.entity.crypto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/1/10.
 */
public class Base64 {
	private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	public static String encode(byte[] data) {
		int len = data.length, start = 0, end = len - 3, i = start, n = 0;
		StringBuffer buf = new StringBuffer(data.length * 3 / 2);

		while (i <= end) {
			int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8) | (((int) data[i + 2]) & 0x0ff);
			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append(legalChars[d & 63]);
			i += 3;
			if (n++ >= 14) {
				n = 0;
				buf.append(" ");
			}
		}

		if (i == start + len - 2) {
			int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);
			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append(legalChars[(d >> 6) & 63]);
			buf.append("=");
		} else if (i == start + len - 1) {
			int d = (((int) data[i]) & 0x0ff) << 16;
			buf.append(legalChars[(d >> 18) & 63]);
			buf.append(legalChars[(d >> 12) & 63]);
			buf.append("==");
		}

		return buf.toString();
	}

	public static byte[] decode(String data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			decode(data, out);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		byte[] decodedBytes = out.toByteArray();
		try {
			out.close();
			out = null;
		} catch (Exception e) {
			System.err.println("Error while decoding BASE64: " + e.toString());
		}
		
		return decodedBytes;
	}

	private static int decode(char c) {
		if (c >= 'A' && c <= 'Z')
			return ((int) c) - 65;
		else if (c >= 'a' && c <= 'z')
			return ((int) c) - 97 + 26;
		else if (c >= '0' && c <= '9')
			return ((int) c) - 48 + 26 + 26;
		else {
			switch (c) {
				case '+':
					return 62;
				case '/':
					return 63;
				case '=':
					return 0;
				default:
					throw new RuntimeException("unexpected code: " + c);
			}
		}
	}

	private static void decode(String data, OutputStream out) throws Exception {
		int i = 0, len = data.length();

		while (true) {
			while (i < len && data.charAt(i) <= ' ') {
				i++;
			}
			if (i == len) {
				break;
			}

			int tri = (decode(data.charAt(i)) << 18) + (decode(data.charAt(i + 1)) << 12)
					+ (decode(data.charAt(i + 2)) << 6) + (decode(data.charAt(i + 3)));
			out.write((tri >> 16) & 255);
			if (data.charAt(i + 2) == '=') {
				break;
			}
			out.write((tri >> 8) & 255);
			if (data.charAt(i + 3) == '=') {
				break;
			}
			out.write(tri & 255);
			i += 4;
		}
	}
}
