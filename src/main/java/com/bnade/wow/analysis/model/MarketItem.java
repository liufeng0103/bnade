package com.bnade.wow.analysis.model;

public class MarketItem {
	private int itemId;
	private long buy;
	private int realmQuantity;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public long getBuy() {
		return buy;
	}

	public void setBuy(long buy) {
		this.buy = buy;
	}

	public int getRealmQuantity() {
		return realmQuantity;
	}

	public void setRealmQuantity(int realmQuantity) {
		this.realmQuantity = realmQuantity;
	}
}
