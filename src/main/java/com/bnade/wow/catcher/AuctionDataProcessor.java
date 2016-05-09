package com.bnade.wow.catcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bnade.wow.client.model.JAuction;

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
	private Set<String> players;
	private Map<String, JAuction> minByoutAuctions;
	
	public AuctionDataProcessor() {
		players = new HashSet<>();
		minByoutAuctions = new HashMap<>();
	}
	
	public void process(List<JAuction> auctions) {
		for (JAuction auction : auctions) {
			if (auction.getAuc() > maxAucId) {
				maxAucId = auction.getAuc();
			}
			players.add(auction.getOwner());
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

	public int getMaxAucId() {
		return maxAucId;
	}
	
	public int getPlayerQuantity() {
		return players.size();
	}
	
	public List<JAuction> getMinBuyoutAuctions() {
		return new ArrayList<>(minByoutAuctions.values());
	}
}
