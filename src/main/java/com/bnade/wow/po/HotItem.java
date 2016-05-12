package com.bnade.wow.po;

public class HotItem {
	
	public static final int HOT_DAY = 1;
	public static final int HOT_WEEK = 2;
	public static final int HOT_MONTH = 3;
	
	private int itemId;
	private int queried;
	private long dateTime;

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

	public long getDateTime() {
		return dateTime;
	}

	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}

}
