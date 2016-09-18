package com.bnade.wow.rs;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.bnade.util.TimeUtil;
import com.bnade.wow.po.HotItem;
import com.bnade.wow.po.Item;
import com.bnade.wow.po.QueryItem;
import com.bnade.wow.service.HotItemService;
import com.bnade.wow.service.ItemRuleService;
import com.bnade.wow.service.ItemService;
import com.bnade.wow.service.impl.HotItemServiceImpl;
import com.bnade.wow.service.impl.ItemRuleServiceImpl;
import com.bnade.wow.service.impl.ItemServiceImpl;
import com.bnade.wow.vo.HotItemVo;
import com.bnade.wow.vo.ItemVo;

@Path("/item")
public class ItemResource {
	
	private static Logger logger = LoggerFactory.getLogger(ItemResource.class);
	
	private ItemService itemService;
	private HotItemService hotItemService;
	private ItemRuleService itemRuleService;
	
	public ItemResource() {
		itemService = new ItemServiceImpl();
		hotItemService = new HotItemServiceImpl();
		itemRuleService = new ItemRuleServiceImpl();
	}

	/*
	 * 物品名称查询物品
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemsByName(@PathParam("name")String name, @QueryParam("fuzzy") boolean isFuzzy, @Context HttpServletRequest request, @Context HttpServletResponse resp) {
		try {
			if (isFuzzy) {
				List<Item> items = itemService.getItemsByName(name, true, 0, -1);
				List<String> result = new ArrayList<>();
				for (Item item : items) {
					result.add(item.getName());
				}
				System.out.println(result);
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
				resp.setHeader("Access-Control-Allow-Origin", "*");
				return result;	
			}			
		} catch (SQLException e) {			
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	/*
	 * 返回包含term的所有物品名
	 */
	@GET
	@Path("/names")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemNamesByTerm(@QueryParam("term") String term, @Context HttpServletResponse resp) {
		int max_length = 10;
		try {			
			List<Item> items = itemService.getItemsByName(term, true, 0, max_length);
			List<String> result = new ArrayList<>();
			for (Item item : items) {
				result.add(item.getName());
			}
			resp.setHeader("Access-Control-Allow-Origin", "*");
			return result;			
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
	public Object getHotDailyItems(@Context HttpServletResponse resp) {
		int hotCount = 10;
		try {
			List<HotItemVo> items = new ArrayList<>();
			long monthStart = TimeUtil.parse(TimeUtil.getDate(-30)).getTime();
			List<HotItem> hotItems = hotItemService.getGroupItemIdAfterDatetime(monthStart, hotCount);
			copy(hotItems, items, HotItem.HOT_MONTH);
			long weekStart = TimeUtil.parse(TimeUtil.getDate(-7)).getTime();
			hotItems = hotItemService.getGroupItemIdAfterDatetime(weekStart, hotCount);
			copy(hotItems, items, HotItem.HOT_WEEK);
			long dayStart = TimeUtil.parse(TimeUtil.getDate(0)).getTime();
			hotItems = hotItemService.getGroupItemIdAfterDatetime(dayStart, hotCount);
			copy(hotItems, items, HotItem.HOT_DAY);		
			resp.setHeader("Access-Control-Allow-Origin", "*");
			return items;
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	/*
	 * 用户每天搜索的排行
	 */
	@GET
	@Path("/hotsearch")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getHotSearchItems(@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		try {
			return hotItemService.getHotSearchItems(offset, limit);
		} catch (SQLException e) {		 
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	/*
	 * 物品规则
	 */
	@GET
	@Path("/rules")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemRules() {		
		try {
			return itemRuleService.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	/*
	 * 满足规则的物品
	 */
	@GET
	@Path("/rule/matchs")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getItemRuleMatchs() {		
		try {
			return itemRuleService.getAllMatched();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	private void copy(List<HotItem> hotItems, List<HotItemVo> items, int type) throws SQLException {
		for (HotItem hotItem : hotItems) {
			HotItemVo item = new HotItemVo();
			item.setId(hotItem.getItemId());
			Item dbItem = itemService.getItemById(hotItem.getItemId());
			if (dbItem != null) {
				item.setName(dbItem.getName());	
			}
			item.setQueried(hotItem.getQueried());
			item.setType(type);
			items.add(item);
		}
	}
	
}