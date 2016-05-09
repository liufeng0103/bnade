package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 所有服务器最新的物品最低一口价数据的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionHouseMinBuyoutDataDao {

	void save(List<Auction> aucs) throws SQLException;
	
	void deleteAll(int realmId) throws SQLException;
	
	List<Auction> getByItemIdAndBounsList(int itemId, String bounsList) throws SQLException;
	
	List<Auction> getPetsByIdAndBreed(int petId, int breedId) throws SQLException;
	
}
