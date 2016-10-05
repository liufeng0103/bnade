package com.bnade.wow.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class XItemInventorySlot {
	private int id;
	private String inventorySlot;

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getInventorySlot() {
		return inventorySlot;
	}

	@XmlValue
	public void setInventorySlot(String inventorySlot) {
		this.inventorySlot = inventorySlot;
	}

	@Override
	public String toString() {
		return "XItemInventorySlot [id=" + id + ", inventorySlot="
				+ inventorySlot + "]";
	}

}
