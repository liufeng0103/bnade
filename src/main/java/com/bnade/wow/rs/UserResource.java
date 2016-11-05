package com.bnade.wow.rs;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bnade.wow.dao.UserDao;
import com.bnade.wow.dao.impl.UserDaoImpl;
import com.bnade.wow.po.User;
import com.bnade.wow.po.UserItemNotification;
import com.bnade.wow.po.UserRealm;
import com.bnade.wow.vo.Result;

@Path("/user")
public class UserResource {

	private UserDao userDao;
	@Context
	private HttpServletRequest req;

	public UserResource() {
		userDao = new UserDaoImpl();
	}

	@GET
	@Path("/getRealms")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRealms() {
		try {
			User user = (User) req.getSession().getAttribute("user");
			return Response.ok(userDao.getRealms(user.getId())).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/addRealm")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRealm(@FormParam("realmId") int realmId) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			userDao.addRealm(new UserRealm(user.getId(), realmId));
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Duplicate entry")) {
				return Response.ok(Result.ERROR("该服务器已添加")).build();
			}
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/deleteRealm")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRealm(@FormParam("realmId") int realmId) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			userDao.deleteRealm(new UserRealm(user.getId(), realmId));
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@GET
	@Path("/getItemNotifications")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItemNotifications() {
		try {
			User user = (User) req.getSession().getAttribute("user");
			return Response.ok(userDao.getItemNotifications(user.getId()))
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/set-item-notification")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemNotification(@FormParam("realmId") int realmId,
			@FormParam("itemId") int itemId, @FormParam("price") long price,
			@FormParam("isInverted") int isInverted) {
		try {
			User user = (User) req.getSession().getAttribute("user");

			UserItemNotification item = new UserItemNotification();
			item.setUserId(user.getId());
			item.setRealmId(realmId);
			item.setItemId(itemId);
			item.setPrice(price);
			item.setIsInverted(isInverted);

			// TODO 验证用户和服务器id
			userDao.addItemNotification(item);
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			if (e.getMessage().contains("Duplicate entry")) {
				return Response.ok(Result.ERROR("物品提醒之前已添加")).build();
			}
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/deleteItemNotification")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItemNotification(@FormParam("realmId") int realmId,
			@FormParam("itemId") int itemId,
			@FormParam("isInverted") int isInverted) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			UserItemNotification item = new UserItemNotification();
			item.setUserId(user.getId());
			item.setRealmId(realmId);
			item.setItemId(itemId);
			item.setIsInverted(isInverted);

			userDao.deleteItemNotification(item);
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}
}
