package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

public interface AuctionHouseMinBuyoutDataService {
	
	void save(List<Auction> aucs) throws SQLException;
	
	void deleteAll(int realmId) throws SQLException;
	
	List<Auction> getByItemIdAndBounsList(int itemId, String bounsList) throws SQLException;
	
	List<Auction> getPetsByIdAndBreed(int petId, int breedId) throws SQLException;
}
 