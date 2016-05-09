package com.bnade.wow.rs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.HttpClient;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.QueryItem;
import com.bnade.wow.service.HotItemService;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.HotItemServiceImpl;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.bnade.wow.vo.HotItemVo;
import com.bnade.wow.vo.ItemVo;

@Path("/item")
public class ItemResource {
	
	private static Logger logger = LoggerFactory.getLogger(ItemResource.class);
	
	private ItemService itemService;
	private HotItemService hotItemService;
	
	public ItemResource() {
		itemService = new ItemServiceImpl();
		hotItemService = new HotItemServiceImpl();
	}

	/*
	 * 物品名称查询物品
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemsByName(@PathParam("name")String name, @QueryParam("fuzzy") boolean isFuzzy, @Context HttpServletRequest request) {
		try {
			if (isFuzzy) {
				List<Item> items = itemService.getItemsByName(name, true);
				List<String> result = new ArrayList<>();
				for (Item item : items) {
					result.add(item.getName());
				}
				return result;
			} else {
				List<ItemVo> result = new ArrayList<>();
				List<Item> items = itemService.getItemsByName(name);			
				for (Item item : items) {
					ItemVo itemVo = new ItemVo();
					itemVo.setId(item.getId());
					itemVo.setName(item.getName());
					itemVo.setIcon(item.getIcon());
					itemVo.setItemLevel(item.getItemLevel());
					itemVo.setBonusList(item.getBonusList());
					result.add(itemVo);
				}	
				if (items.size() > 0) {
					try {
						QueryItem item = new QueryItem();
						item.setItemId(items.get(0).getId());
						item.setIp(request.getRemoteAddr());
						item.setCreatedAt(System.currentTimeMillis());
						hotItemService.add(item);	
					} catch (SQLException e) {
						// 忽略错误，认为该用户已经查询过该物品
						logger.debug("插入搜索物品出错{}", e.getMessage());
					}								
				}
				return result;	
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	/*
	 * 物品ID查询物品描述
	 */
	@GET
	@Path("/{id}")	
	public Response getItemById(@PathParam("id")int id,  @QueryParam("bl") String bl,  @QueryParam("tooltip") boolean tooltip) {
		try {
			if (tooltip) {
				String url = "https://www.battlenet.com.cn/wow/zh/item/" + id + "/tooltip";
				if(bl != null){
					url+="?u=529&bl=" + bl;
				}
				HttpClient client = new HttpClient();
				client.setGzipSupported(true);
				String itemHtml = client.get(url);				
				return Response.status(200).entity(itemHtml.replaceAll("href=\"[^\"]*\"", "href=\"\"")).type(MediaType.TEXT_PLAIN).build();
			} else {				
				return Response.status(200).entity(itemService.getItemById(id)).type(MediaType.APPLICATION_JSON).build();
			}					
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}		
	}
	
	/*
	 * 热门物品，根据用户搜索排行
	 */
	@GET
	@Path("/hot")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getHotDailyItems() {
		try {
			List<HotItem> hotItems = hotItemService.getHotItemsByPeriodSortByQueried(HotItem.PERIOD_DAY, 10);
			hotItems.addAll(hotItemService.getHotItemsByPeriodSortByQueried(HotItem.PERIOD_WEEK, 10));
			hotItems.addAll(hotItemService.getHotItemsByPeriodSortByQueried(HotItem.PERIOD_MONTH, 10));
			List<HotItemVo> items = new ArrayList<>();
			for (HotItem hotItem : hotItems) {
				HotItemVo item = new HotItemVo();
				item.setId(hotItem.getItemId());
				Item dbItem = itemService.getItemById(hotItem.getItemId());
				if (dbItem != null) {
					item.setName(dbItem.getName());	
				}
				item.setQueried(hotItem.getQueried());
				item.setType(hotItem.getPeriod());
				items.add(item);
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
}
