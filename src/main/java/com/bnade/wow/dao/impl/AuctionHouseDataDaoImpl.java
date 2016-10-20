package com.bnade.wow.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.AuctionHouseDataDao;
import com.bnade.wow.po.Auction;

public class AuctionHouseDataDaoImpl implements AuctionHouseDataDao {

//	private static Logger logger = LoggerFactory.getLogger(AuctionHouseDataDaoImpl.class);
	
	private static final String TABLE_NAME_PREFIX = "t_ah_data_";
	
	private QueryRunner run;
	
	public AuctionHouseDataDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public void save(List<Auction> aucs, int realmId) throws SQLException {
		Connection con = DBUtil.getDataSource().getConnection();
		try {
			boolean autoCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			String tableName = TABLE_NAME_PREFIX + realmId;		
			Object[][] params = new Object[aucs.size()][14];
			for (int i = 0; i < aucs.size(); i++) {
				Auction auc = aucs.get(i);
				params[i][0] = auc.getAuc();
				params[i][1] = auc.getItem();
				params[i][2] = auc.getOwner();
				params[i][3] = auc.getOwnerRealm();
				params[i][4] = auc.getBid();
				params[i][5] = auc.getBuyout();
				params[i][6] = auc.getQuantity();
				params[i][7] = auc.getTimeLeft();
				params[i][8] = auc.getPetSpeciesId();
				params[i][9] = auc.getPetLevel();
				params[i][10] = auc.getPetBreedId();
				params[i][11] = auc.getContext();
				params[i][12] = auc.getBonusLists();
				params[i][13] = auc.getLastModifed();			
			}
			run.batch(con, "insert into "
							+ tableName
							+ " (auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", params);
			con.commit();
			con.setAutoCommit(autoCommit);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	@Override
	public void deleteAll(int realmId) throws SQLException {	
		String tableName = TABLE_NAME_PREFIX + realmId;
		run.update("truncate " + tableName);
	}

	@Override
	public List<Auction> getByOwner(String owner, int realmId) throws SQLException {
		String tableName = TABLE_NAME_PREFIX + realmId;
		return run.query("select auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed from "
						+ tableName + " where owner=?", 
						new BeanListHandler<Auction>(Auction.class), owner);
	}

	@Override
	public List<Auction> getByItemId(int itemId, String bounsList, int realmId)
			throws SQLException {
		String tableName = TABLE_NAME_PREFIX + realmId;
		String url = "select auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed from "
				+ tableName + " where item=?";
		if (bounsList != null && !"all".equals(bounsList)) {
			url += " and bonusLists=?";
			return run.query(url, new BeanListHandler<Auction>(Auction.class), itemId, bounsList);
		} else {
			return run.query(url, new BeanListHandler<Auction>(Auction.class), itemId);
		}
	}

}
