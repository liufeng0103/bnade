package com.bnade.wow.po;

public class UserItemNotification {
	private int userId;
	private int realmId;
	private int itemId;
	private int petSpeciesId;
	private int petBreedId;
	private String bonusList;
	private String itemName;
	private int isInverted;
	private long price;
	private int emailNotification;
	private String email;
	private long minBuyout;
	private PetStats petStats;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

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

	public int getIsInverted() {
		return isInverted;
	}

	public void setIsInverted(int isInverted) {
		this.isInverted = isInverted;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(int emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getMinBuyout() {
		return minBuyout;
	}

	public void setMinBuyout(long minBuyout) {
		this.minBuyout = minBuyout;
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

	public PetStats getPetStats() {
		return petStats;
	}

	public void setPetStats(PetStats petStats) {
		this.petStats = petStats;
	}

	@Override
	public String toString() {
		return "UserItemNotification [userId=" + userId + ", realmId="
				+ realmId + ", itemId=" + itemId + ", petSpeciesId="
				+ petSpeciesId + ", petBreedId=" + petBreedId + ", bonusList="
				+ bonusList + ", itemName=" + itemName + ", isInverted="
				+ isInverted + ", price=" + price + ", emailNotification="
				+ emailNotification + ", email=" + email + ", minBuyout="
				+ minBuyout + ", petStats=" + petStats + "]";
	}

}
