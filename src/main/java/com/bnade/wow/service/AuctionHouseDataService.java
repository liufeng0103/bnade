package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Auction;

/**
 * 拍卖行所有拍卖数据的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionHouseDataService {

	void save(List<Auction> aucs, int realmId) throws SQLException;

	void deleteAll(int realmId) throws SQLException;

	List<Auction> getByOwner(String owner, int realmId) throws SQLException;

}
