package com.bnade.wow.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.utils.DBUtils;
import com.bnade.wow.dao.AuctionHouseOwnerItemDao;
import com.bnade.wow.po.OwnerItem;
import com.bnade.wow.po.OwnerItemStatistics;

public class AuctionHouseOwnerItemDaoImpl implements AuctionHouseOwnerItemDao {
	
	private static Logger logger = LoggerFactory.getLogger(AuctionHouseOwnerItemDaoImpl.class);
	
	private static final String TABLE_NAME_PREFIX = "t_ah_owner_item_";
	
	private QueryRunner run;
	
	public AuctionHouseOwnerItemDaoImpl() {
		run = new QueryRunner(DBUtils.getDataSource());
	}
	
	@Override
	public void save(List<OwnerItem> items, int realmId) throws SQLException {	
		String tableName = TABLE_NAME_PREFIX + realmId;
		checkAndCreateTable(tableName);
		Connection con = DBUtils.getDataSource().getConnection();
		try {
			boolean autoCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			Object[][] params = new Object[items.size()][3];
			for (int i = 0; i < items.size(); i++) {
				OwnerItem item = items.get(i);				
				params[i][0] = item.getItem();
				params[i][1] = item.getOwner();				
				params[i][2] = item.getQuantity();			
			}
			run.batch(con, "insert into "
							+ tableName
							+ " (item,owner,quantity) values(?,?,?)", params);
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
			sb.append("owner VARCHAR(12) NOT NULL,");
			sb.append("quantity INT UNSIGNED NOT NULL,");
			sb.append("PRIMARY KEY(id)");
			sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8");
			run.update(sb.toString());			
			logger.info("表{}未创建， 创建表和索引", tableName);
		}
	}

	@Override
	public void deleteAll(int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + realmId;
		if (DBUtils.isTableExist(tableName)) {
			run.update("truncate " + tableName);	
		}				
	}

	@Override
	public List<OwnerItemStatistics> getOwnerItemStatisticsByRealmId(int realmId, String orderBy, int limit) throws SQLException {
		String tableName = "t_ah_owner_item_" + realmId;
		String url = "select owner,count(owner) as itemCategeryCount,sum(quantity) as quantity,sum(quantity*buy) as worth from " 
		+ tableName 
		+ " i join t_item_market m on i.item=m.itemId group by owner order by " 
		+ orderBy 
		+ " desc limit ?";
//		System.out.println(url);
		return run.query(url, new BeanListHandler<OwnerItemStatistics>(OwnerItemStatistics.class), limit);
	}

}
