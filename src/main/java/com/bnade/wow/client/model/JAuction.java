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
		// 7.0 世界掉了装备
		bonusIds.add(1477); // 815
		bonusIds.add(1482); // 820
		bonusIds.add(1487); // 825
		bonusIds.add(1492); // 830
		bonusIds.add(1497); // 835
		bonusIds.add(1502); // 840
		bonusIds.add(1507); // 845
		bonusIds.add(1512); // 850
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
