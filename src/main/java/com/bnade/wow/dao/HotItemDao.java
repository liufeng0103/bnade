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
	
	void saveHotItem(HotItem item) throws SQLException;
	
	void updateHotItem(HotItem item) throws SQLException;
	
	HotItem getByDatetimeAndItemId(long datetime, int itemId) throws SQLException;
	
	List<HotItem> getGroupItemIdAfterDatetime(long datetime, int offset, int limit) throws SQLException;
	
	List<HotItem> getHotItems(long datetime, int offset, int limit) throws SQLException;
}
