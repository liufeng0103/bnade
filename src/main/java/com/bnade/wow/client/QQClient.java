package com.bnade.wow.client;

import java.io.IOException;

import com.bnade.utils.HttpClient;
import com.bnade.wow.client.model.QQUser;
import com.google.gson.Gson;

public class QQClient {
	
	private int appId;
	private String openId;
	private String accessToken;
	private Gson gson;
	private HttpClient client;
	
	public QQClient(int appId, String openId, String accessToken) {
		this.appId = appId;
		this.openId = openId;
		this.accessToken = accessToken;
		gson = new Gson();
		client = new HttpClient();
	}
	
	public QQUser getUserInfo() throws ClientException {
		String url = "https://graph.qq.com/user/get_simple_userinfo?" + getRequiredParam();
		try {
			String json = client.get(url);
			QQUser user = gson.fromJson(json, QQUser.class);
			if (user.getRet() != 0) {
				throw new ClientException(user.getMsg());
			}
			return user;
		} catch (IOException e) {
			throw new ClientException(e);
		}
	}

	private String getRequiredParam() {
		return "access_token="+accessToken+"&oauth_consumer_key="+appId+"&openid="+openId;
	}
}
