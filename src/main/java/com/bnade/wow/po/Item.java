package com.bnade.wow.po;

import java.util.List;

public class Item {
	private int id;
	private String description;
	private String name;
	private String icon;
	private int itemClass;
	private int itemSubClass;
	private int inventoryType;
	private int itemLevel;
	private List<String> bonusList;

	public List<String> getBonusList() {
		return bonusList;
	}

	public void setBonusList(List<String> bonusList) {
		this.bonusList = bonusList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getItemClass() {
		return itemClass;
	}

	public void setItemClass(int itemClass) {
		this.itemClass = itemClass;
	}

	public int getItemSubClass() {
		return itemSubClass;
	}

	public void setItemSubClass(int itemSubClass) {
		this.itemSubClass = itemSubClass;
	}

	public int getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(int inventoryType) {
		this.inventoryType = inventoryType;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

}
