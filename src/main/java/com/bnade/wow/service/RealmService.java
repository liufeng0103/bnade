package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Realm;

public interface RealmService {

	Realm getByName(String name) throws SQLException;
	
	int save(Realm realm) throws SQLException;
	
	int update(Realm realm) throws SQLException;
	
	public List<Realm> getAll() throws SQLException;
	
}
