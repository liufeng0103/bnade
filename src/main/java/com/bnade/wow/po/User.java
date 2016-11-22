package com.bnade.wow.po;

public class User {
	
	public static final String SESSION_USER = "user";
	
	private int id;
	private String openID;
	private String email;
	private int validated;
	private String nickname;
	private long expire;

	public User() {
	}

	public User(String openID, String nickname) {
		super();
		this.openID = openID;
		this.nickname = nickname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getValidated() {
		return validated;
	}

	public void setValidated(int validated) {
		this.validated = validated;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", openID=" + openID + ", email=" + email
				+ ", validated=" + validated + ", nickname=" + nickname
				+ ", expire=" + expire + "]";
	}

}
