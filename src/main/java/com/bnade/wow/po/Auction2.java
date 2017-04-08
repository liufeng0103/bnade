package com.bnade.wow.po;

import com.bnade.util.BnadeUtil;

/**
 * 用户玩家压价功能
 * 
 * @author liufeng0103
 *
 */
public class Auction2 extends Auction {
	private String name;
	private String icon;
	private String owner2;
	private long bid2;
	private long buyout2;
	private int quantity2;

	public String getBidGold() {
		return BnadeUtil.getGold(getBid());
	}

	public String getBuyoutGold() {
		return BnadeUtil.getGold(getBuyout());
	}

	public String getUnitBuyoutGold() {
		return BnadeUtil.getGold(getBuyout() / getQuantity());
	}

	public String getBonus() {
		return BnadeUtil.getBonus(getBonusLists());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOwner2() {
		return owner2;
	}

	public void setOwner2(String owner2) {
		this.owner2 = owner2;
	}

	public long getBid2() {
		return bid2;
	}

	public void setBid2(long bid2) {
		this.bid2 = bid2;
	}

	public long getBuyout2() {
		return buyout2;
	}

	public void setBuyout2(long buyout2) {
		this.buyout2 = buyout2;
	}

	public int getQuantity2() {
		return quantity2;
	}

	public void setQuantity2(int quantity2) {
		this.quantity2 = quantity2;
	}

	@Override
	public String toString() {
		return super.toString() + "Auction2 [name=" + name + ", icon=" + icon + ", owner2="
				+ owner2 + ", bid2=" + bid2 + ", buyout2=" + buyout2
				+ ", quantity2=" + quantity2 + "]";
	}

}
