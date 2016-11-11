package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

public interface AuctionService {
	
	List<Auction> getAuctionsByRealmOwner(int realmId, String owner) throws SQLException;
	
}
