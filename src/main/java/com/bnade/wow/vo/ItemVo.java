package com.bnade.wow.vo;

import java.util.List;

public class ItemVo {
	private int id;
	private String name;
	private String icon;
	private int itemLevel;
	private List<String> bonusList;

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

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public List<String> getBonusList() {
		return bonusList;
	}

	public void setBonusList(List<String> bonusList) {
		this.bonusList = bonusList;
	}

}
