package com.bnade.wow.analysis;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.util.FileUtil;
import com.bnade.wow.analysis.model.MarketItem;
import com.bnade.wow.dao.AuctionHouseMinBuyoutDataDao;
import com.bnade.wow.dao.impl.AuctionHouseMinBuyoutDataDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Pet;

/**
 * 计算和保存物品市场价格
 * 产生插件的数据文件
 * 
 * @author liufeng0103
 *
 */
public class ItemMarketPriceAnalysis {
	
	private static Logger logger = LoggerFactory.getLogger(ItemMarketPriceAnalysis.class);
	
	// 参考的拍卖数量，当大于这个值则按算法计算，否则通过比较上一次的拍卖价格，取低价的那个
	private static final int REFERENCE_AUCTION_COUNT = 85;
	
	private QueryRunner run;
	private AuctionHouseMinBuyoutDataDao auctionHouseMinBuyoutDataDao;
	
	public ItemMarketPriceAnalysis() {
		run = new QueryRunner(DBUtil.getDataSource());
		auctionHouseMinBuyoutDataDao = new AuctionHouseMinBuyoutDataDaoImpl();
	}

	public void calculateAndSaveMarketPrice() throws SQLException {
		long currentTime = System.currentTimeMillis();
		// 获取所有物品参考价格信息
		List<MarketItem> items = run.query("select itemId,buy,realmQuantity from t_item_market", new BeanListHandler<MarketItem>(MarketItem.class));
		Map<Integer, MarketItem> itemMap = getItemMap(items);
		logger.info("数据库中已有物品参考价格{}条", itemMap.size());
		// 获取所有的物品id
		List<Long> ids1 = run.query("select id from mt_item where id<>"+Pet.PET_ITEM_ID, new ColumnListHandler<Long>());
		logger.info("物品总数量{}条", ids1.size());
//		// 获取已处理过的所有物品id
//		List<Long> ids2 = run.query("select itemId from t_item_analysis", new ColumnListHandler<Long>());
//		ids1.removeAll(ids2);
//		logger.info("已处理{}条，需要处理{}条", ids2.size(), ids1.size());
		int count = 0;
		for (long id : ids1) {
			int itemId = new Long(id).intValue();
			List<Auction> aucs = auctionHouseMinBuyoutDataDao.getByItemIdAndBounsList(itemId, null);
			if (aucs.size() > 0 && aucs.size() <= 170) { 
				System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
				Collections.sort(aucs, new Comparator<Auction>() {
		            public int compare(Auction auc1, Auction auc2) {
		                return auc1.getBuyout() <= auc2.getBuyout() ? -1 : 1;
		            }
		        });
//				logger.info("Items:{}",aucs);
				long price = getPrice(aucs);
				int totalQuantity = getTotalQuantity(aucs);
				int realmQuantity = aucs.size();
//				logger.info("Item {} Price {}", id, price);
				MarketItem marketItem = itemMap.get(itemId);
				if (marketItem != null) {
					if (aucs.size() < REFERENCE_AUCTION_COUNT) {
						if (price > marketItem.getBuy() && marketItem.getBuy() != 0) {
							price = marketItem.getBuy();
							realmQuantity = marketItem.getRealmQuantity();							
						} else {
							if (price != marketItem.getBuy()) {
								logger.info("{}参考服务器数量{}少于{}，这次价格{}，历史价格{}数量{}", id, aucs.size(), REFERENCE_AUCTION_COUNT, price, marketItem.getBuy(), marketItem.getRealmQuantity());	
							}							
						}						
					}
					run.update("update t_item_market set buy=?,realmQuantity=? where itemId=?", price, realmQuantity, id);
				} else {
					run.update("insert into t_item_market (itemId,petSpeciesId,petBreedId,bonusLists,buy,realmQuantity) values(?,?,?,?,?,?)", itemId, 0, 0, "", price, realmQuantity);
				}
				run.update("insert into t_item_market_history (itemId,petSpeciesId,petBreedId,bonusLists,buy,realmQuantity,totalQuantity,dateTime) values(?,?,?,?,?,?,?,?)", itemId, 0, 0, "", price, realmQuantity, totalQuantity, currentTime);
//				break;
			}
//			run.update("insert into t_item_analysis (itemId) values(?)", itemId);			
			count++;
			if (count % 200 == 0) {
				logger.info("已保存{}条处理{}", count, (count * 100 / ids1.size()) + "%");
			}
//			break; // 测试用
		}
	}
	
	private Map<Integer, MarketItem> getItemMap(List<MarketItem> items) {
		Map<Integer, MarketItem> itemMap = new HashMap<>();
		for (MarketItem item : items) {
			itemMap.put(item.getItemId(), item);
		}
		return itemMap;
	}
	
	public void clearMarketPrice() throws SQLException {
		run.update("truncate t_item_analysis");
	}
	
	public void exportMarketPriceFile() throws SQLException {
		List<MarketItem> items = run.query("select itemId,buy,realmQuantity from t_item_market where buy <> 0", new BeanListHandler<MarketItem>(MarketItem.class));
		logger.info("获取到{}条数据", items.size());
		StringBuffer sb = new StringBuffer();
		for (MarketItem item : items) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append("[");
			sb.append(item.getItemId());
			sb.append("]={");
			sb.append(item.getBuy());
			sb.append(",");
			sb.append(item.getRealmQuantity());
			sb.append("}");
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addonUpdateTime = sf.format(new Date());
        String text = "Bnade_data={[\"updated\"]=\"" + addonUpdateTime + "\"," + sb.toString() + "}";
        String fileName = "Data.lua";
		new File(fileName).delete();
		FileUtil.stringToFile(text, fileName);
		logger.info("保存到文件");
        updateAddonTime(addonUpdateTime);
        logger.info("插件时间更新");
	}

	private void updateAddonTime(String time) throws SQLException {
        run.update("update t_addon set version=?", time);
    }
	
	private long getPrice(List<Auction> aucs) {	
		float range = 0.8f;
		int size = aucs.size();
		if (size == 1) {
			return aucs.get(0).getBuyout();
		} else {
			int maxSize = (int) (size * range);
			long tmpPrice = 0;
			List<Auction> tmpItems = new ArrayList<>();
			for (int i = 0; i < maxSize; i++) {
				tmpItems.add(aucs.get(i));
				tmpPrice += aucs.get(i).getBuyout()/ maxSize;
			}
			if (tmpPrice * 3 < aucs.get(maxSize - 1).getBuyout()) {
//				System.out.println("高于平均价格3倍，重新计算");
				tmpPrice = getPrice(tmpItems);
			}
			return tmpPrice;
		}		
	}
	
	private int getTotalQuantity(List<Auction> aucs) {	
		int quantity = 0;
		for (Auction auc : aucs) {
			quantity += auc.getQuantity();
		}
		return quantity;
	}
	
	public static void main(String[] args) {
		try {
//			new ItemMarketPriceAnalysis().clearMarketPrice();
			new ItemMarketPriceAnalysis().calculateAndSaveMarketPrice();
			new ItemMarketPriceAnalysis().exportMarketPriceFile();
		} catch (SQLException e) {
			e.printStackTrace();
		};
	}
}