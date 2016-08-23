package com.bnade.wow.task;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.util.FileUtil;
import com.bnade.util.TimeUtil;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Realm;
import com.bnade.wow.po.Task;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.RealmServiceImpl;
import com.bnade.wow.task.model.TSMAppData;

/**
 * 用于生成插件TradeSkillMaster_AppHelper的AppData.lua数据
 * 
 * @author liufe
 *
 */
public class TSMAppDataTask {
	
	private static Logger logger = LoggerFactory.getLogger(TSMAppDataTask.class);
	
	private QueryRunner run;
	
	public TSMAppDataTask() {
		run = new QueryRunner(DBUtil.getDataSource());
	} 
	
	public void process() {
		logger.info("开始");
		String processDate = TimeUtil.getDate();		
		RealmService realmService = new RealmServiceImpl();		
		try {
			// 1. 获取所有服务器信息
			for (Realm realm : realmService.getAll()) {
				int realmId = realm.getId();
//				realmId = 38; // 用于测试
				logger.info("开始处理{}-{}", realmId, realm.getName());
				// 2. 判断服务器是否已处理过
				Task task = run.query("select type,realmId,date,lastUpdated from t_task where type=? and realmId=? and date=?", new BeanHandler<Task>(Task.class), Task.TSM_APP_DATA_TASK, realmId, processDate);
				if (task == null) {
					List<TSMAppData> appDatas = run.query("select a.itemId,a.buy,b.minBuyout,b.historical,b.quantity from t_item_market a join (select item,min(buyout) as minBuyout,avg(buyout) as historical,avg(quantity) as quantity from t_ah_min_buyout_data_"+TimeUtil.getDate(-1)+"_"+realmId+" group by item ) b on a.itemId=b.item", new BeanListHandler<TSMAppData>(TSMAppData.class));
					long updateTime = new Date().getTime() / 1000;
					saveFile(realmId, updateTime, appDatas);
					logger.info("文件已保存");
					// 标记该服务器已处理					
					run.update("insert into t_task (type,realmId,date,lastUpdated) values (?,?,?,?)", Task.TSM_APP_DATA_TASK, realmId, processDate, updateTime);
					logger.info("标记已处理");					
					try {
						run.update("insert into t_tsm_realm_data_version (realmId, version) values(?,?)", realmId,String.valueOf(updateTime));
						logger.info("插入数据版本");
					} catch (SQLException e) {
						run.update("update t_tsm_realm_data_version set version=? where realmId=?", String.valueOf(updateTime), realmId);
						logger.info("更新数据版本");
					}
				} else {
					logger.info("{}已处理{}的数据", realm.getName(), processDate);
				}				
//				break; // 用于测试
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private TSMAppData calAppData(int realmId, int itemId, List<Auction> aucs) {
		// 按一口价排序
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(aucs, new Comparator<Auction>() {
            public int compare(Auction auc1, Auction auc2) {
                return auc1.getBuyout() <= auc2.getBuyout() ? -1 : 1;
            }
        });
		return new TSMAppData(realmId, itemId, aucs.get(0).getBuyout(), getPrice(aucs));
	}
	
	private long getPrice(List<Auction> aucs) {	
		float range = 0.8f;
		int size = aucs.size();
		if (size == 1) {
			return aucs.get(0).getBuyout();
		} else {
			int maxSize = (int) (size * range);
			long tmpPrice = 0;
			List<Auction> tmpItems = new ArrayList<>();
			for (int i = 0; i < maxSize; i++) {
				tmpItems.add(aucs.get(i));
				tmpPrice += aucs.get(i).getBuyout()/ maxSize;
			}
			if (tmpPrice * 3 < aucs.get(maxSize - 1).getBuyout()) {
//				System.out.println("高于平均价格3倍，重新计算");
				tmpPrice = getPrice(tmpItems);
			}
			return tmpPrice;
		}		
	}
	
	private void saveFile(int realmId, long updateTime, List<TSMAppData> appDatas) throws SQLException {		
		StringBuffer sb = new StringBuffer();
		for (TSMAppData data : appDatas) {
			if (sb.length() > 0) {
				sb.append(",");				
			}
			sb.append("{");
			sb.append(data.getItemId());
			sb.append(",");
			sb.append(data.getBuy());
			sb.append(",");
			sb.append(data.getMinBuyout());
			sb.append(",");
			sb.append(data.getQuantity());
			sb.append("}");			
		}
		String result = "select(2, ...).LoadData(\"AUCTIONDB_MARKET_DATA\",\"{xxrealmxx}\",[[return {downloadTime=" + updateTime + ",fields={\"itemString\",\"marketValue\",\"minBuyout\",\"historical\",\"numAuctions\"},data={" + sb.toString() + "}}]])";
		new File("./appData").mkdirs();
		FileUtil.stringToFile(result, "./appData/" + realmId + ".lua");
	}
	
	public static void main(String[] args) throws SQLException {
		long start = System.currentTimeMillis();
		new TSMAppDataTask().process();	
		logger.info("完毕用时{}" , TimeUtil.format(System.currentTimeMillis() - start));
	}

}