package com.bnade.wow.client;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.BnadeProperties;
import com.bnade.util.HttpClient;
import com.bnade.wow.client.model.AuctionDataFile;
import com.bnade.wow.client.model.AuctionDataFiles;
import com.bnade.wow.client.model.JAuction;
import com.bnade.wow.client.model.JAuctions;
import com.google.gson.Gson;

/**
 * 通过战网api获取数据
 * 
 * @author liufeng0103
 *
 */
public class WowClient {
	
	private static Logger logger = LoggerFactory.getLogger(WowClient.class);
		
	private static final String HOST = "https://api.battlenet.com.cn";
	private static final String AUCTION_DATA = "/wow/auction/data/";
	private static final String APIKEY = "?apikey=" + BnadeProperties.getApiKey();
	
	private HttpClient httpClient;
	private Gson gson;
	
	
	public WowClient() {
		httpClient = new HttpClient();
		httpClient.setGzipSupported(true);
		gson = new Gson();
	}

	/**
	 * 通过服务器名获取拍卖数据文件信息
	 * @param realmName
	 * @return
	 * @throws WowClientException
	 * @throws IOException
	 */
	public AuctionDataFile getAuctionDataFile(String realmName) throws WowClientException {		
		String url = HOST + AUCTION_DATA + realmName + APIKEY;
		String json = null;
		try {			
			httpClient.resetTryCount();
			 json = httpClient.reliableGet(url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new WowClientException();
		}		
		return gson.fromJson(json, AuctionDataFiles.class).getFiles().get(0);
	}
	
	/**
	 * 通过url下载拍卖数据
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public List<JAuction> getAuctionData(String url) throws IOException {
		httpClient.resetTryCount();
		String json = httpClient.reliableGet(url);		
		return gson.fromJson(json, JAuctions.class).getAuctions();
	}
}
