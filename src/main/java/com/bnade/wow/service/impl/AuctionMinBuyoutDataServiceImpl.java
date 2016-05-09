package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.AuctionHouseMinBuyoutDataDao;
import com.bnade.wow.dao.impl.AuctionHouseMinBuyoutDataDaoImpl;
import com.bnade.wow.po.Auction;
import com.bnade.wow.service.AuctionHouseMinBuyoutDataService;

public class AuctionMinBuyoutDataServiceImpl implements AuctionHouseMinBuyoutDataService {
	
//	private static Logger logger = LoggerFactory.getLogger(AuctionMinBuyoutDataServiceImpl.class);
	
	private AuctionHouseMinBuyoutDataDao auctionMinBuyoutDataDao;
	
	public AuctionMinBuyoutDataServiceImpl() {
		auctionMinBuyoutDataDao = new AuctionHouseMinBuyoutDataDaoImpl();
	}

	@Override
	public void save(List<Auction> aucs) throws SQLException {
		auctionMinBuyoutDataDao.save(aucs);		
	}

	@Override
	public void deleteAll(int realmId) throws SQLException {
		auctionMinBuyoutDataDao.deleteAll(realmId);		
	}

	@Override
	public List<Auction> getByItemIdAndBounsList(int itemId, String bounsList)
			throws SQLException {		
		return auctionMinBuyoutDataDao.getByItemIdAndBounsList(itemId, bounsList);
	}

	@Override
	public List<Auction> getPetsByIdAndBreed(int petId, int breedId)
			throws SQLException {
		return auctionMinBuyoutDataDao.getPetsByIdAndBreed(petId, breedId);
	}

}
