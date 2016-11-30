package com.bnade.wow.client.model;

import java.util.Collections;
import java.util.List;

public class JAuction {
	private int auc;
	private int item;
	private String owner;
	private String ownerRealm;
	private long bid;
	private long buyout;
	private int quantity;
	private String timeLeft;
	private int rand;
	private long seed;
	private int petSpeciesId;
	private int petLevel;
	private int petBreedId;
	private int context;
	private List<Bonus> bonusLists;
	private String bonusList;

	public int getAuc() {
		return auc;
	}

	public void setAuc(int auc) {
		this.auc = auc;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerRealm() {
		return ownerRealm;
	}

	public void setOwnerRealm(String ownerRealm) {
		this.ownerRealm = ownerRealm;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public long getBuyout() {
		return buyout;
	}

	public void setBuyout(long buyout) {
		this.buyout = buyout;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getRand() {
		return rand;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

	public int getPetBreedId() {
		return petBreedId;
	}

	public void setPetBreedId(int petBreedId) {
		this.petBreedId = petBreedId;
	}

	public List<Bonus> getAllBonus() {
		return bonusLists;
	}
	
	public String getBonusLists() {
		if (bonusList == null) {
			StringBuffer sb = new StringBuffer();
			if (bonusLists != null) {
				Collections.sort(bonusLists);
				for (Bonus b : bonusLists) {
					if (Bonus.bonusIds.contains(b.getBonusListId())) {
						if (sb.length() > 0) {
							sb.append(",");
						}
						sb.append(b.getBonusListId());
					}
				}
			}
			bonusList = sb.toString();
		}		
		return bonusList;
	}

	public void setBonusLists(List<Bonus> bonusLists) {
		this.bonusLists = bonusLists;
	}

	@Override
	public String toString() {
		return String.format("%d %d context:%d bonusLists:%s", auc, item,
				context, getBonusLists());
	}
}
