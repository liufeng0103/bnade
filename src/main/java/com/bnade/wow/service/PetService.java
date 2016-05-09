package com.bnade.wow.service;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.po.Pet;

public interface PetService {

	List<Pet> getPetsByName(String name) throws SQLException;
	
	List<Pet> getPetsByName(String name, boolean isFuzzy) throws SQLException;
	
	Pet getPetById(int id) throws SQLException;
	
}
