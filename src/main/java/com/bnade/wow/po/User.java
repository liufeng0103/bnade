package com.bnade.wow.po;

public class User {
	private int id;
	private String openID;
	private String email;
	private int validated;
	private String nickname;

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

	@Override
	public String toString() {
		return "User [id=" + id + ", openID=" + openID + ", email=" + email
				+ ", validated=" + validated + ", nickname=" + nickname + "]";
	}

}
