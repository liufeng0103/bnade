package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.AuctionDao;
import com.bnade.wow.po.Auction;

public class AuctionDaoImpl implements AuctionDao {
	
	private QueryRunner run;
	
	public AuctionDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	/**
	 * select auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed,i.name as itemName from t_ah_data_80 a left join mt_item i on a.item=i.id where owner='野兽女王' order by buyout/quantity
	 */
	@Override
	public List<Auction> getAuctionsByRealmOwner(int realmId, String owner,
			String order) throws SQLException {
		String sql = "select auc,item,owner,ownerRealm,bid,buyout,quantity,timeLeft,petSpeciesId,petLevel,petBreedId,context,bonusLists,lastModifed,i.name as itemName from t_ah_data_"
				+ realmId + " a left join mt_item i on a.item=i.id where owner=? ";
		if (order != null) {
			sql += " order by " + order;
		}
		return run.query(sql, new BeanListHandler<Auction>(Auction.class), owner);
	}

}
