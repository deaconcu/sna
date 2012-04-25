package com.jike.mobile.sna.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Transfer {
	public static String getMD5(byte[] input) {
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input);
			byte b[] = md.digest();			
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) i += 256;
				if (i < 16) buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		}
		catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
