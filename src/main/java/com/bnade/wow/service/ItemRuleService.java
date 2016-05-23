package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.vo.ItemRuleDto;
import com.bnade.wow.vo.ItemRuleMatchDto;

public interface ItemRuleService {

	List<ItemRuleDto> getAll() throws SQLException;
	
	List<ItemRuleMatchDto> getAllMatched() throws SQLException;
	
}
