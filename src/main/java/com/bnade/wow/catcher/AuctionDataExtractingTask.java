package com.bnade.wow.catcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.BnadeProperties;
import com.bnade.util.TimeUtil;
import com.bnade.wow.client.WowClient;
import com.bnade.wow.client.WoWClientException;
import com.bnade.wow.client.model.AuctionDataFile;
import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.OwnerItem;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.AuctionHouseDataService;
import com.bnade.wow.service.AuctionHouseMinBuyoutDailyDataService;
import com.bnade.wow.service.AuctionHouseMinBuyoutDataService;
import com.bnade.wow.service.AuctionHouseOwnerItemService;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.AuctionHouseDataServiceImpl;
import com.bnade.wow.service.impl.AuctionHouseOwnerItemServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutDailyDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutDataServiceImpl;
import com.bnade.wow.service.impl.RealmServiceImpl;

/**
 * 1. 读取数据库该服务器上一次运行的状态
 * 2. 读取配置文件，服务器的更新时间间隔
 * 3. 如果上一次的更新时间没有超过设置的时间间隔，则退出task
 * 4. 否则通过api获取服务器的拍卖行数据更新时间以及数据文件地址
 * 5. 如果api可用	
 *   1. 读取api上数据更新时间和数据库中的时间比较，如果一样则退出task
 *   2. 否则，根据数据文件地址下载数据
 *   3. 更新服务器状态表，更新数据更新时间以及文件下载路径
 * 6. api不可用
 *   1. 通过数据库状态表中的地址下载数据
 *   2. 获取下载数据的最大aucid跟服务器状态表中的aucid比较，如果一样则不更新
 *   2. 否则更新服务器状态表，数据更新时间设为当前时间
 * 7. 清理并保存该服务器当前所有的所有拍卖数据到数据库
 * 8. 计算每个物品的最低价
 * 9. 清理并保存所有最低价到最新一次物品价格表
 * 10. 保存所有最低价到物品历史表
 * 11. 触发其它需要处理的任务
 *  
 * @author liufeng0103
 *
 */
public class AuctionDataExtractingTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(AuctionDataExtractingTask.class);
	
	private boolean useAPIGetData;
	private String realmName;
	private String logHeader;
	private boolean isComplete;
	private WowClient wowClient;	
	private RealmService realmService;
	private AuctionHouseDataService auctionDataService;
	private AuctionHouseOwnerItemService auctionHouseOwnerItemService;
	private AuctionHouseMinBuyoutDataService auctionMinBuyoutDataService;
	private AuctionHouseMinBuyoutDailyDataService auctionMinBuyoutDailyDataService;
	private AuctionDataProcessor auctionDataProcessor;
	private WorthItemNoticeTask worthItemNoticeTask;
	private boolean isApiAvailable = true;

	public AuctionDataExtractingTask(String realmName) {
		this(realmName, true);
	}
	
	public AuctionDataExtractingTask(String realmName, boolean useAPIGetData) {
		this.useAPIGetData = useAPIGetData;
		this.realmName = realmName;
		logHeader = "服务器[" + realmName + "]";
		wowClient = new WowClient();
		realmService = new RealmServiceImpl();		
		auctionDataService = new AuctionHouseDataServiceImpl();
		auctionHouseOwnerItemService = new AuctionHouseOwnerItemServiceImpl();
		auctionMinBuyoutDataService = new AuctionMinBuyoutDataServiceImpl();
		auctionMinBuyoutDailyDataService = new AuctionMinBuyoutDailyDataServiceImpl();
		auctionDataProcessor = new AuctionDataProcessor();
		worthItemNoticeTask = new WorthItemNoticeTask();
	}

	public void process() throws CatcherException, IOException, SQLException {
		if (realmName == null || "".equals(realmName)) {
			throw new CatcherException("要处理的服务器名为空");
		}		
		Realm realm = realmService.getByName(realmName);
		logHeader = "服务器[" + realmName + "-" + realm.getId() + "]";
		if (realm != null) {
			long interval = BnadeProperties.getTask1Interval();
			if (System.currentTimeMillis() - realm.getLastModified() > interval) {
				List<JAuction> auctions = null;	
				if (useAPIGetData) {
					try {
						addInfo("通过api获取拍卖行数据文件信息");
						AuctionDataFile auctionDataFile = wowClient.getAuctionDataFile(realmName);
						addInfo("拍卖行数据文件信息获取完毕");
						if (auctionDataFile.getLastModified() != realm.getLastModified()) {
							addInfo("2次更新间隔{}", TimeUtil.format(auctionDataFile.getLastModified() - realm.getLastModified()));
							addInfo("开始下载拍卖行数据");
							long start = System.currentTimeMillis();
							auctions = wowClient.getAuctionData(auctionDataFile.getUrl());
							addInfo("拍卖行数据下载完毕,共{}条数据用时{}", auctions.size(), TimeUtil.format(System.currentTimeMillis() - start));
							// 更新realm状态信息
							realm.setUrl(auctionDataFile.getUrl());
							realm.setLastModified(auctionDataFile.getLastModified());
						} else {
							addInfo("数据更新时间{}与api获取的更新时间一样，不更新", new Date(realm.getLastModified()));
							return;
						}
					} catch (WoWClientException e) {
						isApiAvailable = false;
						addInfo("获取拍卖行数据文件信息api不好用，使用数据库中的url下载数据文件");
						long start = System.currentTimeMillis();
						addInfo("2次更新间隔{}", TimeUtil.format(start - realm.getLastModified()));
						addInfo("开始下载拍卖行数据");					
						auctions = wowClient.getAuctionData(realm.getUrl());
						addInfo("拍卖行数据下载完毕,共{}条数据用时{}", auctions.size(), TimeUtil.format(System.currentTimeMillis() - start));
						// 更新realm状态信息
						realm.setLastModified(System.currentTimeMillis());
					} 	
				} else {
					addInfo("直接使用url下载数据");
					long start = System.currentTimeMillis();
					addInfo("2次更新间隔{}", TimeUtil.format(start - realm.getLastModified()));
					addInfo("开始下载拍卖行数据");					
					auctions = wowClient.getAuctionData(realm.getUrl());
					addInfo("拍卖行数据下载完毕,共{}条数据用时{}", auctions.size(), TimeUtil.format(System.currentTimeMillis() - start));
					// 更新realm状态信息
					realm.setLastModified(System.currentTimeMillis());
				}
				List<Auction> tmpAucs = new ArrayList<>();
				copy(auctions, tmpAucs, realm.getId(), realm.getLastModified());
				auctionDataProcessor.process(auctions);
				if (auctionDataProcessor.getMaxAucId() != realm.getMaxAucId() || (isApiAvailable && useAPIGetData)) {
					// 通知低价
					worthItemNoticeTask.a(auctionDataProcessor.getMinBuyoutAuctionMap(), realm.getId(), realm.getLastModified());
					// 1. 保存所有数据
					addInfo("删除上一次拍卖行数据");
					auctionDataService.deleteAll(realm.getId());
					addInfo("开始保存{}条拍卖行数据", tmpAucs.size());
					long start = System.currentTimeMillis();
					auctionDataService.save(tmpAucs, realm.getId());
					addInfo("保存{}条拍卖行数据完毕, 用时{}", auctions.size(), TimeUtil.format(System.currentTimeMillis() - start));
					addInfo("删除上一次玩家物品数数据");
					auctionHouseOwnerItemService.deleteAll(realm.getId());
					List<OwnerItem> ownerItems = auctionDataProcessor.getOwnerItems();
					addInfo("开始保存{}条玩家拍卖物品数", ownerItems.size());
					auctionHouseOwnerItemService.save(ownerItems, realm.getId());
					addInfo("保存{}条玩家拍卖物品数完毕", ownerItems.size());					
					// 2. 保存所有最低一口价数据
					List<JAuction> minBuyoutAuctions = auctionDataProcessor.getMinBuyoutAuctions();
					// 更新服务器拍卖状态信息到t_realm
					realm.setMaxAucId(auctionDataProcessor.getMaxAucId());
					realm.setAuctionQuantity(auctions.size());
					realm.setPlayerQuantity(auctionDataProcessor.getPlayerQuantity());
					realm.setItemQuantity(minBuyoutAuctions.size());					
					addInfo("拍卖数据文件信息更新{}条记录完毕", realmService.update(realm));
					tmpAucs = new ArrayList<>();
					copy(minBuyoutAuctions, tmpAucs, realm.getId(), realm.getLastModified());
					addInfo("开始删除拍卖行最低一口价数据");
					auctionMinBuyoutDataService.deleteAll(realm.getId());
					addInfo("开始保存{}条拍卖行最低一口价数据", tmpAucs.size());
					auctionMinBuyoutDataService.save(tmpAucs);
					addInfo("保存{}条拍卖行最低一口价数据完毕", tmpAucs.size());
					// 3. 保存所有最低一口价数据到历史表
					addInfo("开始保存{}条拍卖行最低一口价数据到历史表", tmpAucs.size());
					auctionMinBuyoutDailyDataService.save(tmpAucs, TimeUtil.getDate(realm.getLastModified()), realm.getId());
					addInfo("保存{}条拍卖行最低一口价数据到历史表完毕", tmpAucs.size());
				} else {
					addInfo("最大的拍卖id{}跟数据库的一样，不更新", realm.getMaxAucId());
				}				
			} else {
				addInfo("上次更新时间{}，未超过设定的更新间隔时间{}，不更新", new Date(realm.getLastModified()), TimeUtil.format(interval));
			}
		}
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {			
			addInfo("开始");
			process();
		} catch (CatcherException | IOException | SQLException e) {
			addError("运行出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			isComplete = true;
			addInfo("完成，用时：" + TimeUtil.format(System.currentTimeMillis() - start));
		}
	}

	private void addInfo(String msg, Object... arguments) {
		logger.info(logHeader + msg, arguments);
	}
	
	private void addError(String msg, Object... arguments) {
		logger.error(logHeader + msg, arguments);
	}
	
//	private void addDebug(String msg, Object... arguments) {
//		logger.debug(logHeader + msg, arguments);;
//	}
	
	private void copy(List<JAuction> jAucs, List<Auction> aucs, int realmId, long lastModified) {
		for (JAuction jAuc : jAucs) {
			Auction auc = new Auction();
			auc.setAuc(jAuc.getAuc());
			auc.setItem(jAuc.getItem());
			auc.setOwner(jAuc.getOwner());
			auc.setOwnerRealm(jAuc.getOwnerRealm());
			auc.setBid(jAuc.getBid());
			auc.setBuyout(jAuc.getBuyout());
			auc.setQuantity(jAuc.getQuantity());
			auc.setTimeLeft(jAuc.getTimeLeft());
			auc.setRand(jAuc.getRand());
			auc.setSeed(jAuc.getSeed());
			auc.setPetSpeciesId(jAuc.getPetSpeciesId());
			auc.setPetLevel(jAuc.getPetLevel());
			auc.setPetBreedId(jAuc.getPetBreedId());
			auc.setContext(jAuc.getContext());
			auc.setBonusLists(jAuc.getBonusLists());
			auc.setRealmId(realmId);
			auc.setLastModifed(lastModified);
			aucs.add(auc);
		}
	}
	
	public boolean isComplete() {
		return isComplete;
	}

	public String getRealmName() {
		return realmName;
	}	
	
	public boolean isApiAvailable() {
		return isApiAvailable;
	}

	public static void main(String[] args) throws Exception {
		new AuctionDataExtractingTask("古尔丹").process();
	}
}
