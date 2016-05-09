package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.Item;
import com.bnade.wow.service.ItemService;

public class ItemServiceImpl implements ItemService {
	
	private ItemDao itemDao;
	
	public ItemServiceImpl() {
		itemDao = new ItemDaoImpl();
	}

	@Override
	public List<Item> getItemsByName(String name) throws SQLException {
		List<Item> items = itemDao.getItemsByName(name);
		for (Item item : items) {
			item.setBonusList(itemDao.getBonusList(item.getId()));
		}
		return items;
	}

	@Override
	public List<Item> getItemsByName(String name, boolean isFuzzy)
			throws SQLException {
		return itemDao.getItemsByName(name, isFuzzy);
	}
	
	@Override
	public Item getItemById(int id) throws SQLException {
		return itemDao.getItemById(id);
	}

}
