package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.ItemRule;
import com.bnade.wow.po.ItemRuleMatch;

public interface ItemRuleDao {

	List<ItemRule> getAll() throws SQLException;
	
	List<ItemRuleMatch> getAllMatched() throws SQLException;
}
