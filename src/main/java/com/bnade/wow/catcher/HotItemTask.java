package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.utils.TimeUtils;
import com.bnade.wow.dao.HotItemDao;
import com.bnade.wow.dao.TaskDao;
import com.bnade.wow.dao.impl.HotItemDaoImpl;
import com.bnade.wow.dao.impl.TaskDaoImpl;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.Task;

public class HotItemTask {

	private static Logger logger = LoggerFactory.getLogger(HotItemTask.class);
	
	private HotItemDao hotItemDao;
	private TaskDao taskDao;
	
	public HotItemTask() {
		taskDao = new TaskDaoImpl();
		hotItemDao = new HotItemDaoImpl();
	}
	
	public void clearQueriedItem() throws SQLException {
		hotItemDao.deleteAllQuiredItem();		
		logger.info("删除查询的所有item数据");
	}
	
	public void updateHotItemCount() throws SQLException, ParseException {
		logger.info("开始更新每天搜索的物品");
		Task task = taskDao.getByType(Task.HOTITEM_TASK);
		long lastUpdated = 0;
		long current = System.currentTimeMillis();
		if (task != null) {
			lastUpdated = task.getLastUpdated();
		}
		List<HotItem> hotItems = hotItemDao.getItemQuiredCountAfterCreatedAt(lastUpdated);
		logger.info("上次更新时间{}之后有新数据{}条", new Date(lastUpdated), hotItems.size());
		// 当天0点的时间，long表示
		long todayTime = TimeUtils.parse(TimeUtils.getDate()).getTime();
		for (HotItem hotItem : hotItems) {
			HotItem dbHot = hotItemDao.getByDatetimeAndItemId(todayTime, hotItem.getItemId());
			if (dbHot != null) {
				dbHot.setQueried(dbHot.getQueried() + hotItem.getQueried());
				hotItemDao.updateHotItem(dbHot);
			} else {
				hotItem.setDateTime(todayTime);
				hotItemDao.saveHotItem(hotItem);
			}						
		}
		if (task != null) {
			task.setLastUpdated(current);
			taskDao.updateHotItemTask(task);
		} else {
			Task tmpTask = new Task();
			tmpTask.setLastUpdated(current);
			tmpTask.setType(Task.HOTITEM_TASK);
			taskDao.saveHotItemTask(tmpTask);
		}	
		logger.info("更新task状态时间{}", new Date(current));
		logger.info("更新每天搜索的物品完毕");
	}
	
	public static void main(String[] args) throws SQLException, ParseException {
//		args = new String[1];		
//		args[0] = "update";
//		args[0] = "clear";
		if (args.length > 0) {
			String parm = args[0];
			switch(parm) {
				case "update":
					new HotItemTask().updateHotItemCount();
					break;
				case "clear":
					new HotItemTask().clearQueriedItem();
					break;
			}
		} else {
			new HotItemTask().updateHotItemCount();
		}		
	}
}
