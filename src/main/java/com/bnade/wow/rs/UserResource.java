package com.bnade.wow.rs;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import com.bnade.util.BnadeProperties;
import com.bnade.util.MD5Util;
import com.bnade.util.Mail;
import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.UserDao;
import com.bnade.wow.dao.impl.UserDaoImpl;
import com.bnade.wow.po.User;
import com.bnade.wow.po.UserCharacter;
import com.bnade.wow.po.UserItemNotification;
import com.bnade.wow.po.UserMailValidation;
import com.bnade.wow.po.UserRealm;
import com.bnade.wow.vo.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	@Path("/addCharacter")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCharacter(@FormParam("realmId") int realmId, @FormParam("name") String name) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			userDao.addCharacter(new UserCharacter(user.getId(), realmId, name));
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("Duplicate entry")) {
				return Response.ok(Result.ERROR("该角色已添加")).build();
			}
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}
	
	@POST
	@Path("/deleteCharacter")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCharacter(@FormParam("realmId") int realmId, @FormParam("name") String name) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			userDao.deleteCharacter(new UserCharacter(user.getId(), realmId, name));
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
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
			return Response.ok(userDao.getItemNotifications(user.getId())).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/addItemNotification")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItemNotification(@FormParam("realmId") int realmId,
			@FormParam("itemId") int itemId, @FormParam("bonusList") String bonusList, @FormParam("price") long price,
			@FormParam("isInverted") int isInverted, @FormParam("petSpeciesId") int petSpeciesId, @FormParam("petBreedId") int petBreedId) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			
			if (!user.getIsVip()) {
				return Response.ok(Result.ERROR("超过限制")).build();
			}
			UserItemNotification itemN = new UserItemNotification();
			itemN.setUserId(user.getId());
			itemN.setRealmId(realmId);
			itemN.setItemId(itemId);
			itemN.setBonusList(bonusList == null ? "" : bonusList);
			itemN.setPrice(price);
			itemN.setIsInverted(isInverted);
			itemN.setPetSpeciesId(petSpeciesId);
			itemN.setPetBreedId(petBreedId);

			if (!checkInput(itemN)) {
				throw new Exception("出错");
			}
			userDao.addItemNotification(itemN);
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			if (e.getMessage().contains("Duplicate entry")) {
				return Response.ok(Result.ERROR("物品提醒之前已添加")).build();
			}
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/deleteItemNotifications")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItemNotifications(String json) {
		try {
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<UserItemNotification>>() {}.getType();
			List<UserItemNotification> itemNs = gson.fromJson(json, listType);
			User user = (User) req.getSession().getAttribute("user");
			for (UserItemNotification itemN : itemNs) {
				itemN.setUserId(user.getId());
			}
			userDao.deleteItemNotifications(itemNs);
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	@POST
	@Path("/updateItemNotification")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItemNotification(@FormParam("realmId") int realmId,
			@FormParam("itemId") int itemId, @FormParam("price") long price,
			@FormParam("isInverted") int isInverted) {
		try {
			User user = (User) req.getSession().getAttribute("user");
			UserItemNotification itemN = new UserItemNotification();
			itemN.setUserId(user.getId());
			itemN.setRealmId(realmId);
			itemN.setItemId(itemId);
			itemN.setPrice(price);
			itemN.setIsInverted(isInverted);

			if (!checkInput(itemN)) {
				throw new Exception("出错");
			}
			userDao.updateItemNotification(itemN);
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("出错")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	private boolean checkInput(UserItemNotification itemN) {
		if (itemN.getRealmId() < 0 || itemN.getRealmId() > 175 || itemN.getUserId() == 0
				|| itemN.getItemId() == 0) {
			return false;
		}
		return true;
	}

	@POST
	@Path("/updateEmailNotifications")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEmailNotifications(String json) {
		try {
			Gson gson = new Gson();
			Type listType = new TypeToken<ArrayList<UserItemNotification>>() {
			}.getType();
			List<UserItemNotification> itemNs = gson.fromJson(json, listType);
			User user = (User) req.getSession().getAttribute("user");
			for (UserItemNotification itemN : itemNs) {
				itemN.setUserId(user.getId());
			}
			userDao.updateEmailNotifications(itemNs);
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
	@Path("/sendMailValidation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMailValidation() {
		String host = BnadeProperties.getValue("email_validation_host");
		try {
			User user = (User) req.getSession().getAttribute("user");
			UserMailValidation userM = userDao.getMailValidationById(user
					.getId());
			String acode;
			if (userM == null) {
				userM = new UserMailValidation();
				userM.setUserId(user.getId());
				acode = generateAcode(user);
				userM.setAcode(acode);
				userM.setExpired(System.currentTimeMillis() + TimeUtil.DAY); // 24
																				// 小时内有效
				userDao.addMailValidation(userM);
			} else {
				if (userM.getExpired() - System.currentTimeMillis() < TimeUtil.HOUR) { // 有效期小于1小时时重置
					acode = generateAcode(user);
					userM.setAcode(acode);
					userM.setExpired(System.currentTimeMillis() + TimeUtil.DAY);
				} else {
					acode = userM.getAcode();
				}
				userDao.updateMailValidationById(userM);
			}
//			System.out.println(host);
			Mail.sendHtmlEmail(
					"BNADE邮箱激活",
					"欢迎使用BNADE，请点击后面link激活邮箱：<a href='"
							+ host
							+ "/page/user/mailValidation?id="
							+ user.getId()
							+ "&acode="
							+ acode
							+ "'>"+host+"/page/user/mailValidation?id="
							+ user.getId() + "&acode=" + acode + "</a>"
							+ " 此链接24小时内有效", user.getEmail());
			return Response.ok(Result.OK("success")).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR("error")).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.ok(Result.ERROR(e.getMessage())).build();
		}
	}

	private String generateAcode(User user) {
		return MD5Util.MD5("" + user.getId())
				+ MD5Util.MD5(user.getEmail() + System.currentTimeMillis())
				+ MD5Util.MD5(user.getOpenID());
	}
}
