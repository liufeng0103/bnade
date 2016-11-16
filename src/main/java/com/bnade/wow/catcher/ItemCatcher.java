package com.bnade.wow.catcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.util.HttpClient;
import com.bnade.wow.client.WowAPI;
import com.bnade.wow.client.WowClient;
import com.bnade.wow.client.WowClientException;
import com.bnade.wow.client.WowHeadClient;
import com.bnade.wow.client.model.Item;
import com.bnade.wow.client.model.XItem;
import com.bnade.wow.client.model.XItemCreatedBy;
import com.bnade.wow.client.model.XItemReagent;


/**
 * 添加拍卖行出现的新物品
 * 
 * @author liufeng0103
 *
 */
public class ItemCatcher {
	
	private static Logger logger = LoggerFactory.getLogger(ItemCatcher.class);
	
	private QueryRunner run;	
	private WowAPI wowAPI;
	
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
			}
			// 用于邮件标题
			System.out.println("未找到:" + notFound);
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
			run.update("insert into mt_item (id,name,icon,itemClass,itemSubClass,inventoryType,itemLevel,hot) select id,name,icon,itemClass,itemSubClass,inventoryType,itemLevel,hot from t_item");			
		} catch (SQLException e) {
			logger.error("刷新mt_item表出错:{}", e.getMessage());
			// 用于邮件标题
			System.out.println("严重异常");
			e.printStackTrace();
		}	
		logger.info("刷新mt_item表完毕");
	}
	
	/*
	 * 更新item bonus信息
	 */
	public void updateItemBounus() {		 
		try {
			List<ItemBonus> ibs = run.query("select itemId,bonusList from t_item_bonus", new BeanListHandler<ItemBonus>(ItemBonus.class));
			logger.info("当前数据库Item Bonus数量" + ibs.size());
			List<ItemBonus> aibs = run.query("select item as itemId,context,bonusLists as bonusList from t_ah_min_buyout_data where bonusLists <> '' group by item,bonusLists", new BeanListHandler<ItemBonus>(ItemBonus.class));
			logger.info("当前最新排卖行数据中的Item Bonus数量" + aibs.size());
			aibs.removeAll(ibs);
			logger.info("最新的Item Bonus数量" + aibs.size());
			for (ItemBonus ib : aibs) {
				run.update("insert into t_item_bonus (itemId,bonusList) values (?,?)", ib.getItemId(), ib.getBonusList());
				logger.info(ib.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * 添加新的宠物, 国服api不好用 临时解决方案
	 */
	public void addNewPets() {
		HttpClient client = new HttpClient();
		client.setGzipSupported(true);
		try {
			List<Long> ids = run.query("select distinct petSpeciesId from t_ah_min_buyout_data where item = 82800 and petSpeciesId not in (select id from t_pet)", new ColumnListHandler<Long>());
			for (Long tmpId : ids) {
				int id = tmpId.intValue();
				String petTooltip = client.get("https://www.battlenet.com.cn/wow/zh/pet/"+id+"/tooltip");
				int index = petTooltip.indexOf("<span class=\"name\">");
				String name = petTooltip.substring(index + "<span class=\"name\">".length(), petTooltip.indexOf("</span>", index));
				int index2 = petTooltip.indexOf("http://content.battlenet.com.cn/wow/icons/36/");
				String icon = petTooltip.substring(index2+"http://content.battlenet.com.cn/wow/icons/36/".length(), petTooltip.indexOf(".jpg", index2));
				System.out.println(id + name + " " + icon);
				run.update("insert into t_pet (id,name,icon) values (?,?,?)", id, name, icon); 
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addPetStats() {
		try {
			List<PetStats> pets = run.query("select distinct petSpeciesId as speciesId,petbreedId as breedId from t_ah_min_buyout_data where item = 82800", new BeanListHandler<PetStats>(PetStats.class));
			List<PetStats> pets2 = run.query("select speciesId,breedId from t_pet_stats", new BeanListHandler<PetStats>(PetStats.class));
			pets.removeAll(pets2);
			WowClient client = new WowClient();
			client.setRegion(WowClient.REGION_TW);
			for (PetStats pet : pets) {
				com.bnade.wow.client.model.PetStats petStats = client.getPetStats(pet.getSpeciesId(), pet.getBreedId(), 25, 3);
				System.out.println(petStats);
				run.update("insert into t_pet_stats (speciesId,breedId,petQualityId,level,health,power,speed) values (?,?,?,?,?,?,?)", pet.getSpeciesId(),pet.getBreedId(),3,25,petStats.getHealth(),petStats.getPower(),petStats.getSpeed());
			}	
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}		 
	}
	
	/*
	 * 获取所有制造业物品的制造配方
	 */
	public void processItemCreatedBy() {		
		WowHeadClient client = new WowHeadClient();
		try {			
			List<Long> ids1 = run.query("select distinct id from mt_item", new ColumnListHandler<Long>());
			logger.info("现有物品数		: {}", ids1.size());
			List<Long> ids2 = run.query("select distinct itemId from t_item_processed where type = 1 union all select distinct itemId from t_item_processed where type = 2", new ColumnListHandler<Long>());
			logger.info("已处理物品数	: {}", ids2.size());
			ids1.removeAll(ids2);
			logger.info("需要更新		: {}", ids1.size());
			for (Long id : ids1) {
				int itemId = id.intValue();
				logger.info("开始处理{}", itemId);
				XItem item = client.getItem2(itemId);
				if (item.getCreatedBy() != null) {
					for (XItemCreatedBy itemCb : item.getCreatedBy()) {
						System.out.println(itemCb);		
						if (itemCb.getId() != 0 && itemCb.getReagent() != null) {
							run.update("insert into t_item_created_by (itemId,spellId,name,icon,minCount,maxCount) values (?,?,?,?,?,?)", itemId, itemCb.getId(), itemCb.getName(), itemCb.getIcon(), itemCb.getMinCount(), itemCb.getMaxCount());
							for (XItemReagent itemR : itemCb.getReagent()) {								
								run.update("insert into t_item_reagent (spellId,itemId,name,quality,icon,count,buyPrice) values (?,?,?,?,?,?,?)", itemCb.getId(), itemR.getId(), itemR.getName(), itemR.getQuality(), itemR.getIcon(), itemR.getCount(),0);								
							}	
						} else {
							logger.info("物品[{}{}]配方获取失败=================================", item.getName(), itemId);
						}
					}
					logger.info("物品[{}{}]制造配方已添加", item.getName(), itemId);
					run.update("insert into t_item_processed (itemId,type) values (?,?)", itemId, 1);
				} else {
					logger.info("物品[{}{}]无法被制造", item.getName(), itemId);
					run.update("insert into t_item_processed (itemId,type) values (?,?)", itemId, 2);
				}				
//				break; // 用于测试
			}			
		} catch (WowClientException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ItemCatcher itemCatcher = new ItemCatcher();
//		itemCatcher.process();
//		itemCatcher.refreshItems();
//		itemCatcher.updateItemBounus();
//		itemCatcher.addNewPets();
//		itemCatcher.addPetStats();
		itemCatcher.processItemCreatedBy();
	}	

}
