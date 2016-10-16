package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Item;
import com.bnade.wow.po.ItemCreatedBy;
import com.bnade.wow.po.ItemReagent;
import com.bnade.wow.po.ItemV;

public interface ItemDao {

	List<Item> getItemsByName(String name) throws SQLException;

	List<Item> getItemsByName(String name, boolean isFuzzy, int offset, int limit) throws SQLException;

	Item getItemById(int id) throws SQLException;

	List<String> getBonusList(int itemId) throws SQLException;

	List<ItemV> get(String name, int offset, int limit) throws SQLException;
	
	List<ItemCreatedBy> getItemCreatedBy(int itemId) throws SQLException;
	
	List<ItemReagent> getItemReagent(int spellId) throws SQLException;
	
}
