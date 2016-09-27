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
import com.bnade.wow.client.model.JItem;
import com.bnade.wow.client.model.PetStats;
import com.google.gson.Gson;

/**
 * 通过战网api获取数据
 * 
 * @author liufeng0103
 *
 */
public class WowClient {
	
	public static final String REGION_US = "us";
	public static final String REGION_CN = "cn";
	public static final String REGION_TW = "tw";
	
	private static Logger logger = LoggerFactory.getLogger(WowClient.class);	
	private static final String HOST = "https://api.battlenet.com.cn";
	private static final String AUCTION_DATA = "/wow/auction/data/";
	private static final String ITEM = "/wow/item/";
	private static final String PET_STATS = "/wow/pet/stats/";
	private static final String APIKEY = "?apikey=" + BnadeProperties.getApiKey();
	
	private HttpClient httpClient;
	private Gson gson;
	
	private String region = "cn";

	public WowClient() {
		httpClient = new HttpClient();
		httpClient.setGzipSupported(true);
		gson = new Gson();
	}
	
	public WowClient(String region) {		
		this();
		this.region = region;
	}

	/**
	 * 通过服务器名获取拍卖数据文件信息
	 * @param realmName
	 * @return
	 * @throws WoWClientException
	 * @throws IOException
	 */
	public AuctionDataFile getAuctionDataFile(String realmName) throws WoWClientException {		
		// 晴日峰 (江苏)和丽丽需要转化空格
		realmName = realmName.replaceAll(" ", "%20");
		String url = getHost() + AUCTION_DATA + realmName + APIKEY;
		String json = null;
		try {			
			httpClient.resetTryCount();
			json = httpClient.reliableGet(url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new WoWClientException();
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
	
	/**
	 * 通过物品ID获取物品的信息
	 * @param id
	 * @return
	 * @throws WoWClientException 
	 */
	public JItem getItem(int id) throws WoWClientException {
		String url = getHost() + ITEM + id + APIKEY;
		String json = null;
		try {			
			httpClient.resetTryCount();
			json = httpClient.reliableGet(url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new WoWClientException();
		}		
		return gson.fromJson(json, JItem.class);
	}
	
	public PetStats getPetStats(int id, int breedId, int level, int qualityId) throws IOException {
		String url = getHost() +  PET_STATS + id + APIKEY + "&level=" + level + "&qualityId="+qualityId+"&breedId="+breedId;			
		String json = httpClient.reliableGet(url);
		return gson.fromJson(json, PetStats.class);		
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	private String getHost() {
		// default
		String host = HOST;
		if (!REGION_CN.equals(region)) {
			host = "https://" + region + ".api.battle.net";
		}
		return host;
	}
}
