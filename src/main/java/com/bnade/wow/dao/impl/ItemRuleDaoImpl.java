package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.utils.DBUtils;
import com.bnade.wow.dao.ItemRuleDao;
import com.bnade.wow.po.ItemRule;
import com.bnade.wow.po.ItemRuleMatch;

public class ItemRuleDaoImpl implements ItemRuleDao {

	private QueryRunner run;
	
	public ItemRuleDaoImpl() {
		run = new QueryRunner(DBUtils.getDataSource());
	}
	
	@Override
	public List<ItemRule> getAll() throws SQLException {
		return run.query("select itemId,ltBuy,gtBuy from t_item_rule", new BeanListHandler<ItemRule>(ItemRule.class));
	}

	@Override
	public List<ItemRuleMatch> getAllMatched() throws SQLException {		
		return run.query("select realmId,itemId,buyout,lastModified from t_item_rule_match", new BeanListHandler<ItemRuleMatch>(ItemRuleMatch.class));
	}

}
