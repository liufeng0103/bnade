package com.bnade.wow.po;

public class ItemRule {

	private int itemId;
	private long ltBuy;
	private long gtBuy;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
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

	@Override
	public String toString() {
		return "ItemRule [itemId=" + itemId + ", ltBuy=" + ltBuy + ", gtBuy="
				+ gtBuy + "]";
	}
	
}
