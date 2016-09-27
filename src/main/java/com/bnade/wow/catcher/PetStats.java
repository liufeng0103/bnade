package com.bnade.wow.catcher;

public class PetStats {
	private int speciesId;
	private int breedId;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + breedId;
		result = prime * result + speciesId;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PetStats other = (PetStats) obj;
		if (breedId != other.breedId)
			return false;
		if (speciesId != other.speciesId)
			return false;
		return true;
	}	
	
}
