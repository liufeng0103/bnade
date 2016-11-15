package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.User;
import com.bnade.wow.po.UserItemNotification;
import com.bnade.wow.po.UserMailValidation;
import com.bnade.wow.po.UserRealm;

public interface UserDao {
	
	void save(User user) throws SQLException;
	
	void update(User user) throws SQLException;

	User getUserByOpenID(String openID) throws SQLException;
	User getUserByID(int id) throws SQLException;
	User getUserByToken(String token) throws SQLException;
	void updateUserToken(int id, String token) throws SQLException;
	// ---------------------- 用户服务器 ----------------------
	void addRealm(UserRealm realm) throws SQLException;
	void deleteRealm(UserRealm realm) throws SQLException;
	List<UserRealm> getRealms(int userId) throws SQLException;
	
	// ---------------------- 用户物品通知 ----------------------
	void addItemNotification(UserItemNotification itemNotification) throws SQLException;
	List<UserItemNotification> getItemNotifications(int userId) throws SQLException;
	void deleteItemNotifications(List<UserItemNotification> itemNotifications) throws SQLException;
	void updateItemNotification(UserItemNotification itemNotification) throws SQLException;
	List<UserItemNotification> getItemNotificationsByRealmId(int realmId) throws SQLException;
	
	void updateEmailNotifications(List<UserItemNotification> itemNotifications) throws SQLException;
	
	void addMailValidation(UserMailValidation userMailValidation) throws SQLException;
	UserMailValidation getMailValidationById(int id) throws SQLException;
	void deleteMailValidationById(int id) throws SQLException;
	void updateMailValidationById(UserMailValidation userMailValidation) throws SQLException;
}