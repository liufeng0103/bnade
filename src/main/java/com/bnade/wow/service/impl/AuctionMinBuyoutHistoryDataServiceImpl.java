package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.AuctionHouseMinBuyoutMonthlyDataDao;
import com.bnade.wow.dao.impl.AuctionHouseMinBuyoutMonthlyDataDaoImpl;
import com.bnade.wow.po.HistoryAuction;
import com.bnade.wow.service.AuctionHouseMinBuyoutHistoryDataService;

public class AuctionMinBuyoutHistoryDataServiceImpl implements AuctionHouseMinBuyoutHistoryDataService {
	
	private AuctionHouseMinBuyoutMonthlyDataDao auctionHouseMinBuyoutMonthlyDataDao;
	
	public AuctionMinBuyoutHistoryDataServiceImpl() {
		auctionHouseMinBuyoutMonthlyDataDao = new AuctionHouseMinBuyoutMonthlyDataDaoImpl();	
	}

	@Override
	public void save(List<HistoryAuction> aucs, String month, int realmId) throws SQLException {
		auctionHouseMinBuyoutMonthlyDataDao.save(aucs, month, realmId);		
	}

	@Override
	public List<HistoryAuction> get(int itemId, String bounsList, String month, int realmId) throws SQLException {
		return auctionHouseMinBuyoutMonthlyDataDao.get(itemId, bounsList, month, realmId);
	}

}
