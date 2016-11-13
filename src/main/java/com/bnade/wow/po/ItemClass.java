package com.bnade.wow.po;

import java.util.List;

public class ItemClass {
	private int itemClass;
	private String name;
	private List<ItemSubclass> subclasses;

	public int getItemClass() {
		return itemClass;
	}

	public void setItemClass(int itemClass) {
		this.itemClass = itemClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ItemSubclass> getSubclasses() {
		return subclasses;
	}

	public void setSubclasses(List<ItemSubclass> subclasses) {
		this.subclasses = subclasses;
	}

}
