package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public void clearDailyHotItem() throws SQLException {
		hotItemDao.deleteAllQuiredItem();
		hotItemDao.deleteAllHotItemByPeriod(HotItem.PERIOD_DAY);
		logger.info("删除全天查询的所有item数据");
	}
	
	public void updateDailyHotItem() throws SQLException {
		logger.info("开始更新每天搜索的物品");
		Task task = taskDao.getByType(Task.HOTITEM_TASK);
		long lastUpdated = 0;
		if (task != null) {
			lastUpdated = task.getLastUpdated();
		}
		long current = System.currentTimeMillis();
		List<HotItem> hotItems = hotItemDao.getItemQuiredCountAfterCreatedAt(lastUpdated);
		logger.info("上次更新时间{}之后有新数据{}条", new Date(lastUpdated), hotItems.size());
		for (HotItem hotItem : hotItems) {
			HotItem dbHot = hotItemDao.getHotByItemIdAndPeriod(hotItem.getItemId(), HotItem.PERIOD_DAY);
			hotItem.setPeriod(HotItem.PERIOD_DAY);
			if (dbHot != null) {
				hotItem.setQueried(dbHot.getQueried() + hotItem.getQueried());
				hotItemDao.updateHotItem(hotItem);
			} else {
				hotItemDao.saveHotItem(hotItem);
			}						
		}
		if (task != null) {
			taskDao.updateLastUpdatedForHotItemTask(current);
		} else {
			Task tmpTask = new Task();
			tmpTask.setLastUpdated(current);
			tmpTask.setType(Task.HOTITEM_TASK);
			taskDao.saveHotItemTask(tmpTask);
		}	
		logger.info("更新task状态时间{}", new Date(current));
		logger.info("更新每天搜索的物品完毕");
	}
	
	public void updateWeeklyHotItem() throws SQLException {
		logger.info("开始更新每周搜索的物品");
		List<HotItem> hotItems = hotItemDao.getHotItemsByPeriod(HotItem.PERIOD_DAY);
		logger.info("更新新数据{}条", hotItems.size());
		for (HotItem hotItem : hotItems) {
			HotItem weekHotItem = hotItemDao.getHotByItemIdAndPeriod(hotItem.getItemId(), HotItem.PERIOD_WEEK);
			hotItem.setPeriod(HotItem.PERIOD_WEEK);
			if (weekHotItem != null) {
				hotItem.setQueried(weekHotItem.getQueried() + hotItem.getQueried());
				hotItemDao.updateHotItem(hotItem);
			} else {
				hotItemDao.saveHotItem(hotItem);
			}	
		}
		logger.info("更新每周搜索的物品完毕");
	}
	
	public void clearWeeklyHotItem() throws SQLException {
		hotItemDao.deleteAllHotItemByPeriod(HotItem.PERIOD_WEEK);
		logger.info("删除全周查询的所有item数据");
	}
	
	
	public void updateMonthlyHotItem() throws SQLException {
		logger.info("开始更新每月搜索的物品");
		List<HotItem> hotItems = hotItemDao.getHotItemsByPeriod(HotItem.PERIOD_WEEK);
		logger.info("更新新数据{}条", hotItems.size());
		for (HotItem hotItem : hotItems) {
			HotItem monthHotItem = hotItemDao.getHotByItemIdAndPeriod(hotItem.getItemId(), HotItem.PERIOD_MONTH);
			hotItem.setPeriod(HotItem.PERIOD_MONTH);
			if (monthHotItem != null) {
				hotItem.setQueried(monthHotItem.getQueried() + hotItem.getQueried());
				hotItemDao.updateHotItem(hotItem);
			} else {
				hotItemDao.saveHotItem(hotItem);
			}	
		}
		logger.info("更新每月搜索的物品完毕");
	}
	
	public void clearMonthlyHotItem() throws SQLException {
		hotItemDao.deleteAllHotItemByPeriod(HotItem.PERIOD_MONTH);
		logger.info("删除全周月询的所有item数据");
	}
	
	public static void main(String[] args) throws SQLException {
//		args = new String[1];		
//		args[0] = "updateDaily";
//		args[0] = "clearDaily";
//		args[0] = "updateWeek";
//		args[0] = "clearWeek";
//		args[0] = "updateMonth";
//		args[0] = "clearMonth";
		if (args.length > 0) {
			String parm = args[0];
			switch(parm) {
				case "updateDaily":
					new HotItemTask().updateDailyHotItem();
					break;
				case "clearDaily":
					new HotItemTask().clearDailyHotItem();
					break;
				case "updateWeek":
					new HotItemTask().updateWeeklyHotItem();
					break;
				case "clearWeek":
					new HotItemTask().clearWeeklyHotItem();
					break;
				case "updateMonth":
					new HotItemTask().updateMonthlyHotItem();
					break;
				case "clearMonth":
					new HotItemTask().clearMonthlyHotItem();
					break;
			}
		} else {
			new HotItemTask().updateDailyHotItem();
		}		
	}
}
