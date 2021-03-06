package com.bnade.wow.catcher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.HttpClient;
import com.bnade.wow.po.WowToken;
import com.bnade.wow.service.WowTokenService;
import com.bnade.wow.service.impl.WowTokenServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 获取时光徽章价格信息并保存到数据库
 * 
 * @author liufeng0103
 *
 */
public class WowTokenExtractingTask {
	
	// 获取时光徽章信息的URL
	private static final String WOWTOKEN_URL = "https://data.wowtoken.info/snapshot.json";
	private static final String WOWTOKEN_HISTORY_URL = "https://data.wowtoken.info/wowtoken.json";
	
	private static Logger logger = LoggerFactory.getLogger(WowTokenExtractingTask.class);
	
	private HttpClient httpClient;
	private WowTokenService wowTokenService;
	private Gson gson;
	
	public WowTokenExtractingTask() {
		httpClient = new HttpClient();
		wowTokenService = new WowTokenServiceImpl();	
		gson = new Gson();
	}
	
	/**
	 * 初始化时光徽章数据
	 * 1. 清空时光徽章数据
	 * 2. 下载所有时光徽章数据并更新
	 */
	public void init() {
		Gson gs = new GsonBuilder()
	    .setPrettyPrinting()
	    .disableHtmlEscaping()
	    .create();
		logger.info("开始初始化时光徽章信息");
		try {
			logger.info("删除数据库所有时光徽章信息");
			wowTokenService.deleteAll();
			logger.info("时光徽章信息删除完毕");
			logger.info("开始下载所有时光徽章信息");
			httpClient.setGzipSupported(true);
			String tokensJson = httpClient.reliableGet(WOWTOKEN_HISTORY_URL, true);
			
//			int index = tokensJson.indexOf("history");
//			tokensJson = "{" + tokensJson.substring(index - 1);
			// 
			System.out.println(tokensJson);
			logger.info("下载完成");
			List<List<Long>> tokens = gs.fromJson(tokensJson, WowTokensHistoryJson.class).getHistory().getCN();
			if (tokens != null && tokens.size() != 0) {
				logger.info("获取到{}条时光徽章信息", tokens.size());
				List<WowToken> wowTokens = new ArrayList<>();
				int i = 0;
				for (List<Long> token : tokens) {
					WowToken wowToken = new WowToken();
					wowToken.setBuy(new Long(token.get(1)).intValue());
					wowToken.setUpdated(token.get(0) * 1000);
					wowTokens.add(wowToken);
					wowTokenService.save(wowToken);
					System.out.println(++i);
				}
				logger.info("开始保存{}条数据", wowTokens.size());
//				wowTokenService.save(wowTokens);
				logger.info("保存完毕");
			} else {
				logger.info("未获取到时光徽章数据");
			}			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		logger.info("初始化完毕");
	}
	
	public void process() {
		try {
			logger.info("开始获取时光徽章信息");
			String tokenJson = httpClient.reliableGet(WOWTOKEN_URL, true);
			logger.info("时光徽章信息获取成功");			
			WowToken wowToken = gson.fromJson(tokenJson, WowTokensJson.class).getCN().getRaw();
			// 通过url获取的时间是秒单位的需要转换成毫秒
			wowToken.setUpdated(wowToken.getUpdated() * 1000);
			logger.info("从数据库获取更新时间{}的时光徽章", new Date(wowToken.getUpdated()));
			WowToken dbWowToken = wowTokenService.getByUpdated(wowToken.getUpdated());
			if (dbWowToken == null && wowToken.getBuy() != 0) {
				logger.info("时光徽章不在数据库，添加信息{}", wowToken);
				wowTokenService.save(wowToken);
			} else {
				logger.info("时光徽章信息{}已存在，不更新", dbWowToken);
			}			
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 减少wowtoken数量， 删除偶数间隔数据
	 */
	public void reduceWowTokens() {
		try {
			List<WowToken> list = wowTokenService.getAll();
			System.out.println("共" + list.size() + "条记录");
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				if (i % 2 == 0) {
					WowToken wowToken = list.get(i);
					count++;
					wowTokenService.deleteByUpdated(wowToken.getUpdated());
					System.out.println("清除updated " + wowToken.getUpdated());
				}
			}
			System.out.println("共清除" + count + "条记录");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
//		new WowTokenExtractingTask().reduceWowTokens();
//		new WowTokenExtractingTask().init();
		new WowTokenExtractingTask().process();
	}

}

class WowTokensJson {
	private WowTokenJson CN;

	public WowTokenJson getCN() {
		return CN;
	}

	public void setCN(WowTokenJson cN) {
		CN = cN;
	}
	
}

class WowTokenJson {
	private WowToken raw;

	public WowToken getRaw() {
		return raw;
	}

	public void setRaw(WowToken raw) {
		this.raw = raw;
	}

}

class WowTokensHistoryJson {
	private WowTokensCnHistoryJson history;

	public WowTokensCnHistoryJson getHistory() {
		return history;
	}

	public void setHistory(WowTokensCnHistoryJson history) {
		this.history = history;
	}

}

class WowTokensCnHistoryJson {
	private List<List<Long>> CN;

	public List<List<Long>> getCN() {
		return CN;
	}

	public void setCN(List<List<Long>> cN) {
		CN = cN;
	}

}