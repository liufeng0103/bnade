package com.bnade.wow.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.TaskDao;
import com.bnade.wow.po.Task;

public class TaskDaoImpl implements TaskDao {

	private QueryRunner run;
	
	public TaskDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public void addArchivedTask(Task task) throws SQLException {
		run.update("insert into t_task (type,realmId,date,lastUpdated) values(?,?,?,?)", Task.ARCHIVE_TASK, task.getRealmId(), task.getDate(), System.currentTimeMillis());
	}

	@Override
	public Task getArchivedTask(Task task) throws SQLException {
		return run.query("select realmId,date from t_task where realmId=? and date=?", new BeanHandler<Task>(Task.class), task.getRealmId(), task.getDate());
	}

	@Override
	public Task getByType(int type) throws SQLException {
		return run.query("select type,realmId,date,lastUpdated from t_task where type=?", new BeanHandler<Task>(Task.class), type);
	}

	@Override
	public void updateLastUpdatedForHotItemTask(long lastUpdated) throws SQLException {
		run.update("update t_task set lastUpdated=? where type=?",  lastUpdated, Task.HOTITEM_TASK);		
	}

	@Override
	public void saveHotItemTask(Task task) throws SQLException {
		run.update("insert into t_task (type,realmId,date,lastUpdated) values(?,?,?,?)", Task.HOTITEM_TASK, task.getRealmId(), "", task.getLastUpdated());		
	}	
	
}
