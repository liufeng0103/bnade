package com.bnade.wow.po;

import java.util.Date;

import com.bnade.util.BnadeUtil;

public class HotItem {
	
	public static final int HOT_DAY = 1;
	public static final int HOT_WEEK = 2;
	public static final int HOT_MONTH = 3;
	
	private int itemId;
	private String name;
	private String icon;
	private long buy;
	private int queried;
	private long dateTime;
	
	public Date getDate() {
		return new Date(dateTime);
	}

	public String getPrice() {
		return BnadeUtil.getGold(buy);
	}
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getBuy() {
		return buy;
	}

	public void setBuy(long buy) {
		this.buy = buy;
	}

	@Override
	public String toString() {
		return "HotItem [itemId=" + itemId + ", name=" + name + ", icon="
				+ icon + ", buy=" + buy + ", queried=" + queried
				+ ", dateTime=" + dateTime + "]";
	}

}
