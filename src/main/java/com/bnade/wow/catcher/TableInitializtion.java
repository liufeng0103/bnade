package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.bnade.utils.DBUtils;
import com.bnade.utils.IOUtils;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.RealmServiceImpl;

/**
 * 使用realmlist.txt中的服务器名来初始化t_item表
 * realmlist.txt包含170个服务器名，保存到数据库中有顺序要求，老的程序
 * 按照这个顺序保存的数据
 * 
 * @author liufeng0103
 *
 */
public class TableInitializtion {

	public void initRealmTable() throws SQLException {
		RealmService realmService = new RealmServiceImpl();
		List<String> realmNames = IOUtils.fileLineToList("realmlist.txt");
		if (realmNames.size() != 170) {
			System.err.println("国服一共170个服务器， 请确认使用了正确的文件");
			return;
		}
		for (String realmName : realmNames) {
			Realm realm = new Realm();
			realm.setName(realmName);
			realm.setUrl("");
			realmService.save(realm);
			System.out.println("添加成功：" + realmName);
		}
		System.out.println("t_realm初始化完成");
	}
	
	public void initAuctionHouseDataTable() throws SQLException {
		RealmService realmService = new RealmServiceImpl();
		QueryRunner run = new QueryRunner(DBUtils.getDataSource());
		List<String> realmNames = IOUtils.fileLineToList("realmlist.txt");
		if (realmNames.size() != 170) {
			System.err.println("国服一共170个服务器， 请确认使用了正确的文件");
			return;
		}
		for (String realmName : realmNames) {
			Realm realm = realmService.getByName(realmName);			
			StringBuffer sb = new StringBuffer();
			sb.append("CREATE TABLE IF NOT EXISTS t_ah_data_" + realm.getId() + " (");
			sb.append("id INT UNSIGNED NOT NULL AUTO_INCREMENT,");
			sb.append("auc INT UNSIGNED NOT NULL,");
			sb.append("item INT UNSIGNED NOT NULL,");
			sb.append("owner VARCHAR(12) NOT NULL,");
			sb.append("ownerRealm VARCHAR(8) NOT NULL,");
			sb.append("bid BIGINT UNSIGNED NOT NULL,");
			sb.append("buyout BIGINT UNSIGNED NOT NULL,");
			sb.append("quantity INT UNSIGNED NOT NULL,");
			sb.append("timeLeft VARCHAR(12) NOT NULL,");
			sb.append("petSpeciesId INT UNSIGNED NOT NULL,");
			sb.append("petLevel INT UNSIGNED NOT NULL,");
			sb.append("petBreedId INT UNSIGNED NOT NULL,");
			sb.append("context INT UNSIGNED NOT NULL,");
			sb.append("bonusLists VARCHAR(20) NOT NULL,");
			sb.append("lastModifed BIGINT UNSIGNED NOT NULL,");
			sb.append("PRIMARY KEY(id)");
			sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
			String url = sb.toString();			
			run.update(url);
			run.update("ALTER TABLE t_ah_data_" + realm.getId() + " ADD INDEX(item)");
			run.update("ALTER TABLE t_ah_data_" + realm.getId() + " ADD INDEX(owner)");
			System.out.println("添加表成功：" + realmName);
		}
		System.out.println("完毕");
	}
	
	public static void main(String[] args) throws SQLException {		
		new TableInitializtion().initAuctionHouseDataTable();
	}
}
