package com.bnade.wow.po;

public class UserRealm {

	private int userId;
	private int realmId;
	private long lastModified;

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

	@Override
	public String toString() {
		return "UserRealm [userId=" + userId + ", realmId=" + realmId
				+ ", lastModified=" + lastModified + "]";
	}
}
