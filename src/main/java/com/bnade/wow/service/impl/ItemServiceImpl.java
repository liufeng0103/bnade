package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.ItemCreatedBy;
import com.bnade.wow.po.ItemReagent;
import com.bnade.wow.po.ItemV;
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
			if (item.getItemClass() == 2 || item.getItemClass() == 4 || item.getItemClass() == 3) {
				item.setBonusList(itemDao.getBonusList(item.getId()));
			} else {
				item.setBonusList(new ArrayList<String>());
			}
			item.setCreatedBy(getItemCreatedBy(item.getId()));
		}
		return items;
	}

	@Override
	public List<Item> getItemsByName(String name, boolean isFuzzy, int offset, int limit)
			throws SQLException {
		return itemDao.getItemsByName(name, isFuzzy, offset, limit);
	}
	
	@Override
	public Item getItemById(int id) throws SQLException {
		return itemDao.getItemById(id);
	}

	@Override
	public List<ItemV> get(String name, int offset, int limit)
			throws SQLException {
		return null;
	}

	@Override
	public List<ItemCreatedBy> getItemCreatedBy(int id) throws SQLException {
		List<ItemCreatedBy> createdBys = itemDao.getItemCreatedBy(id);
		if (createdBys.size() > 0) {				
			for (ItemCreatedBy createdBy : createdBys) {
				List<ItemReagent> reagent = itemDao.getItemReagent(createdBy.getSpellId());
				createdBy.setReagent(reagent);
			}
		}
		return createdBys;
	}	

}
