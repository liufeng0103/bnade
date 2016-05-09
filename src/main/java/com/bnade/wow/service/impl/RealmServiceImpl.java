package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.RealmDao;
import com.bnade.wow.dao.impl.RealmDaoImpl;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.RealmService;

public class RealmServiceImpl implements RealmService {
	
	private RealmDao realmDao;
	
	public RealmServiceImpl() {
		realmDao = new RealmDaoImpl();
	}

	@Override
	public Realm getByName(String name) throws SQLException {
		return realmDao.getByName(name);
	}

	@Override
	public int save(Realm realm) throws SQLException {
		return realmDao.save(realm);
	}

	@Override
	public int update(Realm realm) throws SQLException {		
		return realmDao.update(realm);
	}

	@Override
	public List<Realm> getAll() throws SQLException {
		return realmDao.getAll();
	}

}
