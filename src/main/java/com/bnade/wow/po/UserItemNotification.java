package com.bnade.wow.po;

public class UserItemNotification {
	private int userId;
	private int realmId;
	private int itemId;
	private String itemName;
	private int isInverted;
	private long price;
	private int emailNotification;

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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getIsInverted() {
		return isInverted;
	}

	public void setIsInverted(int isInverted) {
		this.isInverted = isInverted;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(int emailNotification) {
		this.emailNotification = emailNotification;
	}

	@Override
	public String toString() {
		return "UserItemNotification [userId=" + userId + ", realmId="
				+ realmId + ", itemId=" + itemId + ", itemName=" + itemName
				+ ", isInverted=" + isInverted + ", price=" + price
				+ ", emailNotification=" + emailNotification + "]";
	}

}
