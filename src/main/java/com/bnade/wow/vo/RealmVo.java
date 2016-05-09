package com.bnade.wow.vo;

public class RealmVo {
	private int id;
	private String type;
	private long lastModified;
	private int auctionQuantity;
	private int playerQuantity;
	private int itemQuantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public int getAuctionQuantity() {
		return auctionQuantity;
	}

	public void setAuctionQuantity(int auctionQuantity) {
		this.auctionQuantity = auctionQuantity;
	}

	public int getPlayerQuantity() {
		return playerQuantity;
	}

	public void setPlayerQuantity(int playerQuantity) {
		this.playerQuantity = playerQuantity;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

}
