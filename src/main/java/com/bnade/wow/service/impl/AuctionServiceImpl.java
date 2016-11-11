package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bnade.wow.dao.AuctionDao;
import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.AuctionDaoImpl;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Pet;
import com.bnade.wow.service.AuctionService;

public class AuctionServiceImpl implements AuctionService {

	private ItemDao itemDao;
	private AuctionDao auctionDao;
	
	public AuctionServiceImpl() {
		itemDao = new ItemDaoImpl();
		auctionDao = new AuctionDaoImpl();
	}
	
	@Override
	public List<Auction> getAuctionsByRealmOwner(int realmId, String owner)
			throws SQLException {
		List<Auction> aucs = foldAuctionsByOwnerBuyout(auctionDao.getAuctionsByRealmOwner(realmId, owner, "buyout/quantity"));
		for (Auction auc : aucs) {
			if (Pet.PET_ITEM_ID == auc.getItem()) {
				auc.setItemObj(itemDao.getPetItemById(auc.getPetSpeciesId()));
			} else {
				auc.setItemObj(itemDao.getItemById(auc.getItem()));
			}
		}
		return aucs;
	}
	
	/**
	 * 对相同卖家相同一口单价的物品整合
	 * 
	 * @return
	 */
	private List<Auction> foldAuctionsByOwnerBuyout(List<Auction> aucs) {
		int size = aucs.size();
		if (size <= 1) {
			return aucs;
		}
		List<Auction> result = new ArrayList<>();
		Auction preAuc = aucs.get(0);
		for (int i = 1; i < size; i++) {
			Auction auc = aucs.get(i);
			if (preAuc.getOwner().equals(auc.getOwner()) && (preAuc.getBuyout()/preAuc.getQuantity() == auc.getBuyout()/auc.getQuantity())) {
				preAuc.setBid(preAuc.getBid() + auc.getBid());
				preAuc.setBuyout(preAuc.getBuyout() + auc.getBuyout());
				preAuc.setQuantity(preAuc.getQuantity() + auc.getQuantity());
			} else {
				result.add(preAuc);
				preAuc = auc;
			}
			if (i == size - 1) {
				result.add(preAuc);
			}
		}
		return result;
	}
	
}
