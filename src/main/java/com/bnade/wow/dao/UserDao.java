package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.User;
import com.bnade.wow.po.UserItemNotification;
import com.bnade.wow.po.UserRealm;

public interface UserDao {
	
	void save(User user) throws SQLException;
	
	User getUserByOpenID(String openID) throws SQLException;
	
	// ---------------------- 用户服务器 ----------------------
	void addRealm(UserRealm realm) throws SQLException;
	void deleteRealm(UserRealm realm) throws SQLException;
	List<UserRealm> getRealms(int userId) throws SQLException;
	
	// ---------------------- 用户物品通知 ----------------------
	void addItemNotification(UserItemNotification itemNotification) throws SQLException;
	List<UserItemNotification> getItemNotifications(int userId) throws SQLException;
	void deleteItemNotification(UserItemNotification itemNotification) throws SQLException;
}
