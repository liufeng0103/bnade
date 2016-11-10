package com.bnade.wow.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.bnade.util.BnadeUtil;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Item;
import com.bnade.wow.service.AuctionHouseMinBuyoutDataService;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.AuctionMinBuyoutDataServiceImpl;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;

@Path("/auction")
public class AuctionController {
	
	private ItemService itemService;
	
	public AuctionController() {
		itemService = new ItemServiceImpl();
	}
	
	@GET
	@Path("/item/{id}")
	public Viewable item(@PathParam("id") int id, @Context HttpServletRequest req) {		
		AuctionHouseMinBuyoutDataService auctionService = new AuctionMinBuyoutDataServiceImpl();
		try {
			Gson gson = new Gson();
			Item item = itemService.getItemById(id);
			if (item != null) {
				List<Auction> aucs = auctionService.getByItemIdAndBounsList(id, null);
				List<Float> price = new ArrayList<>();
				List<Integer> quantities = new ArrayList<>();
				List<String> labels = new ArrayList<>();
				int max80 = aucs.size() * 8 / 10;
				int count = 0;
				for (Auction auc : aucs) {
					float buyout = Float.valueOf(BnadeUtil.getGold(auc.getBuyout()));
					price.add(buyout);
					req.setAttribute("price", gson.toJson(price));
					quantities.add(auc.getQuantity());
					req.setAttribute("quantities", gson.toJson(quantities));
					labels.add(BnadeUtil.getRealmNameById(auc.getRealmId()));
					req.setAttribute("labels", gson.toJson(labels));
					if (count == max80) {
						System.out.println(buyout);
						req.setAttribute("maxBuyout", buyout);
					}
					count++;
				}
				req.setAttribute("item", item);
				req.setAttribute("auctions", aucs);
				return new Viewable("/itemAuction.jsp");
			} else {
				req.setAttribute("title", "出错");
				req.setAttribute("message", "出错");
				return new Viewable("/message.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错");
			return new Viewable("/message.jsp");
		}
	}

}
