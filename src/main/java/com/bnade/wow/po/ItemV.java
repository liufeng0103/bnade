package com.bnade.wow.po;

public class ItemV {

	public static final int TYPE_ITEM = 1;
	public static final int TYPE_PET = 2;

	private int id;
	private String name;
	private String icon;
	private int itemLevel;
	private int type;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ItemV [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", itemLevel=" + itemLevel + ", type=" + type + "]";
	}

}
