package com.bnade.wow.vo;

import java.util.List;

public class ItemCreatedByVo {
	private int spellId;
	private String name;
	private String icon;
	private List<ItemReagentVo> reagent;

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

	public List<ItemReagentVo> getReagent() {
		return reagent;
	}

	public void setReagent(List<ItemReagentVo> reagent) {
		this.reagent = reagent;
	}

}
