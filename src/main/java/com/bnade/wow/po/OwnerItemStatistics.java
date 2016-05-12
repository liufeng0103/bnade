package com.bnade.wow.po;

public class OwnerItemStatistics {

	public static final String CATEGERY_COUNT = "itemCategeryCount";
	public static final String QUANTITY = "quantity";
	public static final String WORTH = "worth";

	private String owner;
	private int itemCategeryCount;
	private int quantity;
	private long worth;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getItemCategeryCount() {
		return itemCategeryCount;
	}

	public void setItemCategeryCount(int itemCategeryCount) {
		this.itemCategeryCount = itemCategeryCount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getWorth() {
		return worth;
	}

	public void setWorth(long worth) {
		this.worth = worth;
	}

}
