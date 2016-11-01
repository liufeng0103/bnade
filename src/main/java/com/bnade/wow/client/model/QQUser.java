package com.bnade.wow.client.model;

/*
 ret	返回码
 msg	如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
 nickname	用户在QQ空间的昵称。
 figureurl	大小为30×30像素的QQ空间头像URL。
 figureurl_1	大小为50×50像素的QQ空间头像URL。
 figureurl_2	大小为100×100像素的QQ空间头像URL。
 figureurl_qq_1	大小为40×40像素的QQ头像URL。
 figureurl_qq_2	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
 gender	性别。 如果获取不到则默认返回"男"
 is_yellow_vip	标识用户是否为黄钻用户（0：不是；1：是）。
 vip	标识用户是否为黄钻用户（0：不是；1：是）
 yellow_vip_level	黄钻等级
 level	黄钻等级
 is_yellow_year_vip	标识是否为年费黄钻用户（0：不是； 1：是）
 */
public class QQUser {
	private int ret;
	private String msg;
	private String nickname;
	private String figureurl;
	private String figureurl_1;
	private String figureurl_2;
	private String figureurl_qq_1;
	private String figureurl_qq_2;
	private String gender;
	private int is_yellow_vip;
	private int vip;
	private int yellow_vip_level;
	private int level;
	private int is_yellow_year_vip;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFigureurl() {
		return figureurl;
	}

	public void setFigureurl(String figureurl) {
		this.figureurl = figureurl;
	}

	public String getFigureurl_1() {
		return figureurl_1;
	}

	public void setFigureurl_1(String figureurl_1) {
		this.figureurl_1 = figureurl_1;
	}

	public String getFigureurl_2() {
		return figureurl_2;
	}

	public void setFigureurl_2(String figureurl_2) {
		this.figureurl_2 = figureurl_2;
	}

	public String getFigureurl_qq_1() {
		return figureurl_qq_1;
	}

	public void setFigureurl_qq_1(String figureurl_qq_1) {
		this.figureurl_qq_1 = figureurl_qq_1;
	}

	public String getFigureurl_qq_2() {
		return figureurl_qq_2;
	}

	public void setFigureurl_qq_2(String figureurl_qq_2) {
		this.figureurl_qq_2 = figureurl_qq_2;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getIs_yellow_vip() {
		return is_yellow_vip;
	}

	public void setIs_yellow_vip(int is_yellow_vip) {
		this.is_yellow_vip = is_yellow_vip;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public int getYellow_vip_level() {
		return yellow_vip_level;
	}

	public void setYellow_vip_level(int yellow_vip_level) {
		this.yellow_vip_level = yellow_vip_level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getIs_yellow_year_vip() {
		return is_yellow_year_vip;
	}

	public void setIs_yellow_year_vip(int is_yellow_year_vip) {
		this.is_yellow_year_vip = is_yellow_year_vip;
	}

	@Override
	public String toString() {
		return "QQUser [ret=" + ret + ", msg=" + msg + ", nickname=" + nickname
				+ ", figureurl=" + figureurl + ", figureurl_1=" + figureurl_1
				+ ", figureurl_2=" + figureurl_2 + ", figureurl_qq_1="
				+ figureurl_qq_1 + ", figureurl_qq_2=" + figureurl_qq_2
				+ ", gender=" + gender + ", is_yellow_vip=" + is_yellow_vip
				+ ", vip=" + vip + ", yellow_vip_level=" + yellow_vip_level
				+ ", level=" + level + ", is_yellow_year_vip="
				+ is_yellow_year_vip + "]";
	}

}
