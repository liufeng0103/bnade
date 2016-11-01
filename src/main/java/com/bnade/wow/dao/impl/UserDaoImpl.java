package com.bnade.wow.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.UserDao;
import com.bnade.wow.po.User;

public class UserDaoImpl implements UserDao {
	
	private QueryRunner run;
	
	public UserDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}

	@Override
	public void save(User user) throws SQLException {
		run.update("insert into t_user (openId,nickname,createTime) values (?,?,?)", user.getOpenID(), user.getNickname(), System.currentTimeMillis());
	}

	@Override
	public User getUserByOpenID(String openID) throws SQLException {
		return run.query("select id,openId,email,nickname from t_user where openId=?", new BeanHandler<User>(User.class), openID);
	}

}
