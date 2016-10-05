package com.bnade.wow.client.model;

import java.util.List;

public class JItem {
	private int id;
	private String description;
	private String name;
	private String icon;
	private int itemClass;
	private int itemSubClass;
	private int inventoryType;
	private int itemLevel;
	private List<ItemSpell> itemSpells;
	private List<ItemBonusStat> bonusStats;

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

	public List<ItemSpell> getItemSpells() {
		return itemSpells;
	}

	public void setItemSpells(List<ItemSpell> itemSpells) {
		this.itemSpells = itemSpells;
	}

	public int getSpellId() {
		if (itemSpells.size() > 0) {
			return itemSpells.get(0).getSpellId();
		}
		return -1;
	}

	public List<ItemBonusStat> getBonusStats() {
		return bonusStats;
	}

	public void setBonusStats(List<ItemBonusStat> bonusStats) {
		this.bonusStats = bonusStats;
	}

	@Override
	public String toString() {
		return "JItem [id=" + id + ", description=" + description + ", name="
				+ name + ", icon=" + icon + ", itemClass=" + itemClass
				+ ", itemSubClass=" + itemSubClass + ", inventoryType="
				+ inventoryType + ", itemLevel=" + itemLevel + ", itemSpells="
				+ itemSpells + ", bonusStats=" + bonusStats + "]";
	}
	
}

class ItemSpell {
	private int spellId;

	public int getSpellId() {
		return spellId;
	}

	public void setSpellId(int spellId) {
		this.spellId = spellId;
	}
}