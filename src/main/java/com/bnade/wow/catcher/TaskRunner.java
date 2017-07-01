 package com.bnade.wow.catcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.BnadeProperties;
import com.bnade.util.FileUtil;
import com.bnade.util.TimeUtil;

/** 
 * 已重构，新的catcher地址https://github.com/liufeng0103/bnade-catcher
 * TaskRunner用于运行AuctionDataExtractingTask来获取，分析和保存拍卖行数据
 * 
 * 1. 判断TaskRunner是否处于关闭状态(通过shutdown文件判断)，如果关闭则退出TaskRunner 
 * 2. 否则从文件或数据库读取所有需要抓取的服务器名(国服目前一共170个服务器)
 * 3. 从配置文件获取每次处理的线程数，每个线程处理一个服务器
 * 4. 每个线程完成后检查TaskRunner是否需要关闭(通过shutdown文件判断)
 *   1. 如果关闭则线程关闭(服务器任务已经运行完成)，不运行接下来的服务器，其它运行的线程都按1操作，直到所有线程关闭
 *   2. 否则继续处理接下来未处理的服务器
 * 5. 如果TaskRunner需要关闭，在所有线程关闭后关闭TaskRunner
 * 6. 否则继续按2步骤运行
 * 
 * @author liufeng0103
 *
 */
@Deprecated
public class TaskRunner {
	
	private static Logger logger = LoggerFactory.getLogger(TaskRunner.class);	
	// 是否所有线程运行完毕的检查间隔时间
	private static final long CHECK_WAIT_TIME = 10000;
	// 2次运行直接的等待时间
	private static final long WAIT_TIME = 0;
	
	private static final int MAX_API_FAIL_COUNT = 3;
	private int failCount = 0;
	
	private File shutdownFile = new File("shutdown");
	private File runningFile = new File("running");
	private File useUrlGetDataFile = new File("useUrl");

	public boolean isShutdown() {		
		return shutdownFile.exists();
	}
	
	private void exit() {
		runningFile.deleteOnExit();
		logger.info("关闭TaskRunner");		
		System.exit(0);
	}
	
	private boolean useUrlGetData() {
		return useUrlGetDataFile.exists();
	}
	
	public void launch() {
		if (!isShutdown()) {
			while(true) {
				long startTime = System.currentTimeMillis();
				int threadCount = BnadeProperties.getTask1ThreadCount();
				List<String> realmNames = FileUtil.fileLineToList("realmlist.txt");
				if (threadCount > realmNames.size()) {
					threadCount = realmNames.size();
				}
				failCount = 0;
				logger.debug("通过api下载失败次数清0");
				ExecutorService pool = Executors.newFixedThreadPool(threadCount);
				logger.info("启动{}个线程来处理{}个服务器", threadCount, realmNames.size());
				List<AuctionDataExtractingTask> tasks = new ArrayList<AuctionDataExtractingTask>();
				for (int i = 0; i < realmNames.size(); i++) {
					if (isShutdown()) {
						logger.info("TaskRunner准备关闭,等待未完成的Task运行完毕,停止剩下的服务器运行,总共运行{}个,当前服务器[{}]", i + 1, realmNames.get(i));
						break;
					}
					AuctionDataExtractingTask readyTask = null;
					if (failCount >= MAX_API_FAIL_COUNT || useUrlGetData()) {
						readyTask = new AuctionDataExtractingTask(realmNames.get(i), false);
					} else {
						readyTask = new AuctionDataExtractingTask(realmNames.get(i));
					}
					while(true) {
						boolean isTaskAdded = false;
						if (i >= threadCount) {
							for (AuctionDataExtractingTask task : tasks) {
								if (task.isComplete()) {
									if (!task.isApiAvailable()) {
										failCount++;
										logger.debug("通过api下载失败次数{}", failCount);
									}
									tasks.remove(task);
									tasks.add(readyTask);
									pool.execute(readyTask);
									isTaskAdded = true;
									logger.info("服务器[{}]运行完成, 服务器[{}]添加到TaskRunner准备运行", task.getRealmName(), realmNames.get(i));
									break;
								}
							}
							if (isTaskAdded) {
								break;
							} else {
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						} else {
							tasks.add(readyTask);
							pool.execute(readyTask);
							logger.info("服务器[{}]添加到TaskRunner准备运行", realmNames.get(i) );
							break;
						}
					}
				}
				pool.shutdown();
				while(true) {
					if (!pool.isTerminated()) {
						logger.info("有任务线程没有结束，等待{}", TimeUtil.format(CHECK_WAIT_TIME ));
						try {
							Thread.sleep(CHECK_WAIT_TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						logger.info("所有服务器运行完毕，用时{}", TimeUtil.format(System.currentTimeMillis() - startTime));
						if (isShutdown()) {
							exit();
						} else {
							try {
								logger.info("等待{}，准备重启", TimeUtil.format(WAIT_TIME));
								Thread.sleep(WAIT_TIME);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							logger.info("重新启动TaskRunner");
							break;
						}
					}
				}
			}
		} else {
			logger.info("发现shutdown文件, TaskRunner不运行");
		}		
	}
	
	public static void main(String[] args) {		
		new TaskRunner().launch();
	}
}
