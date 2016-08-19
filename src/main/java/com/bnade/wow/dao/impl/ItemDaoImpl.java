package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.ItemV;

public class ItemDaoImpl implements ItemDao {

	private QueryRunner run;
	
	public ItemDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public List<Item> getItemsByName(String name) throws SQLException {		
		return getItemsByName(name, false, 0, -1);
	}
	
	@Override
	public List<Item> getItemsByName(String name, boolean isFuzzy, int offset, int limit) throws SQLException {
		String condition = "=? ";
		if (isFuzzy) {
			condition = "like ?";
			name = "%" + name + "%";
		}
		condition += " order by hot desc ";
		if (limit > 0) {
			condition += " limit ?,?";
			return run.query("select id,name,icon,itemLevel from mt_item where name " + condition, new BeanListHandler<Item>(Item.class), name, offset, limit);
		} else {
			return run.query("select id,name,icon,itemLevel from mt_item where name " + condition, new BeanListHandler<Item>(Item.class), name);
		}		
	}

	@Override
	public List<String> getBonusList(int itemId) throws SQLException {		
		return run.query("select bonusList from t_item_bonus where itemId=?", new ColumnListHandler<String>(), itemId);
	}

	@Override
	public Item getItemById(int id) throws SQLException {		
		return run.query("select id,name,icon,itemLevel from mt_item where id=?", new BeanHandler<Item>(Item.class), id);
	}

	@Override
	public List<ItemV> get(String name, int offset, int limit) throws SQLException {
		name = "%" + name + "%";
		return run.query("select id,name,icon,itemLevel,type from v_item where name like ? order by hot desc limit ?,?", new BeanListHandler<ItemV>(ItemV.class), name, offset, limit);
	}	

}
