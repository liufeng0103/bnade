package com.bnade.wow.task;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.wow.client.WoWAPI;
import com.bnade.wow.client.WoWClientException;
import com.bnade.wow.client.WowClient;
import com.bnade.wow.client.WowHeadClient;
import com.bnade.wow.po.Item;


/**
 * 同步物品信息
 * 
 * 一般版本更替的时候，会有一些物品被删除或修改，需要同步所有的这些物品
 * 
 * @author liufeng0103
 *
 */
public class SyncItemTask {
	
	private static Logger logger = LoggerFactory.getLogger(SyncItemTask.class);
	
	private QueryRunner run;
	private WoWAPI wowAPI;
	private WowClient client;
	
	public SyncItemTask() {
		run = new QueryRunner(DBUtil.getDataSource());
		wowAPI = new WowHeadClient();
		client = new WowClient();
		client.setRegion(WowClient.REGION_US);
	}
	
	public void process() {
		try {
			List<Long> ids1 = run.query("select id from t_item where itemClass=16", new ColumnListHandler<Long>());
			logger.info("{}", ids1.size());
			int count = 0;
			int diff = 0;
			for (Long id : ids1) {
				count++;
				try {
					int itemId = id.intValue();
					Item item1 = run.query("select * from t_item where id=?", new BeanHandler<Item>(Item.class), itemId);
					String key1 = itemId+item1.getName()+item1.getIcon()+item1.getItemClass()+item1.getItemSubClass()+item1.getInventoryType()+item1.getItemLevel();
					com.bnade.wow.client.model.Item item2 = wowAPI.getItem(itemId);				
					String key2 = itemId+item2.getName()+item2.getIcon()+item2.getItemClass()+item2.getSubClass()+item2.getInventorySlot()+item2.getLevel();						
					if (!key1.equals(key2)) {
						logger.info(key1);
						logger.info(key2);					
						run.update("update t_item set name=?,icon=?,itemClass=?,itemSubClass=?,inventoryType=?,itemLevel=? where id=?", item2.getName(), item2.getIcon(),item2.getItemClass(), item2.getSubClass(),item2.getInventorySlot(),item2.getLevel(),itemId);
	//					break;
						diff++;
						logger.info("共{}已{}不同{}", ids1.size(), count, diff);
					} else {
	//					logger.info("same");
					}
				} catch (WoWClientException e) {
					e.printStackTrace();
				}
			}
			logger.info("共{}已{}不同{}", ids1.size(), count, diff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new SyncItemTask().process();
	}

}
