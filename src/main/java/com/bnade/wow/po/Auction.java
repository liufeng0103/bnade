package com.bnade.wow.po;

import java.util.Date;

import com.bnade.utils.BnadeUtils;

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
	private int petSpeciesId;
	private int petLevel;
	private int petBreedId;
	private int context;
	private String bonusLists;	
	private long lastModifed;
	private int realmId;
	private Item itemObj;

	public String getUrlOwner() {
		return BnadeUtils.encodeURIComponent(owner);
	}
	
	public String getRealmName() {
		return BnadeUtils.getRealmNameById(realmId);
	}
	
	public String getBidGold() {
		return BnadeUtils.getGold(bid);
	}
	
	public String getUnitPrice() {
		return BnadeUtils.getGold(buyout/quantity);
	}
	
	public String getPrice() {
		return BnadeUtils.getGold(buyout);
	}
	
	public Date getUpdated() {
		return new Date(lastModifed);
	}
	
	public String getTimeLeftCN() {
		return BnadeUtils.getTimeLeft(timeLeft);
	}
	
	public int getRealmHot() {
		return BnadeUtils.getRealmHot(realmId);
	}
	
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

	public String getBonusLists() {
		return bonusLists;
	}

	public void setBonusLists(String bonusLists) {
		this.bonusLists = bonusLists;
	}

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public long getLastModifed() {
		return lastModifed;
	}

	public void setLastModifed(long lastModifed) {
		this.lastModifed = lastModifed;
	}

	public Item getItemObj() {
		return itemObj;
	}

	public void setItemObj(Item itemObj) {
		this.itemObj = itemObj;
	}

	@Override
	public String toString() {
		return "Auction [auc=" + auc + ", item=" + item + ", owner=" + owner
				+ ", ownerRealm=" + ownerRealm + ", bid=" + bid + ", buyout="
				+ buyout + ", quantity=" + quantity + ", timeLeft=" + timeLeft
				+ ", rand=" + rand + ", seed=" + seed + ", petSpeciesId="
				+ petSpeciesId + ", petLevel=" + petLevel + ", petBreedId="
				+ petBreedId + ", context=" + context + ", bonusLists="
				+ bonusLists + ", lastModifed=" + lastModifed + ", realmId="
				+ realmId + ", itemObj=" + itemObj + "]\n";
	}

}
