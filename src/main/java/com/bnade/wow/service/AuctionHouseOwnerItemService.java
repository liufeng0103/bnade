package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.OwnerItem;
import com.bnade.wow.po.OwnerItemStatistics;

public interface AuctionHouseOwnerItemService {

	void save(List<OwnerItem> items, int realmId) throws SQLException;
	
	void deleteAll(int realmId) throws SQLException;
	
	List<OwnerItemStatistics> getOwnerItemStatisticsByRealmId(int realmId, String orderBy, int limit) throws SQLException;
}
