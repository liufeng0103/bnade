package com.bnade.wow.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.bnade.util.DBUtil;
import com.bnade.wow.dao.PetDao;
import com.bnade.wow.po.Pet;
import com.bnade.wow.po.PetStats;

public class PetDaoImpl implements PetDao {
	
	private QueryRunner run;
	
	public PetDaoImpl() {
		run = new QueryRunner(DBUtil.getDataSource());
	}
	
	@Override
	public List<Pet> getPetsByName(String name) throws SQLException {
		return getPetsByName(name, false);
	}
	
	@Override
	public List<Pet> getPetsByName(String name, boolean isFuzzy) throws SQLException {
		String condition = "=?";
		if (isFuzzy) {
			condition = "like ?";
			name = "%" + name + "%";
		}
		return run.query("select id,name,icon from t_pet where name " + condition, new BeanListHandler<Pet>(Pet.class), name);
	}

	@Override
	public List<PetStats> getPetStatsById(int id) throws SQLException {		
		return run.query("select speciesId,breedId,petQualityId,level,health,power,speed from t_pet_stats where speciesId=?", new BeanListHandler<PetStats>(PetStats.class), id);
	}

	@Override
	public Pet getPetById(int id) throws SQLException {
		return run.query("select id,name,icon from t_pet where id=?", new BeanHandler<Pet>(Pet.class), id);
	}	

}
