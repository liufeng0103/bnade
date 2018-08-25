package com.bnade.wow.task;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.RealmDao;
import com.bnade.wow.dao.impl.RealmDaoImpl;
import com.bnade.wow.po.Realm;

public class PrintRealmList {

	public static void main(String[] args) throws SQLException {
		RealmDao dao = new RealmDaoImpl();
		String text = "local realmMap= {";
		StringBuffer sb = new StringBuffer();
		for (Realm r : dao.getAll()) {
			String name = r.getName();
			int id = r.getId();
			for (String s : name.split("-")) {
				
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append("[\"" + s + "\"]=\"" + id + "\"");
			}
			
		}
		text += sb.toString() + "}";
		System.out.println(text);
	}

}
