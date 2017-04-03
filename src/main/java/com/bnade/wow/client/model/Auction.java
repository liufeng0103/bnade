package com.bnade.wow.client.model;

import java.util.List;

public class Auction {
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
	private int context;
	private List<Modifier> modifiers;
	private int petSpeciesId;
	private int petBreedId;
	private int petLevel;
	private int petQualityId;
	private List<BonusList> bonusLists;

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

	public List<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getPetBreedId() {
		return petBreedId;
	}

	public void setPetBreedId(int petBreedId) {
		this.petBreedId = petBreedId;
	}

	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

	public int getPetQualityId() {
		return petQualityId;
	}

	public void setPetQualityId(int petQualityId) {
		this.petQualityId = petQualityId;
	}

	public List<BonusList> getBonusLists() {
		return bonusLists;
	}

	public void setBonusLists(List<BonusList> bonusLists) {
		this.bonusLists = bonusLists;
	}

	@Override
	public String toString() {
		return "AuctionData [auc=" + auc + ", item=" + item + ", owner="
				+ owner + ", ownerRealm=" + ownerRealm + ", bid=" + bid
				+ ", buyout=" + buyout + ", quantity=" + quantity
				+ ", timeLeft=" + timeLeft + ", rand=" + rand + ", seed="
				+ seed + ", context=" + context + ", modifiers=" + modifiers
				+ ", petSpeciesId=" + petSpeciesId + ", petBreedId="
				+ petBreedId + ", petLevel=" + petLevel + ", petQualityId="
				+ petQualityId + ", bonusLists=" + bonusLists + "]\n";
	}

}
