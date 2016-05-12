package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.OwnerItem;
import com.bnade.wow.po.OwnerItemStatistics;

public interface AuctionHouseOwnerItemDao {

	void save(List<OwnerItem> items, int realmId) throws SQLException;
	
	void deleteAll(int realmId) throws SQLException;
	
	List<OwnerItemStatistics> getOwnerItemStatisticsByRealmId(int realmId, String orderBy, int limit) throws SQLException;
	
}
