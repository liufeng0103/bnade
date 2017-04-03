package com.bnade.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOUtils {
	
	/**
	 * InputStream转String
	 * 
	 * 参考http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
	 * 这种方式转化inputStream到String效率最高
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String toString(InputStream is, String encoding) throws IOException {
		try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString(encoding);
		}
	}
	
	private static Scanner scan;

	/**
	 * 读取文件内容，把每行内容保存到list中返回
	 * @return
	 */
	public static List<String> fileLineToList(String filePath) {
		List<String> list = new ArrayList<String>();		
		try {
			scan = new Scanner(new FileInputStream(filePath));
			while (scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				if (line != null && !"".equals(line)) {
					list.add(line);
				}				
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}		
		return list;
	}
	
	public static void stringToFile(String text, String filePath) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileOutputStream(filePath, false));
			out.println(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();	
			}			
		}		
	}
}
