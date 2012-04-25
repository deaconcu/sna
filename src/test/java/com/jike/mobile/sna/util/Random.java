package com.jike.mobile.sna.util;

public class Random {
	public static String getChars(int length, int start, int range) {
		java.util.Random r = new java.util.Random();
		char[] chars = new char[length];
		for(int i = 0; i < chars.length; i++) {
			int temp = r.nextInt(range) + start;
			chars[i] = (char)temp;
		}
		return String.valueOf(chars);
	}
}
