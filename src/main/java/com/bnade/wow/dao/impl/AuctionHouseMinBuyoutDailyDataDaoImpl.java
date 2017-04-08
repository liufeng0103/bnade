package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.AuctionHouseMinBuyoutDailyDataDao;
import com.bnade.wow.po.Auction;

public class AuctionHouseMinBuyoutDailyDataDaoImpl implements AuctionHouseMinBuyoutDailyDataDao {

	private static Logger logger = LoggerFactory.getLogger(AuctionHouseMinBuyoutDailyDataDaoImpl.class);
	
	private static final String TABLE_NAME_PREFIX = "t_ah_min_buyout_data_";
	
	private QueryRunner run;
	
	public AuctionHouseMinBuyoutDailyDataDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	@Override
	public void save(List<Auction> aucs, String date, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + date + "_" + realmId;
		// 1. 检查并创建表和索引
		checkAndCreateTable(tableName);
		// 2. 保存数据
		run.update("insert into " + tableName + " (item,owner,ownerRealm,bid,buyout,quantity,petSpeciesId,petBreedId,bonusLists,lastModifed) select item,owner,ownerRealm,bid,buyout,quantity,petSpeciesId,petBreedId,bonusLists,lastModifed from t_ah_min_buyout_data where realmId=? and item != 82800", realmId);
	}
	
	private void checkAndCreateTable(String tableName) throws SQLException {
		logger.debug("检查表{}是否存在", tableName);
		if (!DBUtil.isTableExist(tableName)) {
			StringBuffer sb = new StringBuffer();
			sb.append("CREATE TABLE IF NOT EXISTS " + tableName + " (");
			sb.append("id INT UNSIGNED NOT NULL AUTO_INCREMENT,");
			sb.append("item INT UNSIGNED NOT NULL,");
			sb.append("owner VARCHAR(12) NOT NULL,");
			sb.append("ownerRealm VARCHAR(8) NOT NULL,");
			sb.append("bid BIGINT UNSIGNED NOT NULL,");
			sb.append("buyout BIGINT UNSIGNED NOT NULL,");
			sb.append("quantity INT UNSIGNED NOT NULL,");
			sb.append("petSpeciesId INT UNSIGNED NOT NULL,");
			sb.append("petBreedId INT UNSIGNED NOT NULL,");
			sb.append("bonusLists VARCHAR(20) NOT NULL,");
			sb.append("lastModifed BIGINT UNSIGNED NOT NULL,");
			sb.append("PRIMARY KEY(id)");
			sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
			run.update(sb.toString());
			run.update("ALTER TABLE " + tableName + " ADD INDEX(item)");
			logger.info("表{}未创建， 创建表和索引", tableName);
		}
	}

	@Override
	public List<Auction> get(int itemId, String bounsList, String date, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + date + "_" + realmId;
		String url = "select item,owner,ownerRealm,bid,buyout,quantity,petSpeciesId,petBreedId,bonusLists,lastModifed from " + tableName + " where item=?";
		if (DBUtil.isTableExist(tableName)) {
			if (bounsList != null) {
				url += " and bonusLists=?";
				return run.query(url, new BeanListHandler<Auction>(Auction.class), itemId, bounsList);
			} else {
				return run.query(url, new BeanListHandler<Auction>(Auction.class), itemId);
			}			
		} else {
			logger.debug("表{}不存在,返回空数组", tableName);
			return new ArrayList<>();
		}		
	}

	@Override
	public List<Auction> get(String date, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + date + "_" + realmId;
		if (DBUtil.isTableExist(tableName)) {
			return run.query("select item,owner,ownerRealm,bid,buyout,quantity,petSpeciesId,petBreedId,bonusLists,lastModifed from " + tableName, new BeanListHandler<Auction>(Auction.class));
		} else {
			logger.debug("表{}不存在,返回空数组", tableName);
			return new ArrayList<>();
		}	
	}

	@Override
	public void drop(String date, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + date + "_" + realmId;
		if (DBUtil.isTableExist(tableName)) {
			run.update("DROP TABLE " + tableName);
		} else {
			logger.debug("drop表{}不存在", tableName);
		}		
	}	
	
}
