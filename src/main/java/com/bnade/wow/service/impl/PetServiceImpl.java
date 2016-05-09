package com.bnade.wow.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.bnade.wow.dao.PetDao;
import com.bnade.wow.dao.impl.PetDaoImpl;
import com.bnade.wow.po.Pet;
import com.bnade.wow.service.PetService;

public class PetServiceImpl implements PetService {

	private PetDao petDao;
	
	public PetServiceImpl() {
		petDao = new PetDaoImpl();
	}
	
	@Override
	public List<Pet> getPetsByName(String name) throws SQLException {	
		List<Pet> pets = petDao.getPetsByName(name);
		for (Pet pet : pets) {
			pet.setPetStatsList(petDao.getPetStatsById(pet.getId()));			
		}
		return pets;
	}
	
	@Override
	public List<Pet> getPetsByName(String name, boolean isFuzzy) throws SQLException {
		return petDao.getPetsByName(name, isFuzzy);
	}

	@Override
	public Pet getPetById(int id) throws SQLException {		
		return petDao.getPetById(id);
	}	

}
