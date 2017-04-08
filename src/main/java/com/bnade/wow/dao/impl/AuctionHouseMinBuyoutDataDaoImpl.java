package com.bnade.wow.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.AuctionHouseMinBuyoutDataDao;
import com.bnade.wow.po.Auction;

public class AuctionHouseMinBuyoutDataDaoImpl implements AuctionHouseMinBuyoutDataDao {
	
	private QueryRunner run;
	
	public AuctionHouseMinBuyoutDataDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public void save(List<Auction> aucs) throws SQLException {
		Connection con = DBUtil.getDataSource().getConnection();
		try {
			boolean autoCommit = con.getAutoCommit();
			con.setAutoCommit(false);			
			Object[][] params = new Object[aucs.size()][15];
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
				params[i][14] = auc.getRealmId();
			}
			run.batch(con,
					"insert into t_ah_min_buyout_data (auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed,realmId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					params);
			con.commit();
			con.setAutoCommit(autoCommit);
		} finally {
			DbUtils.closeQuietly(con);
		}		
	}

	@Override
	public void deleteAll(int realmId) throws SQLException {
		run.update("delete from t_ah_min_buyout_data where realmId=?", realmId);		
	}

	@Override
	public List<Auction> getByItemIdAndBounsList(int itemId, String bounsList) throws SQLException {
		if ("all".equals(bounsList)) {
			return run.query("select realmId,min(buyout) as buyout,owner,sum(quantity) as quantity,lastModifed,timeLeft from t_ah_min_buyout_data where item = ? group by realmId", 
					new BeanListHandler<Auction>(Auction.class), itemId);			
		} else {
			String url = "select auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed,realmId from t_ah_min_buyout_data where item=?";
			if (bounsList != null) {
				url += " and bonusLists=?";
				return run.query(url, new BeanListHandler<Auction>(Auction.class), itemId, bounsList);
			} else {
				return run.query(url + " order by buyout", new BeanListHandler<Auction>(Auction.class), itemId);
			}
		}		
	}

	@Override
	public List<Auction> getPetsByIdAndBreed(int petId, int breedId) throws SQLException {
		return run.query("select auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed,realmId from t_ah_min_buyout_data where petSpeciesId=? and petBreedId=?", 
				new BeanListHandler<Auction>(Auction.class), petId, breedId);
	}	

}
