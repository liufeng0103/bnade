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
		run.update(
				"insert into t_query_item (itemId,ip,createdAt) values(?,?,?)",
				item.getItemId(), item.getIp(), item.getCreatedAt());
	}

	@Override
	public void deleteAllQuiredItem() throws SQLException {
		run.update("truncate t_query_item");
	}

	@Override
	public List<HotItem> getItemQuiredCountAfterCreatedAt(long createdAt)
			throws SQLException {
		return run
				.query("select itemId,count(itemId) as queried from t_query_item where createdAt>=? group by itemId",
						new BeanListHandler<HotItem>(HotItem.class), createdAt);
	}

	@Override
	public void saveHotItem(HotItem item) throws SQLException {
		run.update(
				"insert into t_hot_item (itemId,queried,dateTime) values (?,?,?)",
				item.getItemId(), item.getQueried(), item.getDateTime());
	}

	@Override
	public void updateHotItem(HotItem item) throws SQLException {
		run.update(
				"update t_hot_item set queried=? where dateTime=? and itemId=?",
				item.getQueried(), item.getDateTime(), item.getItemId());
	}

	@Override
	public HotItem getByDatetimeAndItemId(long datetime, int itemId)
			throws SQLException {
		return run
				.query("select itemId,queried,datetime from t_hot_item where datetime=? and itemId=?",
						new BeanHandler<HotItem>(HotItem.class), datetime,
						itemId);
	}

	@Override
	public List<HotItem> getGroupItemIdAfterDatetime(long datetime, int offset,
			int limit) throws SQLException {
		return run
				.query("select itemId,sum(queried) as queried from t_hot_item where datetime>=? group by itemId order by sum(queried) desc limit ?,?",
						new BeanListHandler<HotItem>(HotItem.class), datetime,
						offset, limit);
	}

	@Override
	public List<HotItem> getHotItems(long datetime, int offset, int limit)
			throws SQLException {
		return run
				.query("select hi.itemId,name,i.icon,sum(queried) as queried,im.buy from t_hot_item hi join mt_item i on hi.itemId=i.id join t_item_market im on im.itemId=hi.itemId where datetime=? group by itemId order by sum(queried) desc limit ?,?",
						new BeanListHandler<HotItem>(HotItem.class), datetime,
						offset, limit);
	}

	@Override
	public List<HotItem> getHotItemsById(int id) throws SQLException {
		return run
				.query("select itemId,queried,dateTime from t_hot_item where itemId=?",
						new BeanListHandler<HotItem>(HotItem.class), id);
	}

}
