package com.bnade.wow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.HotItemDao;
import com.bnade.wow.dao.ItemDao;
import com.bnade.wow.dao.impl.HotItemDaoImpl;
import com.bnade.wow.dao.impl.ItemDaoImpl;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.Item;
import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;

@Path("/item")
public class ItemController {
	
	private HotItemDao hotItemDao;
	private ItemDao itemDao;
	
	public ItemController() {
		itemDao = new ItemDaoImpl();
		hotItemDao = new HotItemDaoImpl();
	}

	@GET
	@Path("/hotSearch")
	public Viewable hotSearch(@DefaultValue("0") @QueryParam("offset") int offset, @Context HttpServletRequest req) {
		int limit = 10;
		try {
			List<HotItem> items = hotItemDao.getHotItems(TimeUtil.parse(TimeUtil.getDate(0)).getTime(), offset, limit);
			if (items.size() < limit) {
				req.setAttribute("isLast", 1);
			}
			req.setAttribute("items", items);
			req.setAttribute("offset", offset);
			req.setAttribute("limit", limit);
			return new Viewable("/itemHotSearch.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错，请联系管理员");
			return new Viewable("/message.jsp");
		}
	}
	
	@GET
	@Path("/searchHistory/{id}")
	public Viewable mail(@PathParam("id") int id, @Context HttpServletRequest req) {		
		try {
			List<HotItem> items = hotItemDao.getHotItemsById(id);
			if (items.size() > 0) {
				ItemDao itemDao = new ItemDaoImpl();
				Item item = itemDao.getItemById(id);
				req.setAttribute("item", item);
				Gson gson = new Gson();
				Object[][] hItems = new Object[items.size()][2];
				for (int i = 0; i < items.size(); i++) {
					HotItem hItem = items.get(i);
					hItems[i][0] = hItem.getDateTime();
					hItems[i][1] = hItem.getQueried();
				}
				req.setAttribute("data", gson.toJson(hItems));
			}			
			req.setAttribute("items", items);
			return new Viewable("/itemSearchHistory.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错，请联系管理员");
			return new Viewable("/message.jsp");
		}
	}
	
	@GET
	@Path("/search")
	public Viewable search(@QueryParam("name") String name,
			@QueryParam("itemClass") Integer itemClass,
			@QueryParam("subclass") Integer subclass,
			@Context HttpServletRequest req) {
		int limit = 100;
		try {
			req.setAttribute("searchName", name);
			req.setAttribute("items", itemDao.getItems(name, itemClass, subclass, 0, limit));
//			req.setAttribute("itemClasses", itemDao.getItemClasses());
			return new Viewable("/itemSearch.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错，请联系管理员");
			return new Viewable("/message.jsp");
		}
	}
}
