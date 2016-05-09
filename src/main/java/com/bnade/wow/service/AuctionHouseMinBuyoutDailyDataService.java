package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

public interface AuctionHouseMinBuyoutDailyDataService {

	void save(List<Auction> aucs, String date, int realmId) throws SQLException;
	
	List<Auction> get(int itemId, String bounsList, String date, int realmId) throws SQLException;
	
	List<Auction> get(String date, int realmId) throws SQLException;

	void drop(String date, int realmId) throws SQLException;
	
}
