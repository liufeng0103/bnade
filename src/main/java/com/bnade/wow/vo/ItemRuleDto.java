package com.bnade.wow.vo;

public class ItemRuleDto {
	private int itemId;
	private String name;
	private long ltBuy;
	private long gtBuy;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLtBuy() {
		return ltBuy;
	}

	public void setLtBuy(long ltBuy) {
		this.ltBuy = ltBuy;
	}

	public long getGtBuy() {
		return gtBuy;
	}

	public void setGtBuy(long gtBuy) {
		this.gtBuy = gtBuy;
	}

}
