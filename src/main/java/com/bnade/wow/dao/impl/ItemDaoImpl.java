package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.po.AuctionItem;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.ItemClass;
import com.bnade.wow.po.ItemCreatedBy;
import com.bnade.wow.po.ItemReagent;
import com.bnade.wow.po.ItemSubclass;
import com.bnade.wow.po.Pet;
import com.bnade.wow.po.PetStats;

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
		String condition = " name=? ";
		if (isFuzzy) {
			condition = " MATCH (name) AGAINST (? IN BOOLEAN MODE) ";
		}
		condition += " order by hot desc ";
		if (limit > 0) {
			condition += " limit ?,?";
			return run.query("select id,name,icon,itemLevel,itemClass from t_item where " + condition, new BeanListHandler<Item>(Item.class), name, offset, limit);
		} else {
			return run.query("select id,name,icon,itemLevel,itemClass from t_item where " + condition, new BeanListHandler<Item>(Item.class), name);
		}		
	}

	@Override
	public List<String> getBonusList(int itemId) throws SQLException {		
		return run.query("select bonusList from t_item_bonus where itemId=?", new ColumnListHandler<String>(), itemId);
	}

	@Override
	public Item getItemById(int id) throws SQLException {		
		return run.query("select id,name,icon,itemLevel from t_item where id=?", new BeanHandler<Item>(Item.class), id);
	}

	@Override
	public Item getPetItemById(int id) throws SQLException {
		return run.query("select id,name,icon from t_pet where id=?", new BeanHandler<Item>(Item.class), id);
	}

	@Override
	public List<ItemCreatedBy> getItemCreatedBy(int itemId) throws SQLException {
		return run.query("select spellId,name,icon,minCount,maxCount from t_item_created_by where itemId=?", new BeanListHandler<ItemCreatedBy>(ItemCreatedBy.class), itemId);
	}

	@Override
	public List<ItemReagent> getItemReagent(int spellId) throws SQLException {
		return run.query("select itemId,name,quality,icon,count,buyPrice from t_item_reagent where spellId=?", new BeanListHandler<ItemReagent>(ItemReagent.class), spellId);
	}

	@Override
	public List<ItemClass> getItemClasses() throws SQLException {
		List<ItemClass> itemClasses = run.query(
				"select class as itemClass,name from t_item_class",
				new BeanListHandler<ItemClass>(ItemClass.class));
		for (ItemClass itemClass : itemClasses) {
			itemClass.setSubclasses(
				run.query("select subclass,name from t_item_subclass where class=?",
				new BeanListHandler<ItemSubclass>(ItemSubclass.class), itemClass.getItemClass()));
		}
		return itemClasses;
	}

	@Override
	public List<AuctionItem> getItems(String name, Integer itemClass, Integer subclass, int offset, int limit)
			throws SQLException {
		String sql = "select id,name,icon,itemLevel,petSpeciesId from v_item where 1=1 ";
		if (itemClass != null) {
			sql += " and itemClass=" + itemClass;
		}
		if (subclass != null) {
			sql += " and itemSubClass=" + subclass;
		}
		List<AuctionItem> items;
		if (name != null && !"".equals(name)) {
			name = "%" + name + "%";
			sql += " and name like ? ";
			sql += " order by hot desc limit ?,?";
			items = run.query(sql , new BeanListHandler<AuctionItem>(AuctionItem.class), name, offset, limit);
		} else {
			sql += " order by hot desc limit ?,?";
			items = run.query(sql , new BeanListHandler<AuctionItem>(AuctionItem.class), offset, limit);	
		}
		return items;
	}
	
	@Override
	public List<PetStats> getPetStatsById(int id) throws SQLException {		
		return run.query("select speciesId,breedId,petQualityId,level,health,power,speed from t_pet_stats where speciesId=?", new BeanListHandler<PetStats>(PetStats.class), id);
	}
	
	@Override
	public List<AuctionItem> getItemsWithBonuslist(String name, int offset,
			int limit) throws SQLException {
		List<AuctionItem> items = getItems(name, null, null, 0, limit);
		for (AuctionItem item : items) {
			if (item.getId() == Pet.PET_ITEM_ID) {
				item.setPetStatsList(getPetStatsById(item.getPetSpeciesId()));
				item.setBonusList(new ArrayList<>());
			} else {
				item.setPetStatsList(new ArrayList<>());
				item.setBonusList(getBonusList(item.getId()));
			}
		}
		return items;
	}

}
