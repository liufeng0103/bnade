package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.WowToken;

public interface WowTokenDao {

	void save(WowToken wowToken) throws SQLException;
	
	void save(List<WowToken> wowTokens) throws SQLException;
	
	WowToken getByUpdated(long updated) throws SQLException;
	
	List<WowToken> getAll() throws SQLException;
	
	void deleteAll() throws SQLException;
	
}
