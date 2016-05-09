package com.bnade.wow.po;

public class Realm {
	private int id;
	private String name;
	private String type;
	private String url;
	private long lastModified;
	private int maxAucId;
	private int auctionQuantity;
	private int playerQuantity;
	private int itemQuantity;
	private long lastUpdateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public int getMaxAucId() {
		return maxAucId;
	}

	public void setMaxAucId(int maxAucId) {
		this.maxAucId = maxAucId;
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

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
