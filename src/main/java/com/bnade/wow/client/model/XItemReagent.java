package com.bnade.wow.client.model;

import javax.xml.bind.annotation.XmlAttribute;

public class XItemReagent {
	private int id;
	private String name;
	private int quality;
	private String icon;
	private int count;

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public int getQuality() {
		return quality;
	}

	@XmlAttribute
	public void setQuality(int quality) {
		this.quality = quality;
	}

	public String getIcon() {
		return icon;
	}

	@XmlAttribute
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getCount() {
		return count;
	}

	@XmlAttribute
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "XItemReagent [id=" + id + ", name=" + name + ", quality="
				+ quality + ", icon=" + icon + ", count=" + count + "]";
	}

}
