package com.bnade.wow.vo;

import com.google.gson.Gson;

public class Result {

	private static final int OK = 0;
	private static final int ERROR = -1;
	private static Gson gson = new Gson();

	public static String OK(String msg) {
		return build(OK, msg);
	}

	public static String OK(String msg, Object obj) {
		return build(OK, msg, obj);
	}

	public static String ERROR(String msg) {
		return build(ERROR, msg);
	}

	public static String ERROR(String msg, Object obj) {
		return build(ERROR, msg, obj);
	}

	public static String build(int code, String msg) {
		return build(code, msg, null);
	}

	public static String build(int code, String msg, Object obj) {
		if (obj != null) {
			return "{\"code\":" + code + ",\"message\":\"" + msg
					+ "\",\"result\":" + gson.toJson(obj) + "}";
		} else {
			return "{\"code\":" + code + ",\"message\":\"" + msg + "\"}";
		}
	}
}
