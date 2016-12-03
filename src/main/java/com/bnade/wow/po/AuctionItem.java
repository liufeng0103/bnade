package com.bnade.wow.po;

import java.util.List;

public class AuctionItem {
	private int id;
	private String name;
	private String icon;
	private int itemClass;
	private int itemSubClass;
	private int inventoryType;
	private int itemLevel;
	private int petSpeciesId;
	private int hot;
	private List<String> bonusList;
	private List<PetStats> petStatsList;

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

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public List<String> getBonusList() {
		return bonusList;
	}

	public void setBonusList(List<String> bonusList) {
		this.bonusList = bonusList;
	}

	public List<PetStats> getPetStatsList() {
		return petStatsList;
	}

	public void setPetStatsList(List<PetStats> petStatsList) {
		this.petStatsList = petStatsList;
	}

	@Override
	public String toString() {
		return "AuctionItem [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", itemClass=" + itemClass + ", itemSubClass=" + itemSubClass
				+ ", inventoryType=" + inventoryType + ", itemLevel="
				+ itemLevel + ", petSpeciesId=" + petSpeciesId + ", hot=" + hot
				+ ", bonusList=" + bonusList + ", petStatsList=" + petStatsList
				+ "]";
	}

}
