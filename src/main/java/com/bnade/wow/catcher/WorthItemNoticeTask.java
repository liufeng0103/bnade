package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.utils.DBUtils;
import com.bnade.wow.client.model.Auction;
import com.bnade.wow.po.ItemRule;

public class WorthItemNoticeTask {
	
	private final Logger logger = LoggerFactory.getLogger(WorthItemNoticeTask.class);
	
	private QueryRunner run;
	
	public WorthItemNoticeTask() {
		run = new QueryRunner(DBUtils.getDataSource());
	}
	
	public void a(Map<String, Auction> minByoutAuctions, int realmId, long lastModified) {
		// 获取所有设置的规则
		List<ItemRule> rules = null;
		try {
			run.update("delete from t_item_rule_match where realmId=?", realmId);
			rules = run.query("select itemId,ltBuy,gtBuy from t_item_rule", new BeanListHandler<ItemRule>(ItemRule.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (ItemRule rule : rules) {
			String key = "" + rule.getItemId() + 0 + 0 + "";
			Auction auc = minByoutAuctions.get(key);
			if (auc != null) {
				long buyout = auc.getBuyout();
				boolean matched = false;
				if (rule.getLtBuy() != 0 && rule.getLtBuy() >= buyout) {
					logger.info("{} {}低于设置价格{}", rule.getItemId(), buyout, rule.getLtBuy());
					matched = true;
				}
				if (rule.getGtBuy() != 0 && rule.getGtBuy() <= buyout) {
					logger.info("{} {}高于设置价格{}", rule.getItemId(), buyout, rule.getLtBuy());
					matched = true;
				}
				try {
					if (matched) {
						run.update("insert into t_item_rule_match (realmId,itemId,buyout,lastModified) values(?,?,?,?)", realmId, rule.getItemId(), buyout, lastModified);	
					}					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
		}
	}
	
	public static void main(String[] args) {

	}

}
