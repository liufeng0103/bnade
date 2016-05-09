package com.bnade.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
	
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
}
