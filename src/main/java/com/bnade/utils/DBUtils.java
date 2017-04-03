package com.bnade.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DBUtils {
	
	private static String configFile = "jdbc.properties";
	private static DataSource dataSource;

	public static DataSource getDataSource() {
		if (dataSource == null) {
			try {
				dataSource = DruidDataSourceFactory.createDataSource(loadPropertyFile(configFile));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataSource;
	}

	private static Properties loadPropertyFile(String fileName) {
		Properties p = null;
		InputStream is = DBUtils.class.getClassLoader().getResourceAsStream(fileName);
		try {
			if (is == null) {
				is = new FileInputStream(fileName);
			}
			if (is != null) {
				p = new Properties();
				p.load(is);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Properties file not found: " + fileName);
		} catch (IOException e) {
			throw new IllegalArgumentException("Properties file can not be loading: " + fileName);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return p;
	}
	
	public static boolean isTableExist(String tableName) throws SQLException {
		boolean isTableExist = false;
		Connection con = DBUtils.getDataSource().getConnection();
		try {
			DatabaseMetaData  dbMeta = con.getMetaData();
			ResultSet rs = dbMeta.getTables(null, null, tableName, new String[]{"TABLE"});
			if (rs.next()) {			
				isTableExist = true;
			} else {
				isTableExist = false;
			}
		} finally {
			DbUtils.closeQuietly(con);
		}		
		return isTableExist;
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println(DBUtils.getDataSource().getConnection() != null);
		System.out.println(DBUtils.isTableExist("t_realm"));
		System.out.println(DBUtils.isTableExist("xxxx"));
	}
}
