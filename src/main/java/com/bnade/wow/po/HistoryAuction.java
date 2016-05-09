package com.bnade.wow.po;

public class HistoryAuction {
	private int item;
	private int petSpeciesId;
	private int petBreedId;
	private String bonusLists;
	// 平均价格
	private long buyout;
	// 平均数量
	private int quantity;
	// 时段
	private long lastModifed;
	// 时段该类物品数量，用于archive中计算平均
	private int count;

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getPetBreedId() {
		return petBreedId;
	}

	public void setPetBreedId(int petBreedId) {
		this.petBreedId = petBreedId;
	}

	public String getBonusLists() {
		return bonusLists;
	}

	public void setBonusLists(String bonusLists) {
		this.bonusLists = bonusLists;
	}

	public long getBuyout() {
		return buyout;
	}

	public void setBuyout(long buyout) {
		this.buyout = buyout;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getLastModifed() {
		return lastModifed;
	}

	public void setLastModifed(long lastModifed) {
		this.lastModifed = lastModifed;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
