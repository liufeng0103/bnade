package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.HotItemDao;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.QueryItem;

public class HotItemDaoImpl implements HotItemDao {
	private QueryRunner run;
	
	public HotItemDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public void add(QueryItem item) throws SQLException {
		run.update("insert into t_query_item (itemId,ip,createdAt) values(?,?,?)", item.getItemId(), item.getIp(), item.getCreatedAt());
	}

	@Override
	public void deleteAllQuiredItem() throws SQLException {
		run.update("truncate t_query_item");		
	}
	
	@Override
	public List<HotItem> getItemQuiredCountAfterCreatedAt(long createdAt) throws SQLException {
		return run.query("select itemId,count(itemId) as queried from t_query_item where createdAt>=? group by itemId", new BeanListHandler<HotItem>(HotItem.class), createdAt);
	}

	@Override
	public void add(List<HotItem> items) throws SQLException {
		
		
	}

	@Override
	public HotItem getHotByItemIdAndPeriod(int itemId, int period)
			throws SQLException {		
		return run.query("select itemId,queried,period from t_hot_item where period>=? and itemId=?", new BeanHandler<HotItem>(HotItem.class), period, itemId);
	}

	@Override
	public void saveHotItem(HotItem item) throws SQLException {
		run.update("insert into t_hot_item (itemId,queried,period) values (?,?,?)", item.getItemId(), item.getQueried(), item.getPeriod());		
	}

	@Override
	public void updateHotItem(HotItem item) throws SQLException {
		run.update("update t_hot_item set queried=? where period=? and itemId=?", item.getQueried(), item.getPeriod(), item.getItemId());
	}

	@Override
	public List<HotItem> getHotItemsByPeriod(int period) throws SQLException {
		return run.query("select itemId,queried,period from t_hot_item where period=?", new BeanListHandler<HotItem>(HotItem.class), period);
	}

	@Override
	public void deleteAllHotItemByPeriod(int period) throws SQLException {
		run.update("delete from t_hot_item where period=?", period);		
	}

	@Override
	public List<HotItem> getHotItemsByPeriodSortByQueried(int period, int limit)
			throws SQLException {
		return run.query("select itemId,queried,period from t_hot_item where period=? order by queried desc limit ?", new BeanListHandler<HotItem>(HotItem.class), period, limit);
	}
	
}
