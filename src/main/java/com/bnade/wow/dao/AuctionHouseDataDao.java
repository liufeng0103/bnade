package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 服务器所有拍卖数据操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionHouseDataDao {
	
	void save(List<Auction> aucs, int realmId) throws SQLException;

	void deleteAll(int realmId) throws SQLException;
	
	List<Auction> getByOwner(String owner, int realmId) throws SQLException;
	
	List<Auction> getByItemId(int itemId, String bounsList, int realmId) throws SQLException;
	
}