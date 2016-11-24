package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.AuctionItem;
import com.bnade.wow.vo.Result;

@Path("/items")
public class AppItemResource {

	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItems(@QueryParam("name") String name) {		
		int limit = 10;
		try {			
			ItemDao itemDao = new ItemDaoImpl();
			List<AuctionItem> result = itemDao.getItemsWithBonuslist(name, 0, limit);
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