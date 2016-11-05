package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.po.Item;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.bnade.wow.vo.ItemVo;
import com.bnade.wow.vo.Result;

@Path("/items")
public class AppItemResource {
	
	private ItemService itemService;

	public AppItemResource() {
		itemService = new ItemServiceImpl();
	}

	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(@QueryParam("name") String name) {		
		int max_length = 10;
		try {			
			List<Item> items = itemService.getItemsByName(name, true, 0, max_length);
			List<ItemVo> result = new ArrayList<>();
			for (Item item : items) {				
				result.add(new ItemVo(item.getId(), item.getName()));
			}
			return Response.ok(result).build();			
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {			
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}
	
}