package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.WowTokenDao;
import com.bnade.wow.dao.impl.WowTokenDaoImpl;
import com.bnade.wow.po.WowToken;
import com.bnade.wow.service.WowTokenService;

public class WowTokenServiceImpl implements WowTokenService {
	
	private WowTokenDao dao;
	
	public WowTokenServiceImpl() {		
		dao = new WowTokenDaoImpl();
	}

	@Override
	public void save(WowToken wowToken) throws SQLException {
		dao.save(wowToken);		
	}
	
	@Override
	public void save(List<WowToken> wowTokens) throws SQLException {
		dao.save(wowTokens);		
	}

	@Override
	public WowToken getByUpdated(long updated) throws SQLException {
		return dao.getByUpdated(updated);
	}

	@Override
	public List<WowToken> getAll() throws SQLException {
		return dao.getAll();
	}

	@Override
	public void deleteAll() throws SQLException {
		dao.deleteAll();		
	}

}
