package com.bnade.wow.catcher;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.bnade.util.DBUtil;
import com.bnade.util.HttpClient;
import com.bnade.wow.client.WowClient;
import com.bnade.wow.client.model.JItem;
import com.bnade.wow.po.Item;

/**
 * 获取并更新新的物品
 * @author liufeng0103
 *
 */
public class ItemCatcher {
	
	private QueryRunner run;
	private WowClient wowClient;
	private HttpClient httpClient;
	
	public ItemCatcher() {
		run = new QueryRunner(DBUtil.getDataSource());
		// 国服api暂时不好用 切换用us的
		wowClient = new WowClient(WowClient.REGION_TW);	
		httpClient = new HttpClient();
	}
	
	public void process() {
		System.out.println("开始更新物品信息");
		try {
			List<Long> ids1 = run.query("select distinct id from t_item", new ColumnListHandler<Long>());
			System.out.println("现有物品数: " + ids1.size());
			List<Long> ids2 = run.query("select distinct item from t_ah_min_buyout_data", new ColumnListHandler<Long>());
			System.out.println("最新拍卖物品数: " + ids2.size());
			ids2.removeAll(ids1);
			System.out.println("需要更新 " + ids2.size());
			for (int i = 0; i < ids2.size(); i++) {
				long itemId = ids2.get(i);
				if (itemId == 82800 || itemId == 154) {
					System.out.println("不更新宠物笼id " + itemId);
					continue;
				}
				try {
					JItem jItem = wowClient.getItem(new Long(itemId).intValue());
					Item item = new Item();
					item.setId(jItem.getId());
//					item.setDescription(jItem.getDescription());
					item.setDescription("");
//					int spellId = jItem.getSpellId();
//					if (spellId > 0) {
//						item.setName(getItemNameBySpellId(spellId));	
//					} else {						
//											
//					}		
					String name = getItemNameById2(new Long(itemId).intValue());
					item.setName(name);	
//					item.setName(jItem.getName());					
					item.setIcon(jItem.getIcon());
					item.setItemClass(jItem.getItemClass());
					item.setItemSubClass(jItem.getItemSubClass());
					item.setInventoryType(jItem.getInventoryType());
					item.setItemLevel(jItem.getItemLevel());
					
					run.update("insert into t_item (id,description,name,icon,itemClass,itemSubClass,inventoryType,itemLevel) values(?,?,?,?,?,?,?,?)", item.getId(), item.getDescription(), item.getName(),
							item.getIcon(), item.getItemClass(), item.getItemSubClass(), item.getInventoryType(), item.getItemLevel());
					System.out.println("保存物品:" + item);					
				} catch (Exception e1) {
					System.out.println("找不到物品ID "+ itemId);
					e1.printStackTrace();
				}
//				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("物品信息更新完毕");
	}
	
	public void refreshItems() {
		System.out.println("开始刷新mt_item表");
		try {
			run.update("truncate mt_item");
			System.out.println("清空mt_item表");
			run.update("insert into mt_item (id,name,icon,itemLevel,hot) select id,name,icon,itemLevel,hot from t_item");
			System.out.println("导入t_item数据到mt_item");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		System.out.println("刷新mt_item表完毕");
	}

	public static void main(String[] args) {
		ItemCatcher itemCatcher = new ItemCatcher();
		itemCatcher.process();
		itemCatcher.refreshItems();
//		try {
//			System.out.println(itemCatcher.getItemNameById2(124442));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	// 由于国服api不好用，通过spell去wowhead网站获取物品中文名称
	public String getItemNameBySpellId(int spell) throws Exception {
		String html = httpClient.reliableGet("http://cn.wowhead.com/spell=" + spell);
		int titleStart = html.indexOf("<title>");
		int titleEnd = html.indexOf("</title>");
		if (titleStart > 0 && titleEnd > 0) {
			String title = html.substring(titleStart + "<title>".length(), titleEnd);
			return title.split("—")[0];
		} else {
			throw new Exception("找不到title");
		}
	}
	
	// 由于国服api不好用，通过178数据库获取物品名称
	public String getItemNameById(int itemId) throws Exception {
		String html = httpClient.reliableGet("http://db.178.com/wow/cn/item/" + itemId + ".html");
		int titleStart = html.indexOf("{pageUrl:location.href,pageName:") + 1;
		int titleEnd = html.indexOf(",pageIcon:") - 1;
		if (titleStart > 0 && titleEnd > 0) {
			String title = html.substring(titleStart + "{pageUrl:location.href,pageName:".length(), titleEnd);
			return title;
		} else {
			throw new Exception("找不到物品名");
		}
	}
	
	// 由于国服api不好用，通过英雄榜获取物品名称
	public String getItemNameById2(int itemId) throws Exception {
		Document doc = Jsoup.connect("https://www.battlenet.com.cn/wow/zh/item/" + itemId + "/tooltip").get();
		Elements elements = doc.getElementsByTag("h3");
		return elements.get(0).html();
	}
}
