package com.bnade.wow.catcher;

public class ItemBonus {
	private int itemId;	
	private String bonusList;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getBonusList() {
		return bonusList;
	}

	public void setBonusList(String bonusList) {
		this.bonusList = bonusList;
	}

	@Override
	public boolean equals(Object obj) {
		ItemBonus ib = (ItemBonus)obj;
		return this.itemId == ib.getItemId() && this.bonusList.equals(ib.getBonusList());
	}
}
