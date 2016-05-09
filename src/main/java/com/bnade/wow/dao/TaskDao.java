package com.bnade.wow.dao;

import java.sql.SQLException;

import com.bnade.wow.po.Task;

/**
 * 爬虫程序相关的数据库操作，比如记录程序状态信息等
 * @author liufeng0103
 *
 */
public interface TaskDao {

	void addArchivedTask(Task task) throws SQLException;
	
	Task getArchivedTask(Task task) throws SQLException;
	
	Task getByType(int type) throws SQLException;
	
	void updateLastUpdatedForHotItemTask(long lastUpdated) throws SQLException;
	
	void saveHotItemTask(Task task) throws SQLException;
	
}
