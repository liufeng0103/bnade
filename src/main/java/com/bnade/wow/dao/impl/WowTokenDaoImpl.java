package com.bnade.wow.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.WowTokenDao;
import com.bnade.wow.po.WowToken;

/**
 * 时光徽章相关的数据库操作
 * 
 * @author liufeng0103
 *
 */
public class WowTokenDaoImpl implements WowTokenDao {

	private QueryRunner run;
	
	public WowTokenDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public void save(WowToken wowToken) throws SQLException {
		run.update("insert into t_wowtoken (buy,updated) values (?,?)", wowToken.getBuy(), wowToken.getUpdated());
	}

	@Override
	public void save(List<WowToken> wowTokens) throws SQLException {		
		Connection con = DBUtil.getDataSource().getConnection();
		try {
			boolean autoCommit = con.getAutoCommit();
			con.setAutoCommit(false);			
			Object[][] params = new Object[wowTokens.size()][2];
			for (int i = 0; i < wowTokens.size(); i++) {
				WowToken token = wowTokens.get(i);
				params[i][0] = token.getBuy();
				params[i][1] = token.getUpdated();				
			}
			run.batch(con, "insert into t_wowtoken (buy,updated) values(?,?)", params);
			con.commit();
			con.setAutoCommit(autoCommit);
		} finally {
			DbUtils.closeQuietly(con);
		}
	}

	@Override
	public WowToken getByUpdated(long updated) throws SQLException {
		return run.query("select buy,updated from t_wowtoken where updated=?", new BeanHandler<WowToken>(WowToken.class), updated);
	}

	@Override
	public List<WowToken> getAll() throws SQLException {		
		return run.query("select buy,updated from t_wowtoken", new BeanListHandler<WowToken>(WowToken.class));
	}

	@Override
	public void deleteAll() throws SQLException {
		run.update("truncate t_wowtoken");		
	}

}
