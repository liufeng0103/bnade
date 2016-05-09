package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Item;

public interface ItemService {
	
	List<Item> getItemsByName(String name) throws SQLException;
	
	List<Item> getItemsByName(String name, boolean isFuzzy) throws SQLException;
	
	Item getItemById(int id) throws SQLException;
	
}
