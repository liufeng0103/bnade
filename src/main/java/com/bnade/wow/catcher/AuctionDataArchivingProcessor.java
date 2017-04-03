package com.bnade.wow.catcher;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bnade.utils.TimeUtils;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.HistoryAuction;
import com.bnade.wow.po.Pet;

public class AuctionDataArchivingProcessor {
	
	// 每天分成几个时段
	private static final int PERIOD = 4; 

	public List<HistoryAuction> process(List<Auction> aucs, String date) throws ParseException, CatcherException {
		long startTime = TimeUtils.parse(date).getTime();
		long period = TimeUtils.DAY / PERIOD;
		// map用来保存每个时段物品信息
		Map<Long, Map<String, HistoryAuction>> periodMap = new HashMap<>();
		// 初始化map
		for (int i = 1; i <= PERIOD; i++) {
			Map<String, HistoryAuction> result = new HashMap<>();
			if (i == PERIOD) {
				periodMap.put(startTime + TimeUtils.DAY, result);				
			} else {
				periodMap.put(startTime + period * i, result);				
			}				
		}
		for (Auction auc : aucs) {
			// 不归档宠物价格
			if (auc.getItem() == Pet.PET_ITEM_ID) {
				continue;
			}
			String key = "" + auc.getItem() + auc.getPetSpeciesId() + auc.getPetBreedId() + auc.getBonusLists();
			long lastModified = auc.getLastModifed();
			// 计算数据属于哪个时段
			long aucPeriod = 0;
			for (int i = 1; i <= PERIOD; i++) {	
				long tmpTime = startTime + period * i;
				if (i == 1) {
					aucPeriod = tmpTime;
				} else if (i == PERIOD) {	
					if (lastModified >= (tmpTime - period)) {
						aucPeriod = startTime + TimeUtils.DAY;
					}					
				} else {
					if (lastModified >= (tmpTime - period)) {
						aucPeriod = tmpTime;
					}
				}				
			}
			// 保存各时段每种物品的总价格，总数量和总物品数量
			Map<String, HistoryAuction> aucMap = periodMap.get(aucPeriod);
			if (aucMap != null) {
				HistoryAuction tmpAuc = aucMap.get(key);
				if (tmpAuc == null) {
					HistoryAuction historyAuction = new HistoryAuction();
					historyAuction.setItem(auc.getItem());
					historyAuction.setPetSpeciesId(auc.getPetSpeciesId());
					historyAuction.setPetBreedId(auc.getPetBreedId());
					historyAuction.setBonusLists(auc.getBonusLists());
					historyAuction.setBuyout(auc.getBuyout()); // 计算单价
					historyAuction.setQuantity(auc.getQuantity());
					historyAuction.setCount(1);
					historyAuction.setLastModifed(aucPeriod);
					aucMap.put(key, historyAuction);
				} else {
					tmpAuc.setBuyout(tmpAuc.getBuyout() + auc.getBuyout()); // 计算单价
					tmpAuc.setQuantity(tmpAuc.getQuantity() + auc.getQuantity());
					tmpAuc.setCount(tmpAuc.getCount() + 1);
				}
			} else {
				throw new CatcherException("获取不到时段" + aucPeriod + "的map");
			}
		}
		// 计算平均价格，平均数量
		List<HistoryAuction> result = new ArrayList<>();
		for (Map<String, HistoryAuction> historyAucs : periodMap.values()) {			
			for (HistoryAuction auc : historyAucs.values()) {
				auc.setBuyout(auc.getBuyout() / auc.getCount());
				auc.setQuantity(auc.getQuantity() / auc.getCount());
				result.add(auc);
			}
		}
		return result;
	}
}
