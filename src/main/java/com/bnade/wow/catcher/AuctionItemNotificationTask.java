package com.bnade.wow.catcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.BnadeUtil;
import com.bnade.util.Mail;
import com.bnade.util.TimeUtil;
import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.dao.UserDao;
import com.bnade.wow.dao.impl.UserDaoImpl;
import com.bnade.wow.po.UserItemNotification;

public class AuctionItemNotificationTask {	
	
	private static Logger logger = LoggerFactory.getLogger(AuctionItemNotificationTask.class);
	private static ExecutorService pool = Executors.newFixedThreadPool(10);
	private UserDao userDao;
	
	public AuctionItemNotificationTask() {
		userDao = new UserDaoImpl();
	}

	public void process(Map<String, JAuction> minByoutAuctions, int realmId, long lastModified) {
		pool.execute(() -> {
			try {
				List<UserItemNotification> itemNs = userDao.getItemNotificationsByRealmId(realmId);
				Map<Integer, List<UserItemNotification>> matchedItems = new HashMap<>();
				logger.info("找到{}条服务器{}的物品通知", itemNs.size(), realmId);
				for (UserItemNotification itemN : itemNs) {
					String key = "" + itemN.getItemId() + itemN.getPetSpeciesId() + itemN.getPetBreedId() + itemN.getBonusList();
					JAuction auc = minByoutAuctions.get(key);
					if (auc != null) {
						if (itemN.getIsInverted() == 0) { // 低于
							if (auc.getBuyout() <= itemN.getPrice()) {
								itemN.setMinBuyout(auc.getBuyout());
								List<UserItemNotification> tmpList = matchedItems.get(itemN.getUserId());
								if (tmpList == null) {
									tmpList = new ArrayList<>();
									tmpList.add(itemN);
									matchedItems.put(itemN.getUserId(), tmpList);
								} else {
									tmpList.add(itemN);
								}
							}
						}
						if (itemN.getIsInverted() == 1) { // 高于
							if (auc.getBuyout() >= itemN.getPrice()) {
								itemN.setMinBuyout(auc.getBuyout());
								List<UserItemNotification> tmpList = matchedItems.get(itemN.getUserId());
								if (tmpList == null) {
									tmpList = new ArrayList<>();
									tmpList.add(itemN);
									matchedItems.put(itemN.getUserId(), tmpList);
								} else {
									tmpList.add(itemN);
								}
							}
						}
					}
				}
				pushNotification(matchedItems, realmId);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		});
	}
	
	private void pushNotification(Map<Integer, List<UserItemNotification>> matchedItems, int realmId) {
		if (matchedItems.size() > 0) {
			logger.info("开始推送{}条服务器[{}]", matchedItems.size(), realmId);
			for (Map.Entry<Integer, List<UserItemNotification>> entry : matchedItems.entrySet()) {
				List<UserItemNotification> items = entry.getValue();
				String mail = null;
				String mailContent = "";
				for (UserItemNotification item : items) {
					mailContent += "";
					mail = item.getEmail();
					mailContent += item.getItemName() + " 当前最低一口单价：" + getGold(item.getMinBuyout());
					if (item.getIsInverted() == 0) {
						mailContent += "低与您的价格：" + getGold(item.getPrice());
					} else {
						mailContent += "高与您的价格：" + getGold(item.getPrice());
					}
					mailContent += "\r\n";
				}
				Mail.asynSendSimpleEmail(TimeUtil.getDate2(System.currentTimeMillis()) + " [BNADE] " + items.size() + "条物品满足在[" + BnadeUtil.getRealmNameById(realmId) + "]", mailContent, mail);
			}
			
		}		
	}

	private String getGold(long price) {
		String s = "";
		long gold = price/10000;
		if (gold > 0) {
			s += gold + "g";
			price -= gold * 10000;
		}
		long silver = price / 100;
		if (silver > 0) {
			s += silver + "s";
			price -= silver * 100;
		}
		if (price > 0) {
			s += price + "c";
		}
		return s;
	}

}
