package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.utils.DBUtils;
import com.bnade.wow.dao.RealmDao;
import com.bnade.wow.po.Realm;

public class RealmDaoImpl implements RealmDao {
	
	private QueryRunner run;
	
	public RealmDaoImpl() {
		run = new QueryRunner(DBUtils.getDataSource());
	}

	@Override
	public Realm getByName(String name) throws SQLException {		
		return run.query("select id,name,url,lastModified,maxAucId,auctionQuantity,playerQuantity,itemQuantity,lastUpdateTime from t_realm where name=?", new BeanHandler<Realm>(Realm.class), name);
	}

	@Override
	public int save(Realm realm) throws SQLException {		
		return run.update("insert into t_realm (name,url,lastModified,maxAucId,auctionQuantity,playerQuantity,itemQuantity,lastUpdateTime) values (?,?,?,?,?,?,?,?)", 
				realm.getName(), realm.getUrl(), realm.getLastModified(), realm.getMaxAucId(),
				realm.getAuctionQuantity(), realm.getPlayerQuantity(), realm.getItemQuantity(),
				System.currentTimeMillis());
	}

	@Override
	public int update(Realm realm) throws SQLException {		
		return run.update("update t_realm set url=?,lastModified=?,maxAucId=?,auctionQuantity=?,playerQuantity=?,itemQuantity=?,lastUpdateTime=? where name=?",
				realm.getUrl(), realm.getLastModified(), realm.getMaxAucId(), realm.getAuctionQuantity(),
				realm.getPlayerQuantity(), realm.getItemQuantity(), System.currentTimeMillis(), realm.getName());
	}

	@Override
	public List<Realm> getAll() throws SQLException {		
		return run.query("select id,name,type,url,lastModified,maxAucId,auctionQuantity,playerQuantity,itemQuantity,lastUpdateTime from t_realm", new BeanListHandler<Realm>(Realm.class));
	}
}
