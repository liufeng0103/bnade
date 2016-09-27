package com.bnade.wow.client.model;

public class PetStats {
	private int speciesId;
	private int breedId;
	private int petQualityId;
	private int level;
	private int health;
	private int power;
	private int speed;

	public int getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(int speciesId) {
		this.speciesId = speciesId;
	}

	public int getBreedId() {
		return breedId;
	}

	public void setBreedId(int breedId) {
		this.breedId = breedId;
	}

	public int getPetQualityId() {
		return petQualityId;
	}

	public void setPetQualityId(int petQualityId) {
		this.petQualityId = petQualityId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public String toString() {
		return "PetStats [speciesId=" + speciesId + ", breedId=" + breedId
				+ ", petQualityId=" + petQualityId + ", level=" + level
				+ ", health=" + health + ", power=" + power + ", speed="
				+ speed + "]";
	}	
	
}
