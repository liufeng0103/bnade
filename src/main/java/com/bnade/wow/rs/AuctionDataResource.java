package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.util.TimeUtil;
import com.bnade.wow.po.Auction;
import com.bnade.wow.po.HistoryAuction;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.OwnerItemStatistics;
import com.bnade.wow.po.Pet;
import com.bnade.wow.po.Realm;
import com.bnade.wow.service.AuctionHouseDataService;
import com.bnade.wow.service.AuctionHouseMinBuyoutDailyDataService;
import com.bnade.wow.service.AuctionHouseMinBuyoutDataService;
import com.bnade.wow.service.AuctionHouseMinBuyoutHistoryDataService;
import com.bnade.wow.service.AuctionHouseOwnerItemService;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.PetService;
import com.bnade.wow.service.RealmService;
import com.bnade.wow.service.impl.AuctionHouseDataServiceImpl;
import com.bnade.wow.service.impl.AuctionHouseOwnerItemServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutDailyDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutDataServiceImpl;
import com.bnade.wow.service.impl.AuctionMinBuyoutHistoryDataServiceImpl;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.bnade.wow.service.impl.PetServiceImpl;
import com.bnade.wow.service.impl.RealmServiceImpl;
import com.bnade.wow.vo.RealmVo;

@Path("/auction")
public class AuctionDataResource {
	
	// 近期记录显示几天的数据
	private static final int PAST_DAYS = 2;
	// 分时段的历史记录显示几个月的数据
	private static final int PAST_MONTHS = 12;
	
	private PetService petService;
	private ItemService itemService;
	private RealmService realmService;
	private AuctionHouseDataService auctionDataService;
	private AuctionHouseOwnerItemService auctionHouseOwnerItemService;
	private AuctionHouseMinBuyoutDataService auctionMinBuyoutDataService;
	private AuctionHouseMinBuyoutDailyDataService auctionMinBuyoutDailyDataService;
	private AuctionHouseMinBuyoutHistoryDataService auctionMinBuyoutHistoryDataService;
	
	public AuctionDataResource() {
		petService = new PetServiceImpl();
		itemService = new ItemServiceImpl();
		realmService = new RealmServiceImpl();
		auctionDataService = new AuctionHouseDataServiceImpl();
		auctionHouseOwnerItemService = new AuctionHouseOwnerItemServiceImpl();
		auctionMinBuyoutDataService = new AuctionMinBuyoutDataServiceImpl();
		auctionMinBuyoutDailyDataService = new AuctionMinBuyoutDailyDataServiceImpl();
		auctionMinBuyoutHistoryDataService = new AuctionMinBuyoutHistoryDataServiceImpl();
	}
	
	/**
	 * 获取物品在所有服务器的最低价
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionsByItemId(@PathParam("id") int itemId, @QueryParam("bl") String bl, @Context HttpServletResponse resp) {		 
		try {
			List<Auction> aucs = auctionMinBuyoutDataService.getByItemIdAndBounsList(itemId, bl);
			Object[] result = new Object[aucs.size()];
			for (int i = 0; i < aucs.size(); i++) {
				Auction auc = aucs.get(i);
				Object[] item = new Object[6];
				item[0] = auc.getRealmId();
				item[1] = auc.getBuyout();
				item[2] = auc.getOwner();
				item[3] = auc.getQuantity();
				item[4] = auc.getLastModifed();
				item[5] = auc.getTimeLeft();
				result[i] = item;
			}
			resp.setHeader("Access-Control-Allow-Origin", "*");
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取宠物在所有服务器的最低价
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/pet/{petId}/breed/{breedId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPetAuctionsByPetId(@PathParam("petId") int petId, @PathParam("breedId") int breedId) {
		try {
			List<Auction> aucs = auctionMinBuyoutDataService.getPetsByIdAndBreed(petId, breedId);
			Object[] result = new Object[aucs.size()];
			for (int i = 0; i < aucs.size(); i++) {
				Auction auc = aucs.get(i);
				Object[] item = new Object[7];
				item[0] = auc.getRealmId();
				item[1] = auc.getBuyout();
				item[2] = auc.getOwner();
				item[3] = auc.getTimeLeft();
				item[4] = auc.getPetLevel();
				item[5] = auc.getQuantity();
				item[6] = auc.getLastModifed();			
				result[i] = item;
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取物品在服务器近期的价格
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/past/realm/{realmId}/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPastAuctionsByRealmAndItemId(@PathParam("realmId") int realmId, @PathParam("id") int itemId, @QueryParam("bl") String bl) {
		try {
			List<Auction> aucs = new ArrayList<>();
			for (int i = 0; i < PAST_DAYS; i++) {
				aucs.addAll(auctionMinBuyoutDailyDataService.get(itemId, bl, TimeUtil.getDate(-i),realmId));
			}			
			Object[] result = new Object[aucs.size()];
			for (int i = 0; i < aucs.size(); i++) {
				Auction auc = aucs.get(i);
				Object[] item = new Object[3];
				item[0] = auc.getBuyout();
				item[1] = auc.getQuantity();
				item[2] = auc.getLastModifed();
				result[i] = item;
			}			
			return result; 
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取物品在服务器的历史
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/history/realm/{realmId}/item/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getHistoryAuctionsByRealmAndItemId(@PathParam("realmId") int realmId, @PathParam("id") int itemId, @QueryParam("bl") String bl) {
		try {
			List<HistoryAuction> aucs = new ArrayList<>(); 
			for (int i = 0; i < PAST_MONTHS; i++) {
				aucs.addAll(auctionMinBuyoutHistoryDataService.get(itemId, bl, TimeUtil.getYearMonth(-i), realmId));
			}
			Object[] result = new Object[aucs.size()];
			for (int i = 0; i < aucs.size(); i++) {
				HistoryAuction auc = aucs.get(i);
				Object[] item = new Object[3];			
				item[0] = auc.getBuyout();
				item[1] = auc.getQuantity();
				item[2] = auc.getLastModifed();
				result[i] = item;
			}
			return result; 
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/*
	 * 获取某个服务器某个物品的所有拍卖
	 */
	@GET
	@Path("/realm/{realmId}/item/{itemId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionItemsByRealmAndItem(@PathParam("realmId") int realmId, @PathParam("itemId") int itemId, @QueryParam("bl") String bl, @Context HttpServletResponse resp) {
		try {
			List<Auction> aucs = auctionDataService.getByItemId(itemId, bl, realmId);
			Object[] result = new Object[aucs.size()];
			for (int i = 0; i < aucs.size(); i++) {
				Auction auc = aucs.get(i);					
				Object[] item = new Object[6];
				item[0] = auc.getOwner();
				item[1] = auc.getOwnerRealm();
				item[2] = auc.getBid();
				item[3] = auc.getBuyout();
				item[4] = auc.getQuantity();
				item[5] = auc.getTimeLeft();
				result[i] = item;			
			}
			resp.setHeader("Access-Control-Allow-Origin", "*");
			return result; 
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}	
	}
	
	/*
	 * 获取某个服务器玩家拍卖的所有东西
	 */
	@GET
	@Path("/realm/{realmId}/owner/{owner}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAuctionItemsByRealmAndOwner(@PathParam("realmId") int realmId, @PathParam("owner") String owner) {
		try {
			List<Auction> aucs = auctionDataService.getByOwner(owner, realmId);
			Object[] result = new Object[aucs.size()];
			for (int i = 0; i < aucs.size(); i++) {
				Auction auc = aucs.get(i);
				if (auc.getItem() == Pet.PET_ITEM_ID) {
					try {
						Pet pet = petService.getPetById(auc.getPetSpeciesId());
						Object[] item = new Object[8];
						if (pet != null) {
							item[0] = pet.getId();
							item[1] = pet.getName();
						}					
						item[2] = auc.getOwnerRealm();
						item[3] = auc.getBid();
						item[4] = auc.getBuyout();
						item[5] = auc.getQuantity();
						item[6] = auc.getTimeLeft();
						item[7] = auc.getLastModifed();
						result[i] = item;
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Item aucItem = itemService.getItemById(auc.getItem());
						Object[] item = new Object[8];
						item[0] = auc.getItem();
						if (aucItem != null) {
							item[1] = aucItem.getName();
						}					
						item[2] = auc.getOwnerRealm();
						item[3] = auc.getBid();
						item[4] = auc.getBuyout();
						item[5] = auc.getQuantity();
						item[6] = auc.getTimeLeft();
						item[7] = auc.getLastModifed();
						result[i] = item;
					} catch (SQLException e) {
						e.printStackTrace();
					}				
				}			
			}
			return result; 
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取物品在服务器的历史
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/realms/summary")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getRealmsSummary() {
		try {
			List<Realm> realms = realmService.getAll();
			List<RealmVo> result = new ArrayList<>();
			for (Realm realm : realms) {
				RealmVo realmVo = new RealmVo();
				realmVo.setId(realm.getId());
				realmVo.setType(realm.getType());
				realmVo.setAuctionQuantity(realm.getAuctionQuantity());
				realmVo.setPlayerQuantity(realm.getPlayerQuantity());
				realmVo.setItemQuantity(realm.getItemQuantity());
				realmVo.setLastModified(realm.getLastModified());				
				result.add(realmVo);
			}	
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取玩家拍卖物品种类最多的排行
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/realm/{realmId}/owner/item/category/top")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getOwnerItemTopCount(@PathParam("realmId") int realmId) {
		try {
			return auctionHouseOwnerItemService.getOwnerItemStatisticsByRealmId(realmId, OwnerItemStatistics.CATEGERY_COUNT, 50);			
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取玩家拍卖物品总价值最多的排行
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/realm/{realmId}/owner/item/worth/top")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getOwnerItemTopWorth(@PathParam("realmId") int realmId) {
		try {
			return auctionHouseOwnerItemService.getOwnerItemStatisticsByRealmId(realmId, OwnerItemStatistics.WORTH, 50);			
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/**
	 * 获取玩家拍卖物品总数量最多的排行
	 * @param itemId
	 * @param bl
	 * @return
	 */
	@GET
	@Path("/realm/{realmId}/owner/item/quantity/top")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getOwnerItemTopQuantity(@PathParam("realmId") int realmId) {
		try {
			return auctionHouseOwnerItemService.getOwnerItemStatisticsByRealmId(realmId, OwnerItemStatistics.QUANTITY, 50);			
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
}
