package com.bnade.wow.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.bnade.wow.dao.UserDao;
import com.bnade.wow.dao.impl.UserDaoImpl;
import com.bnade.wow.po.User;
import com.bnade.wow.po.UserMailValidation;
import com.sun.jersey.api.view.Viewable;

@Path("/user")
public class UserController extends BaseController {

	@Context
	private HttpServletRequest req;
	private UserDao userDao;
	
	public UserController() {
		userDao = new UserDaoImpl();
	}

	@GET
	@Path("/mail")
	public Viewable mail() {
		System.out.println(getUser());
		return new Viewable("/userMail.jsp");
	}
	
	@GET
	@Path("/realm")
	public Viewable realm() {
		User user = (User) req.getSession().getAttribute("user");
		try {
			req.setAttribute("realms", userDao.getRealms(user.getId()));
			return new Viewable("/userRealm.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错，请联系管理员");
			return new Viewable("/message.jsp");
		}		
	}
	
	@GET
	@Path("/itemNotification")
	public Viewable itemNotification() {
		return new Viewable("/userItemNotification.jsp");
	}
	
	@GET
	@Path("/activation")
	public Viewable activation() {
		return new Viewable("/userActivation.jsp");
	}

	@POST
	@Path("/mail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable saveMail(
			@DefaultValue("") @FormParam("email") String email,
			@Context HttpServletRequest req) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			UserDao userDao = new UserDaoImpl();
			if (email == null || user.getEmail() == null || !email.equals(user.getEmail())) {
				user.setEmail(email);
				user.setValidated(0);
				userDao.update(user);
			}
			req.setAttribute("message", "修改成功");
			return new Viewable("/userMail.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			req.setAttribute("title", "保存用户邮件出错");
			req.setAttribute("message", "保存用户邮件出错");
			return new Viewable("/message.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("title", "保存用户邮件出错");
			req.setAttribute("message", "保存用户邮件出错:" + e.getMessage());
			return new Viewable("/message.jsp");
		}
	}
	
	@GET
	@Path("/mailValidation")
	public Viewable mailValidation(@QueryParam("id") int id, @QueryParam("acode") String acode, @Context HttpServletRequest req) {
		try {
			UserDao userDao = new UserDaoImpl();
			UserMailValidation userM = userDao.getMailValidationById(id);
			req.setAttribute("title", "邮件验证结果");
			if (userM == null) {
				req.setAttribute("message", "验证链接无效");
			} else {
				if (userM.getExpired() - System.currentTimeMillis() > 0 && userM.getAcode().equals(acode)) {
					User user = userDao.getUserByID(id);
					if (user == null) {
						req.setAttribute("message", "验证失败");
					} else {
						user.setValidated(1);
						userDao.update(user);
						userDao.deleteMailValidationById(user.getId());
						User userS = (User) req.getSession().getAttribute("user");
						if (userS != null) {
							userS.setValidated(1);
						}
						req.setAttribute("message", "验证成功");
					}
				} else {
					req.setAttribute("message", "验证失败");
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("message", "验证出错，请联系管理员");
		}
		return new Viewable("/message.jsp");
	}
	
}
