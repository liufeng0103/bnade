package com.bnade.wow.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.utils.DBUtils;
import com.bnade.wow.dao.AuctionHouseMinBuyoutMonthlyDataDao;
import com.bnade.wow.po.HistoryAuction;

public class AuctionHouseMinBuyoutMonthlyDataDaoImpl implements AuctionHouseMinBuyoutMonthlyDataDao {

	private static Logger logger = LoggerFactory.getLogger(AuctionHouseMinBuyoutMonthlyDataDaoImpl.class);
	
	private static final String TABLE_NAME_PREFIX = "t_ah_min_buyout_data_";
	
	private QueryRunner run;
	
	public AuctionHouseMinBuyoutMonthlyDataDaoImpl() {
		run = new QueryRunner(DBUtils.getDataSource());
	}

	@Override
	public void save(List<HistoryAuction> aucs, String month, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + month + "_" + realmId;
		checkAndCreateTable(tableName);
		Connection con = DBUtils.getDataSource().getConnection();
		try {
			boolean autoCommit = con.getAutoCommit();
			con.setAutoCommit(false);			
			Object[][] params = new Object[aucs.size()][7];
			for (int i = 0; i < aucs.size(); i++) {
				HistoryAuction auc = aucs.get(i);
				params[i][0] = auc.getItem();
				params[i][1] = auc.getBuyout();
				params[i][2] = auc.getQuantity();				
				params[i][3] = auc.getPetSpeciesId();				
				params[i][4] = auc.getPetBreedId();				
				params[i][5] = auc.getBonusLists();
				params[i][6] = auc.getLastModifed();				
			}
			run.batch(con, "insert into "
							+ tableName
							+ " (item,buyout,quantity,petSpeciesId,petBreedId,bonusLists,lastModifed) values(?,?,?,?,?,?,?)",
					params);
			con.commit();
			con.setAutoCommit(autoCommit);
		} finally {
			DbUtils.closeQuietly(con);
		}	
	}
	
	private void checkAndCreateTable(String tableName) throws SQLException {
		logger.debug("检查表{}是否存在", tableName);
		if (!DBUtils.isTableExist(tableName)) {
			StringBuffer sb = new StringBuffer();
			sb.append("CREATE TABLE IF NOT EXISTS " + tableName + " (");
			sb.append("id INT UNSIGNED NOT NULL AUTO_INCREMENT,");
			sb.append("item INT UNSIGNED NOT NULL,");			
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
	public List<HistoryAuction> get(int itemId, String bounsList, String month, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + month + "_" + realmId;
		if (DBUtils.isTableExist(tableName)) {
			String url = "select item,buyout,quantity,petSpeciesId,petBreedId,bonusLists,lastModifed from "
					+ tableName 
					+ " where item=?";
			if (bounsList != null) {
				url += " and bonusLists=?";
				logger.debug(url);
				return run.query(url, new BeanListHandler<HistoryAuction>(HistoryAuction.class), itemId, bounsList);
			} else {
				logger.debug(url);
				return run.query(url, new BeanListHandler<HistoryAuction>(HistoryAuction.class), itemId);
			}			
		} else {
			logger.debug("表{}不存在,返回空数组", tableName);
			return new ArrayList<>();
		}
	}	
	
}
