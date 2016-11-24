package com.bnade.wow.po;

import com.bnade.util.BnadeUtil;

public class UserCharacter {
	private int userId;
	private int realmId;
	private String name;

	public UserCharacter(){}

	public UserCharacter(int userId, int realmId, String name) {
		super();
		this.userId = userId;
		this.realmId = realmId;
		this.name = name;
	}

	public String getRealmName() {
		return BnadeUtil.getRealmNameById(realmId);
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
