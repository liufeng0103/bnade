package com.bnade.wow.catcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.po.OwnerItem;

/**
 * 计算每种物品最低价，以及返回一些统计数据 
 * 1. 最大拍卖id
 * 2. 玩家数量
 * 3. 所有物品的最低一口价
 * 
 * @author liufeng0103
 *
 */
public class AuctionDataProcessor {
	private int maxAucId = 0;

	private Map<String, JAuction> minByoutAuctions;
	private Map<String, Map<Integer, Integer>> owners = new HashMap<>();
	private List<OwnerItem> items = new ArrayList<>();
	
	public AuctionDataProcessor() {
		minByoutAuctions = new HashMap<>();
	}
	
	public void process(List<JAuction> auctions) {
		for (JAuction auction : auctions) {
			addOwnerItem(auction);
			if (auction.getAuc() > maxAucId) {
				maxAucId = auction.getAuc();
			}			
			if (auction.getBuyout() != 0) {
				String key = "" + auction.getItem() + auction.getPetSpeciesId() + auction.getPetBreedId() + auction.getBonusLists();
				// 计算单价
				long buyout = auction.getBuyout();
				int quantity = auction.getQuantity();
				auction.setBuyout(buyout/quantity);
				JAuction minBuyoutAuction = minByoutAuctions.get(key);
				if (minBuyoutAuction == null) {
					minByoutAuctions.put(key, auction);
				} else if (minBuyoutAuction.getBuyout() > auction.getBuyout()) {
					// 计算总数量
					auction.setQuantity(auction.getQuantity() + minBuyoutAuction.getQuantity());
					minByoutAuctions.put(key, auction);
				} else {
					// 计算总数量
					minBuyoutAuction.setQuantity(auction.getQuantity() + minBuyoutAuction.getQuantity());				
				} 
			}			
		}
	}
	
	private void addOwnerItem(JAuction auc) {		
		String owner = auc.getOwner();
		int itemId = auc.getItem();
		int quantity = auc.getQuantity();
		Map<Integer, Integer> itemQuantity = owners.get(owner);
		if (itemQuantity == null) {
			Map<Integer, Integer> tmpItemQ = new HashMap<>();
			tmpItemQ.put(itemId, quantity);
			owners.put(owner, tmpItemQ);
		} else {
			Integer itemQ = itemQuantity.get(itemId);
			if (itemQ == null) {
				itemQuantity.put(itemId, quantity);
			} else {
				itemQuantity.put(itemId, itemQ + quantity);
			}
		}		
	}

	public int getMaxAucId() {
		return maxAucId;
	}
	
	public int getPlayerQuantity() {
		return owners.size();
	}
	
	public List<OwnerItem> getOwnerItems() {
		if (items.size() == 0) {
			for (Map.Entry<String, Map<Integer, Integer>> entry : owners.entrySet()) {
				Map<Integer, Integer> itemQ = entry.getValue();
				for (Map.Entry<Integer, Integer> entry2 : itemQ.entrySet()) {
					OwnerItem item = new OwnerItem();
					item.setOwner(entry.getKey());
					item.setItem(entry2.getKey());
					item.setQuantity(entry2.getValue());
					items.add(item);
				}
			}
		}		
		return items;
	}
	
	public List<JAuction> getMinBuyoutAuctions() {
		return new ArrayList<>(minByoutAuctions.values());
	}
	
}