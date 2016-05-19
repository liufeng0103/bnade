package com.bnade.wow.analysis;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
	
	private QueryRunner run;
	private AuctionHouseMinBuyoutDataDao auctionHouseMinBuyoutDataDao;
	
	public ItemMarketPriceAnalysis() {
		run = new QueryRunner(DBUtil.getDataSource());
		auctionHouseMinBuyoutDataDao = new AuctionHouseMinBuyoutDataDaoImpl();
	}

	public void calculateAndSaveMarketPrice() throws SQLException {
		// 获取所有的物品id
		List<Long> ids1 = run.query("select id from t_item where id<>"+Pet.PET_ITEM_ID, new ColumnListHandler<Long>());
		logger.info("物品总数量{}条", ids1.size());
		// 获取已处理过的所有物品id
		List<Long> ids2 = run.query("select itemId from t_item_market", new ColumnListHandler<Long>());
		ids1.removeAll(ids2);
		logger.info("已处理{}条，需要处理{}条", ids2.size(), ids1.size());
		int count = 0;
		for (long id : ids1) {
			List<Auction> aucs = auctionHouseMinBuyoutDataDao.getByItemIdAndBounsList(new Long(id).intValue(), null);
			if (aucs.size() > 0) {
				System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
				Collections.sort(aucs, new Comparator<Auction>() {
		            public int compare(Auction auc1, Auction auc2) {
		                return auc1.getBuyout() <= auc2.getBuyout() ? -1 : 1;
		            }
		        });
//				logger.info("Items:{}",aucs);
				long price = getPrice(aucs);
//				logger.info("Item {} Price {}", id, price);
				run.update("insert into t_item_market (itemId,petSpeciesId,petBreedId,bonusLists,buy,realmQuantity) values(?,?,?,?,?,?)", id, 0, 0, "", price, aucs.size());
			} else {
				run.update("insert into t_item_market (itemId,petSpeciesId,petBreedId,bonusLists,buy,realmQuantity) values(?,?,?,?,?,?)", id, 0, 0, "", 0, 0);
			}
			count++;
			if (count % 200 == 0) {
				logger.info("已保存{}条处理{}", count, (count * 100 / ids1.size()) + "%");
			}
		}
	}
	
	public void clearMarketPrice() throws SQLException {
		run.update("truncate t_item_market");
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
		String text = "Bnade_data={[\"updated\"]=\""+new Date()+"\","+sb.toString()+"}";
		
		FileUtil.stringToFile(text, "Data.lua");
		logger.info("保存到文件");		
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