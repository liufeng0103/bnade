package com.bnade.util;

public class StringUtils {

	public static boolean isEmpty(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}
		return false;
	}
	
}
