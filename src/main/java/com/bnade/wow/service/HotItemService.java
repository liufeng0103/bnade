package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.QueryItem;

public interface HotItemService {

	void add(QueryItem item) throws SQLException;
	
	List<HotItem> getItemQuiredCountAfterLastUpdated(long lastUpdated) throws SQLException;
	
	public List<HotItem> getGroupItemIdAfterDatetime(long datetime, int limit) throws SQLException;
	
}
