package com.bnade.wow.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JAuction {
	private int auc;
	private int item;
	private String owner;
	private String ownerRealm;
	private long bid;
	private long buyout;
	private int quantity;
	private String timeLeft;
	private int rand;
	private long seed;
	private int petSpeciesId;
	private int petLevel;
	private int petBreedId;
	private int context;
	private List<Bonus> bonusLists;
	private String bonusList;

	public int getAuc() {
		return auc;
	}

	public void setAuc(int auc) {
		this.auc = auc;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerRealm() {
		return ownerRealm;
	}

	public void setOwnerRealm(String ownerRealm) {
		this.ownerRealm = ownerRealm;
	}

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public long getBuyout() {
		return buyout;
	}

	public void setBuyout(long buyout) {
		this.buyout = buyout;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public int getRand() {
		return rand;
	}

	public void setRand(int rand) {
		this.rand = rand;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public int getContext() {
		return context;
	}

	public void setContext(int context) {
		this.context = context;
	}

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getPetLevel() {
		return petLevel;
	}

	public void setPetLevel(int petLevel) {
		this.petLevel = petLevel;
	}

	public int getPetBreedId() {
		return petBreedId;
	}

	public void setPetBreedId(int petBreedId) {
		this.petBreedId = petBreedId;
	}

	public String getBonusLists() {
		if (bonusList == null) {
			StringBuffer sb = new StringBuffer();
			if (bonusLists != null) {
				Collections.sort(bonusLists);
				for (Bonus b : bonusLists) {
					if (Bonus.bonusIds.contains(b.getBonusListId())) {
						if (sb.length() > 0) {
							sb.append(",");
						}
						sb.append(b.getBonusListId());
					}
				}
			}
			bonusList = sb.toString();
		}		
		return bonusList;
	}

	public void setBonusLists(List<Bonus> bonusLists) {
		this.bonusLists = bonusLists;
	}

	@Override
	public String toString() {
		return String.format("%d %d context:%d bonusLists:%s", auc, item,
				context, getBonusLists());
	}
}

class Bonus implements Comparable<Bonus> {
	/*
	 * FB出品 567 725 史诗 566 710 英雄级别 565 带插槽 史诗 564 带插槽 英雄级别 563 带插槽 普通 562 战火 史诗
	 * 561 战火 英雄级别 560 战火 普通
	 * 
	 * 制造业 164 某种此属性 531 532 548 549 不知道干啥的 620 705 6阶 武器 619 690 5阶 武器 594 675
	 * 4阶 武器 559 660 3阶 武器 558 645 2阶 武器 525 630 1阶 武器
	 * 
	 * 529 530 531 532 533 534 535 536 537 538 不知道干啥的 618 715 6阶 护甲 617 700 5阶
	 * 护甲 593 685 4阶 护甲 527 670 3阶 护甲 526 655 2阶 护甲 525 640 1阶 护甲
	 */
	public static List<Integer> bonusIds = new ArrayList<Integer>();
	static {
		// 护甲
		bonusIds.add(525);
		bonusIds.add(526);
		bonusIds.add(527);
		bonusIds.add(593);
		bonusIds.add(617);
		bonusIds.add(618);
		// 武器
		bonusIds.add(558);
		bonusIds.add(559);
		bonusIds.add(594);
		bonusIds.add(619);
		bonusIds.add(620);
		// FB
		bonusIds.add(560);
		bonusIds.add(561);
		bonusIds.add(562);
		bonusIds.add(563);
		bonusIds.add(564);
		bonusIds.add(565);
		bonusIds.add(566);
		bonusIds.add(567);
		// 7.0 新物品
		bonusIds.add(1477);	//815
		bonusIds.add(1482);	//820
		bonusIds.add(1487);	//825
		bonusIds.add(1492);	//830
		bonusIds.add(1497);	//835
		bonusIds.add(1502);	//840
		bonusIds.add(1507);	//845
		bonusIds.add(1512);	//850
		bonusIds.add(1517);	//855
		bonusIds.add(1522);	//860
		bonusIds.add(1527);	//865
		bonusIds.add(1532);	//870
		bonusIds.add(1537);	//875
		bonusIds.add(1542);	//880
		bonusIds.add(1547);	//885
		bonusIds.add(1552);	//890
		bonusIds.add(1552);	//890
		bonusIds.add(1808);	//棱彩插槽
//		bonusIds.add(40);	//躲闪
//		bonusIds.add(41);	//吸血
//		bonusIds.add(42);	//速度
		bonusIds.add(3398);	//按等级缩放
		
		// 7.0制造业
		/*
		 * 1676 快刀之 245全能 614暴击
		 * 1677 快刀之 307全能 552暴击
		 * 1678 快刀之 368全能 491暴击
		 * 1679 快刀之 429全能 429暴击
		 * 1680 快刀之 491全能 368暴击
		 * 1681 快刀之 552全能 307暴击
		 * 1682 快刀之 614全能 245暴击
		 * 1683 无双之 614暴击 245精通
		 * 1684 无双之 552暴击 307精通
		 * 1685 无双之 491暴击 368精通
		 * 1686 无双之 429暴击 429精通
		 * 1687 无双之 368暴击 491精通
		 * 1688 无双之 307暴击 552精通
		 * 1689 无双之 245暴击 614精通
		 * 1690 燎火之 614暴击 245急速
		 * 1691 燎火之 552暴击 307急速
		 * 1692 燎火之 491暴击 368急速
		 * 1693 燎火之 429暴击 429急速
		 * 1694 燎火之 368暴击 491急速
		 * 1695 燎火之 307暴击 552急速
		 * 1696 燎火之 245暴击 614急速
		 * 1697 灼光之 245精通 614急速
		 * 1698 灼光之 307精通 552急速
		 * 1699 灼光之 368精通 491急速
		 * 1700 灼光之 429精通 429急速
		 * 1701 灼光之 491精通 368急速
		 * 1702 灼光之 552精通 307急速
		 * 1703 灼光之 614精通 245急速
		 * 1704 曙光之 245全能 614急速
		 * 1705 曙光之 307全能 552急速
		 * 1706 曙光之 368全能 491急速
		 * 1707 曙光之 429全能 429急速
		 * 1708 曙光之 491全能 368急速
		 * 1709 曙光之 552全能 307急速
		 * 1710 曙光之 614全能 245急速
		 * 1711 谐律之 245全能 614精通
		 * 1712 谐律之 307全能 552精通
		 * 1713 谐律之 368全能 491精通
		 * 1714 谐律之 429全能 429精通
		 * 1715 谐律之 491全能 368精通
		 * 1716 谐律之 552全能 307精通
		 * 1717 谐律之 614全能 245精通
		 * 1718 屠夫之 859爆击
		 * 1719 应变之 859全能
		 * 1720 焦躁之 859急速
		 * 1721 专擅之 859精通
		 */
		bonusIds.add(1676);
		bonusIds.add(1677);
		bonusIds.add(1678);
		bonusIds.add(1679);
		bonusIds.add(1680);
		bonusIds.add(1681);
		bonusIds.add(1682);
		bonusIds.add(1683);
		bonusIds.add(1684);
		bonusIds.add(1685);
		bonusIds.add(1686);
		bonusIds.add(1687);
		bonusIds.add(1688);
		bonusIds.add(1689);
		bonusIds.add(1690);
		bonusIds.add(1691);
		bonusIds.add(1692);
		bonusIds.add(1693);
		bonusIds.add(1694);
		bonusIds.add(1695);
		bonusIds.add(1696);
		bonusIds.add(1697);
		bonusIds.add(1698);
		bonusIds.add(1699);
		bonusIds.add(1700);
		bonusIds.add(1701);
		bonusIds.add(1702);
		bonusIds.add(1703);
		bonusIds.add(1704);
		bonusIds.add(1705);
		bonusIds.add(1706);
		bonusIds.add(1707);
		bonusIds.add(1708);
		bonusIds.add(1709);
		bonusIds.add(1710);
		bonusIds.add(1711);
		bonusIds.add(1712);
		bonusIds.add(1713);
		bonusIds.add(1714);
		bonusIds.add(1715);
		bonusIds.add(1716);
		bonusIds.add(1717);
		bonusIds.add(1718);
		bonusIds.add(1719);
		bonusIds.add(1720);
		bonusIds.add(1721);
	}

	private int bonusListId;

	public int getBonusListId() {
		return bonusListId;
	}

	public void setBonusListId(int bonusListId) {
		this.bonusListId = bonusListId;
	}

	@Override
	public String toString() {
		return "" + bonusListId;
	}

	@Override
	public int compareTo(Bonus bonus) {
		return this.getBonusListId() - bonus.getBonusListId();
	}
}
