package com.bnade.wow.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.bnade.wow.po.User;

public class BaseController {

	@Context
	private HttpServletRequest req;

	public User getUser() {
		return (User) req.getSession().getAttribute(User.SESSION_USER);
	}
	
	public Cookie getCookieByName(String name) {
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

}
