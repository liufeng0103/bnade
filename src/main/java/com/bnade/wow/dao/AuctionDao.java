package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Auction2;

/**
 * 拍卖行数据操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionDao {

	List<Auction> getAuctionsByRealmOwner(int realmId, String owner, String order) throws SQLException;
	
	List<Auction2> getAuctionsByRealmOwner(int realmId, String owner) throws SQLException;
}
