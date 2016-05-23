package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.AuctionHouseDataDao;
import com.bnade.wow.dao.impl.AuctionHouseDataDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.service.AuctionHouseDataService;

public class AuctionHouseDataServiceImpl implements AuctionHouseDataService {
	
	private AuctionHouseDataDao auctionHouseDataDao;
	
	public AuctionHouseDataServiceImpl() {
		auctionHouseDataDao = new AuctionHouseDataDaoImpl();
	}

	@Override
	public void save(List<Auction> aucs, int realmId) throws SQLException {
		auctionHouseDataDao.save(aucs, realmId);		
	}

	@Override
	public void deleteAll(int realmId) throws SQLException {
		auctionHouseDataDao.deleteAll(realmId);		
	}

	@Override
	public List<Auction> getByOwner(String owner, int realmId) throws SQLException {
		return auctionHouseDataDao.getByOwner(owner, realmId);
	}

	@Override
	public List<Auction> getByItemId(int itemId, String bounsList, int realmId)
			throws SQLException {		
		return auctionHouseDataDao.getByItemId(itemId, bounsList, realmId);
	}
	
}
