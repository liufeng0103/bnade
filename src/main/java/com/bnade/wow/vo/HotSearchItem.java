package com.bnade.wow.vo;

public class HotSearchItem {
	private int id;
	private String name;
	private String icon;
	private int queried;

	public HotSearchItem() {
		super();
	}

	public HotSearchItem(int id, String name, String icon, int queried) {
		super();
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.queried = queried;
	}

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

	public int getQueried() {
		return queried;
	}

	public void setQueried(int queried) {
		this.queried = queried;
	}

}
