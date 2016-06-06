package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.QueryItem;
import com.bnade.wow.vo.HotSearchItem;

public interface HotItemService {

	void add(QueryItem item) throws SQLException;
	
	List<HotItem> getItemQuiredCountAfterLastUpdated(long lastUpdated) throws SQLException;
	
	public List<HotItem> getGroupItemIdAfterDatetime(long datetime, int limit) throws SQLException;
	
	/**
	 * 获取每天搜索的物品
	 * @param offset
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public List<HotSearchItem> getHotSearchItems(int offset, int limit) throws SQLException;
	
}
