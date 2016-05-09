package com.bnade.wow.po;

public class HotItem {
	
	public static final int PERIOD_DAY = 1;
	public static final int PERIOD_WEEK = 2;
	public static final int PERIOD_MONTH = 3;
	public static final int PERIOD_YEAR = 3;

	private int itemId;
	private int queried;
	private int period;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQueried() {
		return queried;
	}

	public void setQueried(int queried) {
		this.queried = queried;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
}
