package com.bnade.wow.client.model;

public class ItemBonusStat {
	private int stat;
	private int amount;

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ItemBonusStat [stat=" + stat + ", amount=" + amount + "]";
	}

}
