package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.QueryItem;

/**
 * 热门物品统计
 * 
 * @author liufeng0103
 *
 */
public interface HotItemDao {

	void add(QueryItem item) throws SQLException;
	
	void deleteAllQuiredItem() throws SQLException;
	
	List<HotItem> getItemQuiredCountAfterCreatedAt(long createdAt) throws SQLException;
	
	void add(List<HotItem> items) throws SQLException;
	
	void saveHotItem(HotItem item) throws SQLException;
	
	void updateHotItem(HotItem item) throws SQLException;
	
	void deleteAllHotItemByPeriod(int period) throws SQLException;
	
	List<HotItem> getHotItemsByPeriod(int period) throws SQLException;
	
	HotItem getHotByItemIdAndPeriod(int itemId, int period) throws SQLException;
	
	List<HotItem> getHotItemsByPeriodSortByQueried(int period, int limit) throws SQLException;
	
}
