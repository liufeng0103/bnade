package com.bnade.wow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.bnade.wow.po.User;

public class BaseController {

	@Context
	private HttpServletRequest req;

	public User getUser() {
		return (User) req.getSession().getAttribute(User.SESSION_USER);
	}

}
