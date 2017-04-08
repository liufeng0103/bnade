package com.bnade.wow.po;

import com.bnade.util.BnadeUtil;


public class UserRealm {

	private int userId;
	private int realmId;
	private String realmName;
	private long lastModified;
	private String updated;

	public UserRealm() {

	}

	public UserRealm(int userId, int realmId) {
		this.userId = userId;
		this.realmId = realmId;
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

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getRealmName() {
		return BnadeUtil.getRealmNameById(realmId);
	}

	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	public String getUpdated() {
		return (System.currentTimeMillis() - lastModified) / (1000 * 60) + "分钟";
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "UserRealm [userId=" + userId + ", realmId=" + realmId
				+ ", realmName=" + realmName + ", lastModified=" + lastModified
				+ ", updated=" + updated + "]";
	}
}
