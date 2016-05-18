package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.ItemRuleDao;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.dao.impl.ItemRuleDaoImpl;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.ItemRule;
import com.bnade.wow.po.ItemRuleMatch;
import com.bnade.wow.service.ItemRuleService;
import com.bnade.wow.vo.ItemRuleDto;
import com.bnade.wow.vo.ItemRuleMatchDto;

public class ItemRuleServiceImpl implements ItemRuleService {
	
	private ItemRuleDao itemRuleDao;
	private ItemDao itemDao;
	
	public ItemRuleServiceImpl() {
		itemDao = new ItemDaoImpl();
		itemRuleDao = new ItemRuleDaoImpl();
	}

	@Override
	public List<ItemRuleDto> getAll() throws SQLException {
		List<ItemRule> rules = itemRuleDao.getAll();
		List<ItemRuleDto> itemDtos = new ArrayList<>();
		for (ItemRule rule : rules) {
			Item item = itemDao.getItemById(rule.getItemId());
			ItemRuleDto itemDto = new ItemRuleDto();
			itemDto.setItemId(rule.getItemId());
			itemDto.setLtBuy(rule.getLtBuy());
			itemDto.setGtBuy(rule.getGtBuy());
			if (item != null) {
				itemDto.setName(item.getName());
			}
			itemDtos.add(itemDto);
		}
		return itemDtos;
	}

	@Override
	public List<ItemRuleMatchDto> getAllMatched() throws SQLException {
		List<ItemRuleMatch> matchs = itemRuleDao.getAllMatched();
		List<ItemRuleMatchDto> matchDtos = new ArrayList<>();
		for (ItemRuleMatch match : matchs) {
			ItemRuleMatchDto matchDto = new ItemRuleMatchDto();
			matchDto.setRealmId(match.getRealmId());
			matchDto.setItemId(match.getItemId());
			matchDto.setBuyout(match.getBuyout());
			matchDto.setLastModified(match.getLastModified());
			Item item = itemDao.getItemById(match.getItemId());
			if (item != null) {
				matchDto.setName(item.getName());
			}
			matchDtos.add(matchDto);
		}
		return matchDtos;
	}
}
