package com.bnade.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bnade.wow.client.model.BonusList;

public class BnadeUtils {
	
	/**
	 * 把BonusLists转化成String，且只显示自己关心的bonus
	 * @param bonusLists
	 * @return
	 */
	public static String convertBonusListsToString(List<BonusList> bonusLists) {
		String result = null;
		if (bonusLists != null && bonusLists.size() > 0) {
			StringBuffer sb = new StringBuffer();
			Collections.sort(bonusLists);
			for (BonusList b : bonusLists) {
				if (BonusList.bonusIds.contains(b.getBonusListId())) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					sb.append(b.getBonusListId());
				}
			}
			result = sb.toString();
		}	
		return result;
	}
	
	public static String getRealmNameById(int id) {
		return realmMap.get(id);
	}
	
	public static String encodeURIComponent(String value) {
		try {
			return URLEncoder.encode(value, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
	
	public static String getGold(long price) {
		long gold = price / 10000;
		if (gold < 10) {
			return String.format("%.2f", price/10000.0);
		}
		return "" + gold;
	}
	
	public static String getBonus(String bonusList) {
		String bonusDesc = "";
		if (bonusList == null || "".equals(bonusList)) {
			return bonusDesc;
		} else {
			for (String bonus : bonusList.split(",")) {
				 String desc = bonusMap.get(Integer.valueOf(bonus));
				 if (desc == null) {
					 bonusDesc += "未知 ";
				 } else {
					 bonusDesc += desc + " ";
				 }				 
			}
		}
		return bonusDesc;
	}
	
	public static String getTimeLeft(String timeLeft) {
		String s = "";
		if ("LONG".equals(timeLeft)) {
			s = "长";
		} else if ("VERY_LONG".equals(timeLeft)) {
			s = "非常长";
		} else if ("MEDIUM".equals(timeLeft)) {
			s = "中";
		} else if ("SHORT".equals(timeLeft)) {
			s = "短";
		} 
		return s;
	}
	
	public static int getRealmHot(int realmId) {
		return realmHotMap.get(realmId);
	}
	
	public static void main(String[] args) {
		System.out.println("" + 641000l/10000.0);
	}
	
	private static Map<Integer, String> realmMap = new HashMap<>();
	private static Map<Integer, Integer> realmHotMap = new HashMap<>();
	public static Map<Integer, String> bonusMap = new HashMap<>();
	static {
		realmMap.put(1, "万色星辰-奥蕾莉亚-世界之树-布莱恩");
		realmMap.put(2, "丹莫德-克苏恩");
		realmMap.put(3, "主宰之剑-霍格");
		realmMap.put(4, "丽丽-四川");
		realmMap.put(5, "亚雷戈斯-银松森林");
		realmMap.put(6, "亡语者");
		realmMap.put(7, "伊兰尼库斯-阿克蒙德-恐怖图腾");
		realmMap.put(8, "伊利丹-尘风峡谷");
		realmMap.put(9, "伊森利恩");
		realmMap.put(10, "伊森德雷-达斯雷玛-库尔提拉斯-雷霆之怒");
		realmMap.put(11, "伊瑟拉-艾森娜-月神殿-轻风之语");
		realmMap.put(12, "伊莫塔尔-萨尔");
		realmMap.put(13, "伊萨里奥斯-祖阿曼");
		realmMap.put(14, "元素之力-菲米丝-夏维安");
		realmMap.put(15, "克尔苏加德");
		realmMap.put(16, "克洛玛古斯-金度");
		realmMap.put(17, "军团要塞-生态船");
		realmMap.put(18, "冬拥湖-迪托马斯-达基萨斯");
		realmMap.put(19, "冬泉谷-寒冰皇冠");
		realmMap.put(20, "冰川之拳-双子峰-埃苏雷格-凯尔萨斯");
		realmMap.put(21, "冰霜之刃-安格博达");
		realmMap.put(22, "冰风岗");
		realmMap.put(23, "凤凰之神-托塞德林");
		realmMap.put(24, "凯恩血蹄-瑟莱德丝-卡德加");
		realmMap.put(25, "利刃之拳-黑翼之巢");
		realmMap.put(26, "刺骨利刃-千针石林");
		realmMap.put(27, "加兹鲁维-奥金顿-哈兰");
		realmMap.put(28, "加基森-黑暗虚空");
		realmMap.put(29, "加尔-黑龙军团");
		realmMap.put(30, "加里索斯-库德兰");
		realmMap.put(31, "勇士岛-达文格尔-索拉丁");
		realmMap.put(32, "卡德罗斯-符文图腾-黑暗魅影-阿斯塔洛");
		realmMap.put(33, "卡扎克-爱斯特纳-戈古纳斯-巴纳扎尔");
		realmMap.put(34, "卡拉赞-苏塔恩");
		realmMap.put(35, "卡珊德拉-暗影之月");
		realmMap.put(36, "厄祖玛特-奎尔萨拉斯");
		realmMap.put(37, "古加尔-洛丹伦");
		realmMap.put(38, "古尔丹-血顶");
		realmMap.put(39, "古拉巴什-安戈洛-深渊之喉-德拉诺");
		realmMap.put(40, "古达克-梅尔加尼");
		realmMap.put(41, "哈卡-诺森德-燃烧军团-死亡熔炉");
		realmMap.put(42, "嚎风峡湾-闪电之刃");
		realmMap.put(43, "回音山-霜之哀伤-神圣之歌-遗忘海岸");
		realmMap.put(44, "国王之谷");
		realmMap.put(45, "图拉扬-海达希亚-瓦里玛萨斯-塞纳里奥");
		realmMap.put(46, "圣火神殿-桑德兰");
		realmMap.put(47, "地狱之石-火焰之树-耐奥祖");
		realmMap.put(48, "地狱咆哮-阿曼尼-奈法利安");
		realmMap.put(49, "埃克索图斯-血牙魔王");
		realmMap.put(50, "埃加洛尔-鲜血熔炉-斩魔者");
		realmMap.put(51, "埃基尔松");
		realmMap.put(52, "埃德萨拉");
		realmMap.put(53, "埃雷达尔-永恒之井");
		realmMap.put(54, "基尔加丹-奥拉基尔");
		realmMap.put(55, "基尔罗格-巫妖之王-迦顿");
		realmMap.put(56, "塔纳利斯-巴瑟拉斯-密林游侠");
		realmMap.put(57, "塞拉摩-暗影迷宫-麦姆");
		realmMap.put(58, "塞拉赞恩-太阳之井");
		realmMap.put(59, "塞泰克-罗曼斯-黑暗之矛");
		realmMap.put(60, "壁炉谷");
		realmMap.put(61, "外域-织亡者-阿格拉玛-屠魔山谷");
		realmMap.put(62, "大地之怒-恶魔之魂-希尔瓦娜斯");
		realmMap.put(63, "大漩涡-风暴之怒");
		realmMap.put(64, "天空之墙");
		realmMap.put(65, "天谴之门");
		realmMap.put(66, "夺灵者-战歌-奥斯里安");
		realmMap.put(67, "奈萨里奥-红龙女王-菲拉斯");
		realmMap.put(68, "奎尔丹纳斯-艾莫莉丝-布鲁塔卢斯");
		realmMap.put(69, "奥妮克希亚-海加尔-纳克萨玛斯");
		realmMap.put(70, "奥尔加隆");
		realmMap.put(71, "奥杜尔-普瑞斯托-逐日者");
		realmMap.put(72, "奥特兰克");
		realmMap.put(73, "奥达曼-甜水绿洲");
		realmMap.put(74, "守护之剑-瑞文戴尔");
		realmMap.put(75, "安东尼达斯");
		realmMap.put(76, "安其拉-弗塞雷迦-盖斯");
		realmMap.put(77, "安加萨-莱索恩");
		realmMap.put(78, "安威玛尔-扎拉赞恩");
		realmMap.put(79, "安纳塞隆-日落沼泽-风暴之鳞-耐普图隆");
		realmMap.put(80, "安苏");
		realmMap.put(81, "山丘之王-拉文霍德");
		realmMap.put(82, "巨龙之吼-黑石尖塔");
		realmMap.put(83, "巴尔古恩-托尔巴拉德");
		realmMap.put(84, "布兰卡德");
		realmMap.put(85, "布莱克摩-灰谷");
		realmMap.put(86, "希雷诺斯-芬里斯-烈焰荆棘");
		realmMap.put(87, "幽暗沼泽");
		realmMap.put(88, "影之哀伤");
		realmMap.put(89, "影牙要塞-艾苏恩");
		realmMap.put(90, "恶魔之翼-通灵学院");
		realmMap.put(91, "戈提克-雏龙之翼");
		realmMap.put(92, "拉文凯斯-迪瑟洛克");
		realmMap.put(93, "拉格纳洛斯-龙骨平原");
		realmMap.put(94, "拉贾克斯-荆棘谷");
		realmMap.put(95, "提尔之手-萨菲隆");
		realmMap.put(96, "提瑞斯法-暗影议会");
		realmMap.put(97, "摩摩尔-熵魔-暴风祭坛");
		realmMap.put(98, "斯坦索姆-穆戈尔-泰拉尔-格鲁尔");
		realmMap.put(99, "无尽之海-米奈希尔");
		realmMap.put(100, "无底海渊-阿努巴拉克-刀塔-诺莫瑞根");
		realmMap.put(101, "时光之穴");
		realmMap.put(102, "普罗德摩-铜龙军团");
		realmMap.put(103, "晴日峰-江苏");
		realmMap.put(104, "暗影裂口");
		realmMap.put(105, "暮色森林-杜隆坦-狂风峭壁-玛瑟里顿");
		realmMap.put(106, "月光林地-麦迪文");
		realmMap.put(107, "末日祷告祭坛-迦罗娜-纳沙塔尔-火羽山");
		realmMap.put(108, "末日行者");
		realmMap.put(109, "朵丹尼尔-蓝龙军团");
		realmMap.put(110, "格瑞姆巴托-埃霍恩");
		realmMap.put(111, "格雷迈恩-黑手军团-瓦丝琪");
		realmMap.put(112, "梦境之树-诺兹多姆-泰兰德");
		realmMap.put(113, "森金-沙怒-血羽");
		realmMap.put(114, "死亡之翼");
		realmMap.put(115, "毁灭之锤-兰娜瑟尔");
		realmMap.put(116, "永夜港-翡翠梦境-黄金之路");
		realmMap.put(117, "沃金");
		realmMap.put(118, "法拉希姆-玛法里奥-麦维影歌");
		realmMap.put(119, "洛肯-海克泰尔");
		realmMap.put(120, "洛萨-阿卡玛-萨格拉斯");
		realmMap.put(121, "深渊之巢");
		realmMap.put(122, "激流之傲-红云台地");
		realmMap.put(123, "激流堡-阿古斯");
		realmMap.put(124, "火喉-雷克萨");
		realmMap.put(125, "火烟之谷-玛诺洛斯-达纳斯");
		realmMap.put(126, "烈焰峰-瓦拉斯塔兹");
		realmMap.put(127, "熊猫酒仙");
		realmMap.put(128, "熔火之心-黑锋哨站");
		realmMap.put(129, "燃烧之刃");
		realmMap.put(130, "燃烧平原-风行者");
		realmMap.put(131, "狂热之刃");
		realmMap.put(132, "玛多兰-银月-羽月-耳语海岸");
		realmMap.put(133, "玛洛加尔");
		realmMap.put(134, "玛里苟斯-艾萨拉");
		realmMap.put(135, "瓦拉纳");
		realmMap.put(136, "白银之手");
		realmMap.put(137, "白骨荒野-能源舰");
		realmMap.put(138, "石爪峰-阿扎达斯");
		realmMap.put(139, "石锤-范达尔鹿盔");
		realmMap.put(140, "破碎岭-祖尔金");
		realmMap.put(141, "祖达克-阿尔萨斯");
		realmMap.put(142, "索瑞森-试炼之环");
		realmMap.put(143, "红龙军团");
		realmMap.put(144, "罗宁");
		realmMap.put(145, "自由之风-达隆米尔-艾欧纳尔-冬寒");
		realmMap.put(146, "艾维娜-艾露恩");
		realmMap.put(147, "范克里夫-血环");
		realmMap.put(148, "萨洛拉丝");
		realmMap.put(149, "藏宝海湾-阿拉希-塔伦米尔");
		realmMap.put(150, "蜘蛛王国");
		realmMap.put(151, "血吼-黑暗之门");
		realmMap.put(152, "血色十字军");
		realmMap.put(153, "贫瘠之地");
		realmMap.put(154, "踏梦者-阿比迪斯");
		realmMap.put(155, "辛达苟萨");
		realmMap.put(156, "达克萨隆-阿纳克洛斯");
		realmMap.put(157, "达尔坎-鹰巢山");
		realmMap.put(158, "迅捷微风");
		realmMap.put(159, "远古海滩");
		realmMap.put(160, "迦拉克隆");
		realmMap.put(161, "迦玛兰-霜狼");
		realmMap.put(162, "金色平原");
		realmMap.put(163, "阿拉索-阿迦玛甘");
		realmMap.put(164, "雷斧堡垒");
		realmMap.put(165, "雷霆之王");
		realmMap.put(166, "雷霆号角-风暴之眼");
		realmMap.put(167, "风暴峭壁");
		realmMap.put(168, "鬼雾峰");
		realmMap.put(169, "黑铁");
		realmMap.put(170, "斯克提斯");
		
		realmHotMap.put(80, 1);
		realmHotMap.put(3, 2);
		realmHotMap.put(110, 3);
		realmHotMap.put(114, 4);
		realmHotMap.put(88, 5);
		realmHotMap.put(136, 6);
		realmHotMap.put(108, 7);
		realmHotMap.put(23, 8);
		realmHotMap.put(43, 9);
		realmHotMap.put(99, 10);
		realmHotMap.put(44, 11);
		realmHotMap.put(119, 12);
		realmHotMap.put(127, 13);
		realmHotMap.put(129, 14);
		realmHotMap.put(153, 15);
		realmHotMap.put(144, 16);
		realmHotMap.put(9, 17);
		realmHotMap.put(22, 18);
		realmHotMap.put(152, 19);
		realmHotMap.put(52, 20);
		realmHotMap.put(15, 21);
		realmHotMap.put(158, 22);
		realmHotMap.put(84, 23);
		realmHotMap.put(57, 24);
		realmHotMap.put(72, 25);
		realmHotMap.put(160, 26);
		realmHotMap.put(140, 27);
		realmHotMap.put(123, 28);
		realmHotMap.put(35, 29);
		realmHotMap.put(169, 30);
		realmHotMap.put(131, 31);
		realmHotMap.put(53, 32);
		realmHotMap.put(156, 33);
		realmHotMap.put(21, 34);
		realmHotMap.put(168, 35);
		realmHotMap.put(112, 36);
		realmHotMap.put(4, 37);
		realmHotMap.put(93, 38);
		realmHotMap.put(28, 39);
		realmHotMap.put(11, 40);
		realmHotMap.put(128, 41);
		realmHotMap.put(164, 42);
		realmHotMap.put(147, 43);
		realmHotMap.put(5, 44);
		realmHotMap.put(143, 45);
		realmHotMap.put(162, 46);
		realmHotMap.put(60, 47);
		realmHotMap.put(8, 48);
		realmHotMap.put(62, 49);
		realmHotMap.put(70, 50);
		realmHotMap.put(25, 51);
		realmHotMap.put(34, 52);
		realmHotMap.put(165, 53);
		realmHotMap.put(46, 54);
		realmHotMap.put(106, 55);
		realmHotMap.put(151, 56);
		realmHotMap.put(55, 57);
		realmHotMap.put(130, 58);
		realmHotMap.put(41, 59);
		realmHotMap.put(1, 60);
		realmHotMap.put(19, 61);
		realmHotMap.put(29, 62);
		realmHotMap.put(167, 63);
		realmHotMap.put(96, 64);
		realmHotMap.put(78, 65);
		realmHotMap.put(126, 66);
		realmHotMap.put(85, 67);
		realmHotMap.put(64, 68);
		realmHotMap.put(120, 69);
		realmHotMap.put(125, 70);
		realmHotMap.put(150, 71);
		realmHotMap.put(67, 72);
		realmHotMap.put(7, 73);
		realmHotMap.put(82, 74);
		realmHotMap.put(122, 75);
		realmHotMap.put(141, 76);
		realmHotMap.put(69, 77);
		realmHotMap.put(12, 78);
		realmHotMap.put(32, 79);
		realmHotMap.put(161, 80);
		realmHotMap.put(170, 81);
		realmHotMap.put(37, 82);
		realmHotMap.put(109, 83);
		realmHotMap.put(137, 84);
		realmHotMap.put(66, 85);
		realmHotMap.put(74, 86);
		realmHotMap.put(79, 87);
		realmHotMap.put(42, 88);
		realmHotMap.put(47, 89);
		realmHotMap.put(134, 90);
		realmHotMap.put(50, 91);
		realmHotMap.put(94, 92);
		realmHotMap.put(163, 93);
		realmHotMap.put(58, 94);
		realmHotMap.put(98, 95);
		realmHotMap.put(116, 96);
		realmHotMap.put(38, 97);
		realmHotMap.put(76, 98);
		realmHotMap.put(48, 99);
		realmHotMap.put(92, 100);
		realmHotMap.put(56, 101);
		realmHotMap.put(24, 102);
		realmHotMap.put(81, 103);
		realmHotMap.put(146, 104);
		realmHotMap.put(87, 105);
		realmHotMap.put(132, 106);
		realmHotMap.put(61, 107);
		realmHotMap.put(13, 108);
		realmHotMap.put(97, 109);
		realmHotMap.put(40, 110);
		realmHotMap.put(138, 111);
		realmHotMap.put(142, 112);
		realmHotMap.put(73, 113);
		realmHotMap.put(63, 114);
		realmHotMap.put(14, 115);
		realmHotMap.put(118, 116);
		realmHotMap.put(49, 117);
		realmHotMap.put(26, 118);
		realmHotMap.put(100, 119);
		realmHotMap.put(2, 120);
		realmHotMap.put(39, 121);
		realmHotMap.put(95, 122);
		realmHotMap.put(10, 123);
		realmHotMap.put(86, 124);
		realmHotMap.put(54, 125);
		realmHotMap.put(121, 126);
		realmHotMap.put(149, 127);
		realmHotMap.put(16, 128);
		realmHotMap.put(45, 129);
		realmHotMap.put(90, 130);
		realmHotMap.put(31, 131);
		realmHotMap.put(89, 132);
		realmHotMap.put(133, 133);
		realmHotMap.put(102, 134);
		realmHotMap.put(68, 135);
		realmHotMap.put(17, 136);
		realmHotMap.put(105, 137);
		realmHotMap.put(30, 138);
		realmHotMap.put(103, 139);
		realmHotMap.put(33, 140);
		realmHotMap.put(157, 141);
		realmHotMap.put(91, 142);
		realmHotMap.put(159, 143);
		realmHotMap.put(113, 144);
		realmHotMap.put(166, 145);
		realmHotMap.put(139, 146);
		realmHotMap.put(154, 147);
		realmHotMap.put(107, 148);
		realmHotMap.put(111, 149);
		realmHotMap.put(124, 150);
		realmHotMap.put(20, 151);
		realmHotMap.put(145, 152);
		realmHotMap.put(27, 153);
		realmHotMap.put(77, 154);
		realmHotMap.put(59, 155);
		realmHotMap.put(18, 156);
		realmHotMap.put(71, 157);
		realmHotMap.put(83, 158);
		realmHotMap.put(6, 159);
		realmHotMap.put(115, 160);
		realmHotMap.put(36, 161);
		realmHotMap.put(65, 162);
		realmHotMap.put(101, 163);
		realmHotMap.put(75, 164);
		realmHotMap.put(104, 165);
		realmHotMap.put(148, 166);
		realmHotMap.put(155, 167);
		realmHotMap.put(135, 168);
		realmHotMap.put(117, 169);
		realmHotMap.put(51, 170);
		
		bonusMap.put(525  , "1阶");
		bonusMap.put(526  , "2阶");
		bonusMap.put(527  , "3阶");
		bonusMap.put(593  , "4阶");
		bonusMap.put(617  , "5阶");
		bonusMap.put(618  , "6阶");
		bonusMap.put(558  , "2阶");
		bonusMap.put(559  , "3阶");
		bonusMap.put(594  , "4阶");
		bonusMap.put(619  , "5阶");
		bonusMap.put(620  , "6阶");
		bonusMap.put(560  , "普通 战火");
		bonusMap.put(561  , "战火");
		bonusMap.put(562  , "战火");
		bonusMap.put(563  , "普通 带插槽");
		bonusMap.put(564  , "带插槽");
		bonusMap.put(565  , "带插槽");
		bonusMap.put(566  , "英雄级别");
		bonusMap.put(567  , "史诗");
		bonusMap.put(1457, "-15装等");
		bonusMap.put(1462, "-10装等");
		bonusMap.put(1467, "-5装等");
		bonusMap.put(1472, "+0装等");
		bonusMap.put(1477 , "+5装等");
		bonusMap.put(1482 , "+10装等");
		bonusMap.put(1487 , "+15装等");
		bonusMap.put(1492 , "+20装等");
		bonusMap.put(1497 , "+25装等");
		bonusMap.put(1502 , "+30装等");
		bonusMap.put(1507 , "+35装等");
		bonusMap.put(1512 , "+40装等");
		bonusMap.put(1517 , "+45装等");
		bonusMap.put(1522 , "+50装等");
		bonusMap.put(1527 , "+55装等");
		bonusMap.put(1532 , "+60装等");
		bonusMap.put(1537 , "+65装等");
		bonusMap.put(1542 , "+70装等");
		bonusMap.put(1547 , "+75装等");
		bonusMap.put(1552 , "+80装等");
		bonusMap.put(1612, "+140装等");
		bonusMap.put(1617, "+145装等");
		bonusMap.put(1622, "+150装等");
		bonusMap.put(1627, "+155装等");
		bonusMap.put(1632, "+160装等");
		bonusMap.put(1637, "+165装等");
		bonusMap.put(1808 , "棱彩插槽");
		bonusMap.put(40   , "躲闪");
		bonusMap.put(41   , "吸血");
		bonusMap.put(42   , "速度");
		bonusMap.put(3398 , "按等级缩放");
		bonusMap.put(1676 , "快刀之 245全能 614暴击");
		bonusMap.put(1677 , "快刀之 307全能 552暴击");
		bonusMap.put(1678 , "快刀之 368全能 491暴击");
		bonusMap.put(1679 , "快刀之 429全能 429暴击");
		bonusMap.put(1680 , "快刀之 491全能 368暴击");
		bonusMap.put(1681 , "快刀之 552全能 307暴击");
		bonusMap.put(1682 , "快刀之 614全能 245暴击");
		bonusMap.put(1683 , "无双之 614暴击 245精通");
		bonusMap.put(1684 , "无双之 552暴击 307精通");
		bonusMap.put(1685 , "无双之 491暴击 368精通");
		bonusMap.put(1686 , "无双之 429暴击 429精通");
		bonusMap.put(1687 , "无双之 368暴击 491精通");
		bonusMap.put(1688 , "无双之 307暴击 552精通");
		bonusMap.put(1689 , "无双之 245暴击 614精通");
		bonusMap.put(1690 , "燎火之 614暴击 245急速");
		bonusMap.put(1691 , "燎火之 552暴击 307急速");
		bonusMap.put(1692 , "燎火之 491暴击 368急速");
		bonusMap.put(1693 , "燎火之 429暴击 429急速");
		bonusMap.put(1694 , "燎火之 368暴击 491急速");
		bonusMap.put(1695 , "燎火之 307暴击 552急速");
		bonusMap.put(1696 , "燎火之 245暴击 614急速");
		bonusMap.put(1697 , "灼光之 245精通 614急速");
		bonusMap.put(1698 , "灼光之 307精通 552急速");
		bonusMap.put(1699 , "灼光之 368精通 491急速");
		bonusMap.put(1700 , "灼光之 429精通 429急速");
		bonusMap.put(1701 , "灼光之 491精通 368急速");
		bonusMap.put(1702 , "灼光之 552精通 307急速");
		bonusMap.put(1703 , "灼光之 614精通 245急速");
		bonusMap.put(1704 , "曙光之 245全能 614急速");
		bonusMap.put(1705 , "曙光之 307全能 552急速");
		bonusMap.put(1706 , "曙光之 368全能 491急速");
		bonusMap.put(1707 , "曙光之 429全能 429急速");
		bonusMap.put(1708 , "曙光之 491全能 368急速");
		bonusMap.put(1709 , "曙光之 552全能 307急速");
		bonusMap.put(1710 , "曙光之 614全能 245急速");
		bonusMap.put(1711 , "谐律之 245全能 614精通");
		bonusMap.put(1712 , "谐律之 307全能 552精通");
		bonusMap.put(1713 , "谐律之 368全能 491精通");
		bonusMap.put(1714 , "谐律之 429全能 429精通");
		bonusMap.put(1715 , "谐律之 491全能 368精通");
		bonusMap.put(1716 , "谐律之 552全能 307精通");
		bonusMap.put(1717 , "谐律之 614全能 245精通");
		bonusMap.put(1718 , "屠夫之 859爆击");
		bonusMap.put(1719 , "应变之 859全能");
		bonusMap.put(1720 , "焦躁之 859急速");
		bonusMap.put(1721 , "专擅之 859精通");
		bonusMap.put(1742 , "快刀之 460全能 1150暴击");
		bonusMap.put(1743 , "快刀之 552全能 1058暴击");
		bonusMap.put(1744 , "快刀之 644全能 966暴击");
		bonusMap.put(1745 , "快刀之 736全能 874暴击");
		bonusMap.put(1746 , "快刀之 966全能 644暴击");
		bonusMap.put(1747 , "快刀之 1058全能 552暴击");
		bonusMap.put(1748 , "快刀之 1150全能 460暴击");
		bonusMap.put(1749 , "无双之 1150暴击 460精通");
		bonusMap.put(1750 , "无双之 1058暴击 552精通");
		bonusMap.put(1751 , "无双之 966暴击 644精通");
		bonusMap.put(1752 , "无双之 874暴击 736精通");
		bonusMap.put(1753 , "无双之 644暴击 966精通");
		bonusMap.put(1754 , "无双之 552暴击 1058精通");
		bonusMap.put(1755 , "无双之 460暴击 1150精通");
		bonusMap.put(1756 , "燎火之 460急速 1150暴击");
		bonusMap.put(1757 , "燎火之 598急速 1012暴击");
		bonusMap.put(1758 , "燎火之 644急速 966暴击");
		bonusMap.put(1759 , "燎火之 736急速 874暴击");
		bonusMap.put(1760 , "燎火之 966急速 644暴击");
		bonusMap.put(1761 , "燎火之 1058急速 552暴击");
		bonusMap.put(1762 , "燎火之 1150急速 460暴击");
		bonusMap.put(1763 , "灼光之 1150精通 460急速");
		bonusMap.put(1764 , "灼光之 1058精通 552急速");
		bonusMap.put(1765 , "灼光之 966精通 644急速");
		bonusMap.put(1766 , "灼光之 874精通 736急速");
		bonusMap.put(1767 , "灼光之 644精通 966急速");
		bonusMap.put(1768 , "灼光之 552精通 1058急速");
		bonusMap.put(1769 , "灼光之 460精通 1150急速");
		bonusMap.put(1770 , "曙光之 460全能 1150急速");
		bonusMap.put(1771 , "曙光之 552全能 1058急速");
		bonusMap.put(1772 , "曙光之 644全能 966急速");
		bonusMap.put(1773 , "曙光之 736全能 874急速");
		bonusMap.put(1774 , "曙光之 966全能 644急速");
		bonusMap.put(1775 , "曙光之 1058全能 552急速");
		bonusMap.put(1776 , "曙光之 1150全能 460急速");
		bonusMap.put(1777 , "谐律之 460全能 1150精通");
		bonusMap.put(1778 , "谐律之 552全能 1058精通");
		bonusMap.put(1779 , "谐律之 644全能 966精通");
		bonusMap.put(1780 , "谐律之 736全能 874精通");
		bonusMap.put(1781 , "谐律之 966全能 644精通");
		bonusMap.put(1782 , "谐律之 1058全能 552精通");
		bonusMap.put(1783 , "谐律之 1150全能 460精通");
		bonusMap.put(1784 , "屠夫之 1611爆击");
		bonusMap.put(1785 , "应变之 1611全能");
		bonusMap.put(1786 , "焦躁之 1611急速");
		bonusMap.put(1787 , "专擅之 1611精通");
		bonusMap.put(3343 , "快刀之 460全能 229暴击");
		bonusMap.put(3344 , "快刀之 575全能 114暴击");
		bonusMap.put(3345 , "快刀之 690全能");
		bonusMap.put(3361 , "快刀之 229全能 460暴击");
		bonusMap.put(3362 , "快刀之 114全能 575暴击");
		bonusMap.put(3363 , "快刀之 690暴击");
		bonusMap.put(3346 , "无双之 229暴击 460精通");
		bonusMap.put(3347 , "无双之 114暴击 575精通");
		bonusMap.put(3348 , "无双之 690精通");
		bonusMap.put(3351 , "无双之 229精通 460暴击");
		bonusMap.put(3352 , "无双之 114精通 575暴击");
		bonusMap.put(3354 , "无双之 690暴击");
		bonusMap.put(3349 , "燎火之 460急速 229暴击");
		bonusMap.put(3350 , "燎火之 575急速 114暴击");
		bonusMap.put(3353 , "燎火之 690急速");
		bonusMap.put(3370 , "燎火之 229急速 460暴击");
		bonusMap.put(3371 , "燎火之 114急速 575暴击");
		bonusMap.put(3372 , "燎火之 690暴击");
		bonusMap.put(3355 , "灼光之 460急速 229精通");
		bonusMap.put(3356 , "灼光之 575急速 114精通");
		bonusMap.put(3357 , "灼光之 690急速");
		bonusMap.put(3373 , "灼光之 229急速 460精通");
		bonusMap.put(3374 , "灼光之 114急速 575精通");
		bonusMap.put(3375 , "灼光之 690精通");
		bonusMap.put(3358 , "谐律之 460全能 229精通");
		bonusMap.put(3359 , "谐律之 575全能 114精通");
		bonusMap.put(3360 , "谐律之 690全能");
		bonusMap.put(3367 , "谐律之 229全能 460精通");
		bonusMap.put(3368 , "谐律之 114全能 575精通");
		bonusMap.put(3369 , "谐律之 690精通");
		bonusMap.put(3364 , "曙光之 229全能 460急速");
		bonusMap.put(3365 , "曙光之 114全能 575急速");
		bonusMap.put(3366 , "曙光之 690急速");
		bonusMap.put(3376 , "曙光之 460全能 229急速");
		bonusMap.put(3377 , "曙光之 575全能 114急速");
		bonusMap.put(3378 , "曙光之 690全能");
		bonusMap.put(3406, "谐律之 全能 精通");
		bonusMap.put(3405, "曙光之 全能 急速");
		bonusMap.put(3404, "灼光之 精通 急速");
		bonusMap.put(3403, "燎火之 暴击 急速");
		bonusMap.put(3402, "无双之 暴击 精通");
		bonusMap.put(3401, "快刀之 全能 暴击"); 
		
	}
}
