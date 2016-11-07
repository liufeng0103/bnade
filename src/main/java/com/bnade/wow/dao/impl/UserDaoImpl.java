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
import com.bnade.wow.po.UserMailValidation;
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
	public void update(User user) throws SQLException {
		run.update(
				"update t_user set email=?, validated=?, nickname=? where id=?",
				user.getEmail(), user.getValidated(), user.getNickname(),
				user.getId());
	}

	@Override
	public User getUserByOpenID(String openID) throws SQLException {
		return run
				.query("select id,openId,email,nickname,validated from t_user where openId=?",
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
	public void deleteItemNotifications(List<UserItemNotification> itemNs)
			throws SQLException {
		Object[][] params = new Object[itemNs.size()][4];
		for (int i = 0; i < itemNs.size(); i++) {
			UserItemNotification itemN = itemNs.get(i);
			params[i][0] = itemN.getUserId();
			params[i][1] = itemN.getRealmId();
			params[i][2] = itemN.getItemId();
			params[i][3] = itemN.getIsInverted();
		}
		run.batch(
				"delete from t_user_item_notification where userId=? and realmId=? and itemId=? and isInverted=?",
				params);
	}

	@Override
	public void updateItemNotification(UserItemNotification itemN)
			throws SQLException {
		run.update(
				"update t_user_item_notification set price=? where userId=? and realmId=? and itemId=? and isInverted=?",
				itemN.getPrice(), itemN.getUserId(), itemN.getRealmId(),
				itemN.getItemId(), itemN.getIsInverted());
	}

	@Override
	public void updateEmailNotifications(List<UserItemNotification> itemNs)
			throws SQLException {
		Object[][] params = new Object[itemNs.size()][5];
		for (int i = 0; i < itemNs.size(); i++) {
			UserItemNotification itemN = itemNs.get(i);
			params[i][0] = itemN.getEmailNotification();
			params[i][1] = itemN.getUserId();
			params[i][2] = itemN.getRealmId();
			params[i][3] = itemN.getItemId();
			params[i][4] = itemN.getIsInverted();
		}
		run.batch(
				"update t_user_item_notification set emailNotification=? where userId=? and realmId=? and itemId=? and isInverted=?",
				params);
	}

	@Override
	public void addMailValidation(UserMailValidation userM) throws SQLException {
		run.update(
				"insert into t_user_mail_validation (userId,acode,expired) values (?,?,?)",
				userM.getUserId(), userM.getAcode(), userM.getExpired());
	}

	@Override
	public UserMailValidation getMailValidationById(int id) throws SQLException {
		return run
				.query("select userId,acode,expired from t_user_mail_validation where userId=?",
						new BeanHandler<UserMailValidation>(
								UserMailValidation.class), id);
	}

	@Override
	public void deleteMailValidationById(int id) throws SQLException {
		run.update("delete from t_user_mail_validation where userId=?", id);
	}

	@Override
	public void updateMailValidationById(UserMailValidation userM)
			throws SQLException {
		run.update(
				"update t_user_mail_validation set acode=?,expired=? where userId=?",
				userM.getAcode(), userM.getExpired(), userM.getUserId());
	}

	@Override
	public User getUserByID(int id) throws SQLException {
		return run
				.query("select id,openId,email,nickname,validated from t_user where id=?",
						new BeanHandler<User>(User.class), id);
	}

	@Override
	public List<UserItemNotification> getItemNotificationsByRealmId(int realmId)
			throws SQLException {
		return run
				.query(" select userId,realmId,itemId,i.name as itemName,email,isInverted,price from t_user_item_notification n join t_user u on n.userId=u.id join mt_item i on i.id=n.itemId where n.realmId=? and n.emailNotification=1 and u.validated=1",
						new BeanListHandler<UserItemNotification>(
								UserItemNotification.class), realmId);
	}

}
