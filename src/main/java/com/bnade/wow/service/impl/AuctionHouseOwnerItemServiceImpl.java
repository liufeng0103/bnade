package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.AuctionHouseOwnerItemDao;
import com.bnade.wow.dao.impl.AuctionHouseOwnerItemDaoImpl;
import com.bnade.wow.po.OwnerItem;
import com.bnade.wow.po.OwnerItemStatistics;
import com.bnade.wow.service.AuctionHouseOwnerItemService;

public class AuctionHouseOwnerItemServiceImpl implements AuctionHouseOwnerItemService {

	private AuctionHouseOwnerItemDao auctionHouseOwnerItemDao;
	
	public AuctionHouseOwnerItemServiceImpl() {
		auctionHouseOwnerItemDao = new AuctionHouseOwnerItemDaoImpl();
	}
	
	@Override
	public void save(List<OwnerItem> items, int realmId) throws SQLException {
		auctionHouseOwnerItemDao.save(items, realmId);
	}

	@Override
	public void deleteAll(int realmId) throws SQLException {
		auctionHouseOwnerItemDao.deleteAll(realmId);		
	}

	@Override
	public List<OwnerItemStatistics> getOwnerItemStatisticsByRealmId(
			int realmId, String orderBy, int limit) throws SQLException {
		return auctionHouseOwnerItemDao.getOwnerItemStatisticsByRealmId(realmId, orderBy, limit);
	}

}
