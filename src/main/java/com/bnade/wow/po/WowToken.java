package com.bnade.wow.po;

import java.util.Date;

/**
 * 持久化对象，时光徽章信息
 * 
 * @author liufeng0103
 *
 */
public class WowToken {
	private int buy;
	private long updated;

	public int getBuy() {
		return buy;
	}

	public void setBuy(int buy) {
		this.buy = buy;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return String.format("WowToken[buy=%d, updated=%s]", buy, new Date(updated));
	}
}
