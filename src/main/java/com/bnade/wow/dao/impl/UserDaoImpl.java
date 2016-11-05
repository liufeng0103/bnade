package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.UserDao;
import com.bnade.wow.po.User;
import com.bnade.wow.po.UserItemNotification;
import com.bnade.wow.po.UserRealm;

public class UserDaoImpl implements UserDao {

	private QueryRunner run;

	public UserDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	@Override
	public void save(User user) throws SQLException {
		run.update(
				"insert into t_user (openId,nickname,createTime) values (?,?,?)",
				user.getOpenID(), user.getNickname(),
				System.currentTimeMillis());
	}

	@Override
	public User getUserByOpenID(String openID) throws SQLException {
		return run.query(
				"select id,openId,email,nickname from t_user where openId=?",
				new BeanHandler<User>(User.class), openID);
	}

	@Override
	public void addRealm(UserRealm realm) throws SQLException {
		run.update("insert into t_user_realm (userId,realmId) values (?,?)",
				realm.getUserId(), realm.getRealmId());
	}

	@Override
	public void deleteRealm(UserRealm realm) throws SQLException {
		run.update("delete from t_user_realm where userId=? and realmId=?",
				realm.getUserId(), realm.getRealmId());
	}

	@Override
	public List<UserRealm> getRealms(int userId) throws SQLException {
		return run
				.query("select userId,realmId,r.lastModified from t_user_realm ur join t_realm r on ur.realmId = r.id where userId=?",
						new BeanListHandler<UserRealm>(UserRealm.class), userId);
	}

	@Override
	public void addItemNotification(UserItemNotification item)
			throws SQLException {
		run.update(
				"insert into t_user_item_notification (userId,realmId,itemId,isInverted,price) values (?,?,?,?,?)",
				item.getUserId(), item.getRealmId(), item.getItemId(),
				item.getIsInverted(), item.getPrice());
	}

	@Override
	public List<UserItemNotification> getItemNotifications(int userId)
			throws SQLException {
		return run
				.query("select userId,realmId,itemId,i.name as itemName,isInverted,price,emailNotification from t_user_item_notification n join mt_item i on n.itemId = i.id  where n.userId=?",
						new BeanListHandler<UserItemNotification>(
								UserItemNotification.class), userId);
	}

	@Override
	public void deleteItemNotification(UserItemNotification itemN)
			throws SQLException {
		run.update(
				"delete from t_user_item_notification where userId=? and realmId=? and itemId=? and isInverted=?",
				itemN.getUserId(), itemN.getRealmId(), itemN.getItemId(),
				itemN.getIsInverted());
	}

}
