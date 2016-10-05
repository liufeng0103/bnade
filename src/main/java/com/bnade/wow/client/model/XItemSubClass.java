package com.bnade.wow.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class XItemSubClass {
	private int id;
	private String subclass;

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getSubclass() {
		return subclass;
	}

	@XmlValue
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	@Override
	public String toString() {
		return "XItemSubClass [id=" + id + ", subclass=" + subclass + "]";
	}

}
