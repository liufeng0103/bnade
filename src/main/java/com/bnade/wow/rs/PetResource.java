package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.po.Pet;
import com.bnade.wow.service.PetService;
import com.bnade.wow.service.impl.PetServiceImpl;

@Path("/pet")
public class PetResource {
	
	private PetService petService;
	
	public PetResource() {
		petService = new PetServiceImpl();	
	}
	
	/*
	 * 通过宠物名查询宠物信息
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPetsByName(@PathParam("name")String name, @QueryParam("fuzzy") boolean isFuzzy) {
		try {
			if (isFuzzy) {
				List<Pet> pets = petService.getPetsByName(name, true);
				List<String> result = new ArrayList<>();
				for (Pet pet : pets) {
					result.add(pet.getName());
				}
				return result;
			} else {
				return petService.getPetsByName(name);	
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
}
