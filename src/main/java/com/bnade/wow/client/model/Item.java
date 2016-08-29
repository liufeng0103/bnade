package com.bnade.wow.client.model;

public class Item {
	private int id;
	private String name;
	private int level;
	private int quality;
	private int itemClass;
	private int subClass;
	private int inventorySlot;
	private String icon;

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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getItemClass() {
		return itemClass;
	}

	public void setItemClass(int itemClass) {
		this.itemClass = itemClass;
	}

	public int getSubClass() {
		return subClass;
	}

	public void setSubClass(int subClass) {
		this.subClass = subClass;
	}

	public int getInventorySlot() {
		return inventorySlot;
	}

	public void setInventorySlot(int inventorySlot) {
		this.inventorySlot = inventorySlot;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", level=" + level
				+ ", quality=" + quality + ", itemClass=" + itemClass
				+ ", subClass=" + subClass + ", inventorySlot=" + inventorySlot
				+ ", icon=" + icon + "]";
	}
	
}
