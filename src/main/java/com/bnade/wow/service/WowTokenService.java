package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.WowToken;

/**
 * 时光徽章相关操作
 * 
 * @author liufeng0103
 *
 */
public interface WowTokenService {

	void save(WowToken wowToken) throws SQLException;
	
	void save(List<WowToken> wowTokens) throws SQLException;
	
	WowToken getByUpdated(long updated) throws SQLException;
	
	List<WowToken> getAll() throws SQLException;
	
	void deleteAll() throws SQLException;
	
}
