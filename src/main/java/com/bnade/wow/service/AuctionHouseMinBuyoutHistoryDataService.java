package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.HistoryAuction;

public interface AuctionHouseMinBuyoutHistoryDataService {

	void save(List<HistoryAuction> aucs, String month, int realmId) throws SQLException;
	
	List<HistoryAuction> get(int itemId, String bounsList, String month, int realmId) throws SQLException;
	
}
