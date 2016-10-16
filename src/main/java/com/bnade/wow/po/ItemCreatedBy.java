package com.bnade.wow.po;

import java.util.List;

public class ItemCreatedBy {
	private int spellId;
	private String name;
	private String icon;
	private List<ItemReagent> reagent;

	public int getSpellId() {
		return spellId;
	}

	public void setSpellId(int spellId) {
		this.spellId = spellId;
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

	public List<ItemReagent> getReagent() {
		return reagent;
	}

	public void setReagent(List<ItemReagent> reagent) {
		this.reagent = reagent;
	}

}
