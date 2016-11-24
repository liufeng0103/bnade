package com.bnade.wow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.bnade.util.BnadeUtil;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.Auction2;
import com.bnade.wow.po.Item;
import com.bnade.wow.service.AuctionHouseMinBuyoutDataService;
import com.bnade.wow.service.AuctionService;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.AuctionMinBuyoutDataServiceImpl;
import com.bnade.wow.service.impl.AuctionServiceImpl;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.google.gson.Gson;
import com.sun.jersey.api.view.Viewable;

@Path("/auction")
public class AuctionController {
	
	private AuctionService auctionService;
	private ItemService itemService;
	
	public AuctionController() {
		itemService = new ItemServiceImpl();
		auctionService = new AuctionServiceImpl();
	}
	
	@GET
	@Path("/item/{id}")
	public Viewable item(@PathParam("id") int id, @Context HttpServletRequest req) {		
		AuctionHouseMinBuyoutDataService auctionHouseService = new AuctionMinBuyoutDataServiceImpl();
		try {
			Gson gson = new Gson();
			Item item = itemService.getItemById(id);
			if (item != null) {
				List<Auction> aucs = auctionHouseService.getByItemIdAndBounsList(id, null);
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
//						System.out.println(buyout);
						req.setAttribute("maxBuyout", buyout);
					}
					count++;
				}
				req.setAttribute("item", item);
				req.setAttribute("auctions", aucs);
				return new Viewable("/auctionItem.jsp");
			} else {
				req.setAttribute("title", "出错");
				req.setAttribute("message", "出错");
				return new Viewable("/message.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错");
			return new Viewable("/message.jsp");
		}
	}

	@GET
	@Path("/owner/{name}/{realmId}")
	public Viewable owner(@PathParam("name") String name, @PathParam("realmId") int realmId, @Context HttpServletRequest req) {
		if (name == null || "".equals(name) || realmId < 1 || realmId > 170) {
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错");
			return new Viewable("/message.jsp");
		} else {
			try {
				List<Auction> aucs = auctionService.getAuctionsByRealmOwner(realmId, name);
				req.setAttribute("owner", name);
				req.setAttribute("realmName", BnadeUtil.getRealmNameById(realmId));
				req.setAttribute("auctions", aucs);
				return new Viewable("/auctionOwner.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("title", "出错");
				req.setAttribute("message", "出错");
				return new Viewable("/message.jsp");
			}
		}
	}
	
	@GET
	@Path("/owneritems/{name}/{realmId}")
	public Viewable owneritems(@PathParam("name") String name, @PathParam("realmId") int realmId, @Context HttpServletRequest req) {
		if (name == null || "".equals(name) || realmId < 1 || realmId > 170) {
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错");
			return new Viewable("/message.jsp");
		} else {
			try {
				List<Auction2> aucs = auctionService.getAuctionsByRealmOwner2(realmId, name);
				req.setAttribute("owner", name);
				req.setAttribute("realmId", realmId);
				req.setAttribute("realmName", BnadeUtil.getRealmNameById(realmId));
				req.setAttribute("auctions", aucs);
				return new Viewable("/auctionOwnerItems.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("title", "出错");
				req.setAttribute("message", "出错");
				return new Viewable("/message.jsp");
			}
		}
	}
	
}
