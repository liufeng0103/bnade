package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 把每次计算的最低一口价拍卖信息保存起来的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionHouseMinBuyoutDailyDataDao {

	void save(List<Auction> aucs, String date, int realmId) throws SQLException;
	
	List<Auction> get(int itemId, String bounsList, String date, int realmId) throws SQLException;
	
	List<Auction> get(String date, int realmId) throws SQLException;

	void drop(String date, int realmId) throws SQLException;
	
}
