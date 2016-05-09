package com.bnade.wow.dao;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Pet;
import com.bnade.wow.po.PetStats;

public interface PetDao {

	List<Pet> getPetsByName(String name) throws SQLException;
	
	List<Pet> getPetsByName(String name, boolean isFuzzy) throws SQLException;
	
	Pet getPetById(int id) throws SQLException;
	
	List<PetStats> getPetStatsById(int id) throws SQLException;
	
}
