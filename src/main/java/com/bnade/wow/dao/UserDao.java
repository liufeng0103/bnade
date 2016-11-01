package com.bnade.wow.dao;

import java.sql.SQLException;

import com.bnade.wow.po.User;

public interface UserDao {
	
	void save(User user) throws SQLException;
	
	User getUserByOpenID(String openID) throws SQLException;
	
}
