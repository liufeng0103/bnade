package com.bnade.wow.client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/*
<?xml version="1.0" encoding="UTF-8"?>
<wowhead>
	<item id="123915">
		<name>
			<![CDATA[邪钢肩甲]]>
		</name>
		<level>815</level>
		<quality id="3">稀有</quality>
		<class id="4">
			<![CDATA[护甲]]>
		</class>
		<subclass id="4">
			<![CDATA[板甲]]>
		</subclass>
		<icon displayId="145096">inv_shoulder_plate_legionendgame_c_01</icon>
		<inventorySlot id="3">肩膀</inventorySlot>
		<htmlTooltip>
			<![CDATA[<table><tr><td><!--nstart--><b class="q3">邪钢肩甲</b><!--nend--><!--ndstart--><!--ndend--><span style="color: #ffd100"><br />物品等级：<!--ilvl-->815</span><br /><!--bo-->装备时绑定<table width="100%"><tr><td>肩部</td><th><!--scstart4:4--><span class="q1">板甲</span><!--scend--></th></tr></table><span><!--amr-->474护甲</span><br /><span><!--stat74-->+702 [力量 or 智力]</span><br /><span><!--stat7-->+1053 耐力</span><!--ebstats--><!--egstats--><!--e--><!--ps--><br />耐久: 85 / 85</td></tr></table><table><tr><td>需要等级 <!--rlvl-->110<div class="whtt-sellprice">售价: <span class="moneygold">30</span> <span class="moneysilver">6</span> <span class="moneycopper">22</span></div></td></tr></table>]]></htmlTooltip>
		<json>
			<![CDATA["appearances":{"6":[145096,""]},"armor":474,"bonustrees":[524,274,363],"classs":4,"displayid":145096,"flags2":8192,"id":123915,"level":815,"name":"5邪钢肩甲","reqlevel":110,"slot":3,"slotbak":3,"source":[1],"subclass":4]]>
		</json>
		<jsonEquip>
			<![CDATA["appearances":{"6":[145096,""]},"armor":474,"displayid":145096,"dura":85,"int":702,"reqlevel":110,"sellprice":300622,"slotbak":3,"sta":1053,"str":702,"strint":702]]>
		</jsonEquip>
		<createdBy>
			<spell id="182946" name="邪钢肩甲" icon="inv_shoulder_plate_legionendgame_c_01" minCount="1" maxCount="1">
				<reagent id="124461" name="邪钢锭" quality="1" icon="inv_blacksmithing_70_demonsteelbar" count="8" />
				<reagent id="124440" name="阿卡纳精华" quality="1" icon="inv_enchanting_70_arkhana" count="8" />
				<reagent id="124124" name="萨格拉斯之血" quality="3" icon="inv_blood-of-sargeras" count="3" />
			</spell>
			<spell id="182976" name="邪钢肩甲" icon="inv_shoulder_plate_legionendgame_c_01" minCount="1" maxCount="1">
				<reagent id="124461" name="邪钢锭" quality="1" icon="inv_blacksmithing_70_demonsteelbar" count="4" />
				<reagent id="124440" name="阿卡纳精华" quality="1" icon="inv_enchanting_70_arkhana" count="4" />
				<reagent id="124124" name="萨格拉斯之血" quality="3" icon="inv_blood-of-sargeras" count="2" />
			</spell>
			<spell id="182984" name="邪钢肩甲" icon="inv_shoulder_plate_legionendgame_c_01" minCount="1" maxCount="1">
				<reagent id="124461" name="邪钢锭" quality="1" icon="inv_blacksmithing_70_demonsteelbar" count="4" />
				<reagent id="124124" name="萨格拉斯之血" quality="3" icon="inv_blood-of-sargeras" count="2" />
			</spell>
		</createdBy>
		<link>http://cn.wowhead.com/item=123915</link>
	</item>
</wowhead>
 */
public class XItem {

	private int id;
	private String name;
	private int level;
	private XItemQuality quality;
	private XItemClass itemClass;
	private XItemSubClass subclass;
	private String icon;
	private XItemInventorySlot inventorySlot;
	private List<XItemCreatedBy> createdBy;

	public int getId() {
		return id;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public XItemQuality getQuality() {
		return quality;
	}

	public void setQuality(XItemQuality quality) {
		this.quality = quality;
	}

	public XItemClass getItemClass() {
		return itemClass;
	}

	@XmlElement(name = "class")
	public void setItemClass(XItemClass itemClass) {
		this.itemClass = itemClass;
	}

	public XItemSubClass getSubclass() {
		return subclass;
	}

	public void setSubclass(XItemSubClass subclass) {
		this.subclass = subclass;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public XItemInventorySlot getInventorySlot() {
		return inventorySlot;
	}

	public void setInventorySlot(XItemInventorySlot inventorySlot) {
		this.inventorySlot = inventorySlot;
	}

	public List<XItemCreatedBy> getCreatedBy() {
		return createdBy;
	}

	@XmlElementWrapper(name = "createdBy")
	@XmlElement(name = "spell")
	public void setCreatedBy(List<XItemCreatedBy> createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "XItem [id=" + id + ", name=" + name + ", level=" + level
				+ ", quality=" + quality + ", itemClass=" + itemClass
				+ ", subclass=" + subclass + ", icon=" + icon
				+ ", inventorySlot=" + inventorySlot + ", createdBy="
				+ createdBy + "]";
	}

}
