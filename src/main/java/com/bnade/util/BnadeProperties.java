package com.bnade.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BnadeProperties {

	private static final String BNADE_FILE_PATH = "bnade.properties";
	
	private static Properties bnadeProperties;
	
	private static void load() {
		if (bnadeProperties == null) {
			bnadeProperties = new Properties();
			try {
				InputStream is = BnadeProperties.class.getClassLoader().getResourceAsStream(BNADE_FILE_PATH);
				if (is == null) {
					is = new FileInputStream(BNADE_FILE_PATH);
				}
				bnadeProperties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static long getTask1Interval() {
		load();
		return Long.valueOf(bnadeProperties.getProperty("interval", "2100000"));
	}
	
	public static int getTask1ThreadCount() {
		load();
		return Integer.valueOf(bnadeProperties.getProperty("task1_thread_count", "8"));
	}
	
	public static String getApiKey() {
		load();
		return bnadeProperties.getProperty("api_key");
	}

	public static String getValue(String key) {
		load();
		return bnadeProperties.getProperty(key).trim();
	}
}
