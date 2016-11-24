package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.AuctionDao;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Auction2;

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

	@Override
	public List<Auction2> getAuctionsByRealmOwner(int realmId, String owner)
			throws SQLException {
		String sql = "select a.item,i.name,i.icon,a.owner,a.bid,a.buyout,a.quantity,a.timeLeft,a.petSpeciesId,a.petBreedId,a.bonusLists,m.owner as owner2,m.bid as bid2,m.buyout as buyout2,m.quantity as quantity2 from t_ah_data_"
				+ realmId
				+ " a join mt_item i on a.item=i.id join t_ah_min_buyout_data m on a.item=m.item and m.realmId="
				+ realmId
				+ " and a.bonusLists=m.bonusLists where a.owner=? and a.item <> 82800 union all select a.item,p.name,p.icon,a.owner,a.bid,a.buyout,a.quantity,a.timeLeft,a.petSpeciesId,a.petBreedId,a.bonusLists,m.owner as owner2,m.bid as bid2,m.buyout as buyout2,m.quantity as quantity2 from t_ah_data_"
				+ realmId
				+ " a join t_pet p on a.item=82800 and a.petSpeciesId=p.id join t_ah_min_buyout_data m on a.item=m.item and m.realmId="
				+ realmId
				+ " and a.petSpeciesId=m.petSpeciesId and a.petBreedId=m.petBreedId where a.owner=?";
		return run.query(sql, new BeanListHandler<Auction2>(Auction2.class),
				owner, owner);
	}

}
