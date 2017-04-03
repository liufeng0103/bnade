package com.bnade.wow.catcher;

public class TSMAppData {
	private int realmId;
	private int itemId;
	private long buy;
	private long minBuyout;
	private long historical;
	private int quantity;

	public TSMAppData() {
	};

	public TSMAppData(int realmId, int itemId, long minBuyout, long historical) {
		super();
		this.realmId = realmId;
		this.itemId = itemId;
		this.minBuyout = minBuyout;
		this.historical = historical;
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

	public long getMinBuyout() {
		return minBuyout;
	}

	public void setMinBuyout(long minBuyout) {
		this.minBuyout = minBuyout;
	}

	public long getHistorical() {
		return historical;
	}

	public void setHistorical(long historical) {
		this.historical = historical;
	}

	public long getBuy() {
		return buy;
	}

	public void setBuy(long buy) {
		this.buy = buy;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
