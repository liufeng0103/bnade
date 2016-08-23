package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.wow.po.ItemV;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.ItemServiceImpl;

@Path("/items")
public class AppItemResource {
	
	private static Logger logger = LoggerFactory.getLogger(AppItemResource.class);
	
	private ItemService itemService;

	
	public AppItemResource() {
		itemService = new ItemServiceImpl();
	}

	/**
	 * 获取物品(包括宠物)信息
	 * 
	 * @param name
	 * @param offset
	 * @param limit
	 * @return 
	 */
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItems(@QueryParam("name") String name, @QueryParam("offset") int offset,@QueryParam("limit") int limit) {		
		try {
			name = name == null ? "" : name;			
			List<ItemV> items = itemService.get(name, offset, limit);
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity("找不到").type(MediaType.TEXT_PLAIN).build();
		}
	}	
}