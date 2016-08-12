package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Item;
import com.bnade.wow.po.ItemV;

public interface ItemService {
	
	List<Item> getItemsByName(String name) throws SQLException;
	
	List<Item> getItemsByName(String name, boolean isFuzzy, int offset, int limit) throws SQLException;
	
	Item getItemById(int id) throws SQLException;
	
	List<ItemV> get(String name, int offset, int limit) throws SQLException;
}
