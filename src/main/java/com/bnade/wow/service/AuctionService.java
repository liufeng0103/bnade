package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Auction2;

public interface AuctionService {
	
	List<Auction> getAuctionsByRealmOwner(int realmId, String owner) throws SQLException;
	
	List<Auction2> getAuctionsByRealmOwner2(int realmId, String owner) throws SQLException;
	
	List<Auction> get810101Auctions() throws SQLException;
}
