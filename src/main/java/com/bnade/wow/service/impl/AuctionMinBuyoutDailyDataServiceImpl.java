package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.AuctionHouseMinBuyoutDailyDataDao;
import com.bnade.wow.dao.impl.AuctionHouseMinBuyoutDailyDataDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.service.AuctionHouseMinBuyoutDailyDataService;

public class AuctionMinBuyoutDailyDataServiceImpl implements AuctionHouseMinBuyoutDailyDataService {
	
	private AuctionHouseMinBuyoutDailyDataDao auctionMinBuyoutDailyDataDao;
	
	public AuctionMinBuyoutDailyDataServiceImpl() {
		auctionMinBuyoutDailyDataDao = new AuctionHouseMinBuyoutDailyDataDaoImpl();
	}

	@Override
	public void save(List<Auction> aucs, String date, int realmId) throws SQLException {
		auctionMinBuyoutDailyDataDao.save(aucs, date, realmId);		
	}

	@Override
	public List<Auction> get(int itemId, String bounsList, String date, int realmId) throws SQLException {
		return auctionMinBuyoutDailyDataDao.get(itemId, bounsList, date, realmId);
	}

	@Override
	public List<Auction> get(String date, int realmId) throws SQLException {		
		return auctionMinBuyoutDailyDataDao.get(date, realmId);
	}

	@Override
	public void drop(String date, int realmId) throws SQLException {
		auctionMinBuyoutDailyDataDao.drop(date, realmId);		
	}

}
