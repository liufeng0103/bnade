package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.HotItemDao;
import com.bnade.wow.dao.impl.HotItemDaoImpl;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.QueryItem;
import com.bnade.wow.service.HotItemService;

public class HotItemServiceImpl implements HotItemService {
	
	private HotItemDao hotItemDao;
	
	public HotItemServiceImpl() {
		hotItemDao = new HotItemDaoImpl();	
	}	

	@Override
	public void add(QueryItem item) throws SQLException {
		hotItemDao.add(item);		
	}

	@Override
	public List<HotItem> getItemQuiredCountAfterLastUpdated(long lastUpdated) throws SQLException {
		return hotItemDao.getItemQuiredCountAfterCreatedAt(lastUpdated);
	}

	@Override
	public List<HotItem> getHotItemsByPeriodSortByQueried(int period, int limit) throws SQLException {		
		return hotItemDao.getHotItemsByPeriodSortByQueried(period, limit);
	}

}
