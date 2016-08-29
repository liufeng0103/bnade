package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.wow.client.WoWAPI;
import com.bnade.wow.client.WowHeadClient;
import com.bnade.wow.client.model.Item;


/**
 * 添加拍卖行出现的新物品
 * 
 * @author liufeng0103
 *
 */
public class ItemCatcher {
	
	private static Logger logger = LoggerFactory.getLogger(ItemCatcher.class);
	
	private QueryRunner run;	
	private WoWAPI wowAPI;
	
	public ItemCatcher() {
		run = new QueryRunner(DBUtil.getDataSource());		
		wowAPI = new WowHeadClient(); // 国服不好用切换wowhead api获取物品信息
	}
	
	public void process() {
		logger.info("开始添加物品");
		try {
			List<Long> ids1 = run.query("select distinct id from t_item", new ColumnListHandler<Long>());
			logger.info("现有物品数		: " + ids1.size());
			List<Long> ids2 = run.query("select distinct item from t_ah_min_buyout_data", new ColumnListHandler<Long>());
			logger.info("最新拍卖物品数	: " + ids2.size());
			ids2.removeAll(ids1);
			logger.info("需要更新		: " + ids2.size());
			// 用于邮件标题
			System.out.println("新品:" + (ids2.size() - 1));
			int notFound = 0;
			for (int i = 0; i < ids2.size(); i++) {
				long itemId = ids2.get(i);
				if (itemId == 82800 || itemId == 154) {
					logger.info("不更新宠物笼id " + itemId);
					continue;
				}
				try {
					Item item = wowAPI.getItem(new Long(itemId).intValue());
					run.update("insert into t_item (id,description,name,icon,itemClass,itemSubClass,inventoryType,itemLevel) values(?,?,?,?,?,?,?,?)", item.getId(), "", item.getName(),
							item.getIcon(), item.getItemClass(), item.getSubClass(), item.getInventorySlot(), item.getLevel());
					logger.info("保存物品:" + item);					
				} catch (Exception e1) {
					notFound++;
					logger.info("找不到物品ID "+ itemId);
					e1.printStackTrace();
				}
//				break;
				// 用于邮件标题
				System.out.println("未找到:" + notFound);
			}
		} catch (Exception e) {
			logger.error("添加物品出错:{}", e.getMessage());
			// 用于邮件标题
			System.out.println("异常");
			e.printStackTrace();
		}
		logger.info("物品信息更新完毕");
	}
	
	public void refreshItems() {
		
		try {
			run.update("truncate mt_item");
			run.update("insert into mt_item (id,name,icon,itemLevel,hot) select id,name,icon,itemLevel,hot from t_item");			
		} catch (SQLException e) {
			logger.error("刷新mt_item表出错:{}", e.getMessage());
			// 用于邮件标题
			System.out.println("严重异常");
			e.printStackTrace();
		}	
		logger.info("刷新mt_item表完毕");
	}

	public static void main(String[] args) {
		ItemCatcher itemCatcher = new ItemCatcher();
		itemCatcher.process();
		itemCatcher.refreshItems();
	}	

}
