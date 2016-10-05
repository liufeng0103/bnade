package com.bnade.wow.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class XItemClass {
	private int id;
	private String itemClass;

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getItemClass() {
		return itemClass;
	}

	@XmlValue
	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	@Override
	public String toString() {
		return "XItemClass [id=" + id + ", itemClass=" + itemClass + "]";
	}

}
