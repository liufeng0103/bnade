package com.bnade.wow.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

public class XItemCreatedBy {
	private int id;
	private String name;
	private String icon;
	private List<XItemReagent> reagent;

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

	public String getIcon() {
		return icon;
	}

	@XmlAttribute
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<XItemReagent> getReagent() {
		return reagent;
	}

	public void setReagent(List<XItemReagent> reagent) {
		this.reagent = reagent;
	}

	@Override
	public String toString() {
		return "XItemCreatedBy [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", reagent=" + reagent + "]";
	}

}
