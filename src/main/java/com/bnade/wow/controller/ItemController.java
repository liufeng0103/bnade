package com.bnade.wow.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.HotItemDao;
import com.bnade.wow.dao.impl.HotItemDaoImpl;
import com.bnade.wow.po.HotItem;
import com.sun.jersey.api.view.Viewable;

@Path("/item")
public class ItemController {
	
	private HotItemDao hotItemDao;
	
	public ItemController() {
		hotItemDao = new HotItemDaoImpl();
	}

	@GET
	@Path("/hotSearch")
	public Viewable mail(@DefaultValue("0") @QueryParam("offset") int offset, @Context HttpServletRequest req) {
		int limit = 20;
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
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错，请联系管理员");
			return new Viewable("/message.jsp");
		}
	}
	
}
