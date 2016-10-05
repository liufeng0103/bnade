package com.bnade.wow.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class XItemQuality {

	private int id;
	private String quality;

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getQuality() {
		return quality;
	}

	@XmlValue
	public void setQuality(String quality) {
		this.quality = quality;
	}

	@Override
	public String toString() {
		return "XItemQuality [id=" + id + ", quality=" + quality + "]";
	}

}
