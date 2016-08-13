package com.bnade.wow.catcher;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.util.TimeUtil;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.service.HotItemService;
import com.bnade.wow.service.impl.HotItemServiceImpl;

/**
 * 更新物品的搜索热度
 * 
 * 获取一周内的所有被搜索的物品的搜索次数，更新到t_item表中，用于搜索物品时的排序，
 * 使用户更容易定位到他们想找的物品
 * @author liufeng0103
 *
 */
public class RefreshItemHotTask {
	
	private static Logger logger = LoggerFactory.getLogger(RefreshItemHotTask.class);
	
	public static void main(String[] args) throws Exception {
		logger.info("开始刷新物品热度数据");		
//		Connection con = DBUtil.getDataSource().getConnection();
		try {
			QueryRunner run = new QueryRunner(DBUtil.getDataSource());
			long weekStart = TimeUtil.parse(TimeUtil.getDate(-7)).getTime();
			HotItemService hotItemService = new HotItemServiceImpl();
			List<HotItem> hotItems = hotItemService.getGroupItemIdAfterDatetime(weekStart, 20000);
			logger.info("获取一周内被搜索的物品数{}", hotItems.size());
			int count = 0;
//			Object[][] parms = new Object[hotItems.size()][2];
//			boolean defaultCommit = con.getAutoCommit();
//			con.setAutoCommit(false);
			for (int i = 0; i < hotItems.size(); i++) {
				HotItem hotItem = hotItems.get(i);
				run.update("update t_item set hot=? where id=?", hotItem.getQueried(), hotItem.getItemId());
				count++;
				if (count % (hotItems.size() / 100) == 0 ) {
					logger.info("已处理{}%", count * 100/hotItems.size() + 1);	
				}	
//				parms[i][0] = hotItem.getQueried();
//				parms[i][1] = hotItem.getItemId();
			}
//			run.batch(con, "update t_item set hot=? where id=?", parms);
//			con.commit();
//			con.setAutoCommit(defaultCommit);
			logger.info("运行完毕");	
		} finally {
//			con.close();
		}
		
	}
	
}
