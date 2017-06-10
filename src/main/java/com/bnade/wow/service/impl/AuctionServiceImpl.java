package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bnade.wow.dao.AuctionDao;
import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.AuctionDaoImpl;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Auction2;
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
	
	private List<Auction2> foldAuctionsByOwnerBuyout2(List<Auction2> aucs) {
		Collections.sort(aucs, new Comparator<Auction2>() {
			public int compare(Auction2 auc1, Auction2 auc2) {
				return auc1.getBuyout() / auc1.getQuantity() - auc2.getBuyout()
						/ auc2.getQuantity() > 0 ? -1 : 1;
			}
		});
		int size = aucs.size();
		if (size <= 1) {
			return aucs;
		}
		List<Auction2> result = new ArrayList<>();
		Auction2 preAuc = aucs.get(0);
		for (int i = 1; i < size; i++) {
			Auction2 auc = aucs.get(i);
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

	@Override
	public List<Auction2> getAuctionsByRealmOwner2(int realmId, String name) throws SQLException {
		List<Auction2> aucs = auctionDao.getAuctionsByRealmOwner(realmId, name);
		return foldAuctionsByOwnerBuyout2(aucs);
	}

	@Override
	public List<Auction> get810101Auctions() throws SQLException {
		List<Auction> aucs = auctionDao.get810101Auctions();
		for (Auction auc : aucs) {
			auc.setItemObj(itemDao.getItemById(auc.getItem()));
		}
		return aucs;
	}
	
}
