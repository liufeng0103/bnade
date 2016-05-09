package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.HistoryAuction;


/**
 * 把每天的数据按每天几个时段计算后保存到历史表，对该历史表的操作
 * 
 * @author liufeng0103
 *
 */
public interface AuctionHouseMinBuyoutMonthlyDataDao {

	void save(List<HistoryAuction> aucs, String month, int realmId) throws SQLException;
	
	List<HistoryAuction> get(int itemId, String bounsList, String month, int realmId) throws SQLException;
	
}
