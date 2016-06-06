package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.HotItemDao;
import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.HotItemDaoImpl;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.QueryItem;
import com.bnade.wow.service.HotItemService;
import com.bnade.wow.vo.HotSearchItem;

public class HotItemServiceImpl implements HotItemService {
	
	private HotItemDao hotItemDao;
	private ItemDao itemDao;
	
	public HotItemServiceImpl() {
		hotItemDao = new HotItemDaoImpl();
		itemDao = new ItemDaoImpl();
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
	public List<HotItem> getGroupItemIdAfterDatetime(long datetime, int limit) throws SQLException {
		return hotItemDao.getGroupItemIdAfterDatetime(datetime, 0, limit);
	}

	@Override
	public List<HotSearchItem> getHotSearchItems(int offset, int limit)
			throws SQLException {
		List<HotItem> items = null;
		try {
			items = hotItemDao.getGroupItemIdAfterDatetime(TimeUtil.parse(TimeUtil.getDate(0)).getTime(), offset, limit);
		} catch (ParseException e) {
			e.printStackTrace();
		};
		List<HotSearchItem> hotItems = new ArrayList<>();
		for (HotItem item : items) {
			int id = item.getItemId();
			Item itemObj = itemDao.getItemById(id);
			hotItems.add(new HotSearchItem(id, itemObj.getName(), itemObj.getIcon(), item.getQueried()));
		}
		return hotItems;
	}

}
