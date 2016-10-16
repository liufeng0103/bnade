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
		// 7.0 项链
		/*
		1742 快刀之 614  全能 1534暴击 
		1743 快刀之 736  全能 1411暴击 
		1744 快刀之 859  全能 1289暴击 
		1745 快刀之 982  全能 1166暴击 
		1746 快刀之 1289 全能 859 暴击 
		1747 快刀之 1411 全能 736 暴击 
		1748 快刀之 1534 全能 614 暴击 
		1749 无双之 1534 暴击 614 精通 
		1750 无双之 1411 暴击 736 精通 
		1751 无双之 1289 暴击 859 精通 
		1752 无双之 1166 暴击 982 精通 
		1753 无双之 859  暴击 1289精通 
		1754 无双之 736  暴击 1411精通 
		1755 无双之 614  暴击 1534精通 
		1756 燎火之 1534 暴击 614 急速 
		1757 燎火之 1351 暴击 798 急速 
		1758 燎火之 1289 暴击 859 急速 
		1759 燎火之 1166 暴击 982 急速 
		1760 燎火之 859  暴击 1289急速 
		1761 燎火之 736  暴击 1411急速 
		1762 燎火之 614  暴击 1534急速 
		1763 灼光之 614  精通 1534急速 
		1764 灼光之 736  精通 1411急速 
		1765 灼光之 859  精通 1289急速 
		1766 灼光之 982  精通 1166急速 
		1767 灼光之 1289 精通 859 急速 
		1768 灼光之 1411 精通 736 急速 
		1769 灼光之 1534 精通 614 急速 
		1770 曙光之 614  全能 1534急速 
		1771 曙光之 736  全能 1411急速 
		1772 曙光之 859  全能 1289急速 
		1773 曙光之 982  全能 1166急速 
		1774 曙光之 1289 全能 859 急速 
		1775 曙光之 1411 全能 736 急速 
		1776 曙光之 1534 全能 614 急速 
		1777 谐律之 614  全能 1534精通 
		1778 谐律之 736  全能 1411精通 
		1779 谐律之 859  全能 1289精通 
		1780 谐律之 982  全能 1166精通 
		1781 谐律之 1289 全能 859 精通 
		1782 谐律之 1411 全能 736 精通 
		1783 谐律之 1534 全能 614 精通 
		1784 屠夫之 2148 暴击 
		1785 应变之 2148 全能 
		1786 焦躁之 2148 急速 
		1787 焦躁之 2148 精通 
	 */
		bonusIds.add(1742);
		bonusIds.add(1743);
		bonusIds.add(1744);
		bonusIds.add(1745);
		bonusIds.add(1746);
		bonusIds.add(1747);
		bonusIds.add(1748);
		bonusIds.add(1749);
		bonusIds.add(1750);
		bonusIds.add(1751);
		bonusIds.add(1752);
		bonusIds.add(1753);
		bonusIds.add(1754);
		bonusIds.add(1755);
		bonusIds.add(1756);
		bonusIds.add(1757);
		bonusIds.add(1758);
		bonusIds.add(1759);
		bonusIds.add(1760);
		bonusIds.add(1761);
		bonusIds.add(1762);
		bonusIds.add(1763);
		bonusIds.add(1764);
		bonusIds.add(1765);
		bonusIds.add(1766);
		bonusIds.add(1767);
		bonusIds.add(1768);
		bonusIds.add(1769);
		bonusIds.add(1770);
		bonusIds.add(1771);
		bonusIds.add(1772);
		bonusIds.add(1773);
		bonusIds.add(1774);
		bonusIds.add(1775);
		bonusIds.add(1776);
		bonusIds.add(1777);
		bonusIds.add(1778);
		bonusIds.add(1779);
		bonusIds.add(1780);
		bonusIds.add(1781);
		bonusIds.add(1782);
		bonusIds.add(1783);
		bonusIds.add(1784);
		bonusIds.add(1785);
		bonusIds.add(1786);
		bonusIds.add(1787);
		// 7.0 披风
		
		/*
		3343 快刀之 460全能 229暴击 
		3344 快刀之 575全能 114暴击 
		3345 快刀之 690全能    
		3361 快刀之 229全能 460暴击 
		3362 快刀之 114全能 575暴击 
		3363 快刀之 690暴击    
		3346 无双之 229暴击 460精通 
		3347 无双之 114暴击 575精通 
		3348 无双之 690精通    
		3351 无双之 229精通 460暴击 
		3352 无双之 114精通 575暴击 
		3354 无双之 690暴击    
		3349 燎火之 460急速 229暴击 
		3350 燎火之 575急速 114暴击 
		3353 燎火之 690急速    
		3370 燎火之 229急速 460暴击 
		3371 燎火之 114急速 575暴击 
		3372 燎火之 690暴击    
		3355 灼光之 460急速 229精通 
		3356 灼光之 575急速 114精通 
		3357 灼光之 690急速    
		3373 灼光之 229急速 460精通 
		3374 灼光之 114急速 575精通 
		3375 灼光之 690精通    
		3358 谐律之 460全能 229精通 
		3359 谐律之 575全能 114精通 
		3360 谐律之 690全能    
		3367 谐律之 229全能 460精通 
		3368 谐律之 114全能 575精通 
		3369 谐律之 690精通    
		3364 曙光之 229全能 460急速 
		3365 曙光之 114全能 575急速 
		3366 曙光之 690急速    
		3376 曙光之 460全能 229急速 
		3377 曙光之 575全能 114急速 
		3378 曙光之 690全能 
		 */
		bonusIds.add(3343);
		bonusIds.add(3344);
		bonusIds.add(3345);
		bonusIds.add(3346);
		bonusIds.add(3347);
		bonusIds.add(3348);
		bonusIds.add(3349);
		bonusIds.add(3350);
		bonusIds.add(3351);
		bonusIds.add(3352);
		bonusIds.add(3353);
		bonusIds.add(3354);
		bonusIds.add(3355);
		bonusIds.add(3356);
		bonusIds.add(3357);
		bonusIds.add(3358);
		bonusIds.add(3359);
		bonusIds.add(3360);
		bonusIds.add(3361);
		bonusIds.add(3362);
		bonusIds.add(3363);
		bonusIds.add(3364);
		bonusIds.add(3365);
		bonusIds.add(3366);
		bonusIds.add(3367);
		bonusIds.add(3368);
		bonusIds.add(3369);
		bonusIds.add(3370);
		bonusIds.add(3371);
		bonusIds.add(3372);
		bonusIds.add(3373);
		bonusIds.add(3374);
		bonusIds.add(3375);
		bonusIds.add(3376);
		bonusIds.add(3377);
		bonusIds.add(3378);
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
