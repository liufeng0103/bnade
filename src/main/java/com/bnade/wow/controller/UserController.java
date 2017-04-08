package com.bnade.wow.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.DBUtil;
import com.bnade.util.MD5Util;
import com.bnade.util.StringUtils;
import com.bnade.util.TimeUtil;
import com.bnade.wow.dao.UserDao;
import com.bnade.wow.dao.impl.UserDaoImpl;
import com.bnade.wow.po.User;
import com.bnade.wow.po.UserMailValidation;
import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;
import com.sun.jersey.api.view.Viewable;

@Path("/user")
public class UserController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Context
	private HttpServletRequest req;
	@Context 
	private HttpServletResponse resp;
	private UserDao userDao;
	
	public UserController() {
		userDao = new UserDaoImpl();
	}

	@GET
	@Path("/login")
	public void login(@CookieParam("token") String token) {
		try {
			// 自动登录
			if (!StringUtils.isEmpty(token)) {
				UserDao userDao = new UserDaoImpl();
				User user = userDao.getUserByToken(token);
				if (user != null) {
					// 刷新token
					String newToken = MD5Util.MD5("" + user.getId() + System.currentTimeMillis());
					userDao.updateUserToken(user.getId(), newToken);
					Cookie item = getCookieByName("token");
					item.setHttpOnly(true);
					item.setValue(newToken);
					item.setMaxAge(60 * 60 * 24 * 10 - item.getMaxAge());
					System.out.println(item.getMaxAge());
					item.setPath("/");
					resp.addCookie(item);
					// 创建session
					req.getSession().setAttribute("user", user);
					// 跳转到主页
					resp.setContentType("text/html;charset=utf-8");
					resp.sendRedirect("/");
					logger.info("用户{}自动登录", user.getId());
					return;
				} else {
					logger.info("无效token:{}", token);
				}
			}
			resp.setContentType("text/html;charset=utf-8");
			resp.sendRedirect(new Oauth().getAuthorizeURL(req));
		} catch (QQConnectException | IOException | SQLException e) {
			e.printStackTrace();
		}
//		return new Viewable("/WEB-INF/login.jsp");
	}
	
	@GET
	@Path("/signOut")
	public void signOut() {
		try {
			Cookie item = getCookieByName("token");
			if (item != null) {
				item.setMaxAge(0);
				item.setPath("/");
				item.setValue(null);
				resp.addCookie(item);
			}
			req.getSession().removeAttribute("user");
			resp.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GET
	@Path("/mail")
	public Viewable mail() {
//		System.out.println(getUser());
		return new Viewable("/userMail.jsp");
	}
	
	@GET
	@Path("/info")
	public Viewable info() {
		req.setAttribute("user", getUser());
		return new Viewable("/userInfo.jsp");
	}
	
	@GET
	@Path("/itemQuery")
	public Viewable itemQuery() {
		return new Viewable("/itemQuery2.jsp");
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
	@Path("/character")
	public Viewable character() {
		User user = getUser();
		try {
			req.setAttribute("realms", userDao.getRealms(user.getId()));
			req.setAttribute("characters", userDao.getCharacters(user.getId()));
			return new Viewable("/userCharacter.jsp");
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
		req.setAttribute("user", getUser());
		return new Viewable("/userActivation.jsp");
	}
	
	@POST
	@Path("/activation")
	public Viewable activationPost(@FormParam("activationCode") String code) {
		User user = getUser();
		if (code == null || "".equals(code) || user == null) {
			req.setAttribute("title", "出错");
			req.setAttribute("message", "出错");
			return new Viewable("/message.jsp");
		} else {
			QueryRunner run = new QueryRunner(DBUtil.getDataSource());
			try(Connection conn = DBUtil.getDataSource().getConnection()) {
				boolean autoCommit = conn.getAutoCommit();
				String dbCode = run.query("select activationCode from t_user_activation where activationCode=?", new ScalarHandler<String>(1), code);
				try {
					if (dbCode != null) {
						conn.setAutoCommit(false);
						if (System.currentTimeMillis() > user.getExpire()) {
							user.setExpire(System.currentTimeMillis() + 31 * TimeUtil.DAY);
						} else {
							user.setExpire(user.getExpire() + 31 * TimeUtil.DAY);
						}
						run.update(conn, "update t_user set expire=? where id=?", user.getExpire(), user.getId());
						run.update(conn, "delete from t_user_activation where activationCode=?", code);
						run.update(conn, "insert into t_user_activation_history (activationCode, userId) values (?,?)", code, user.getId());
						conn.commit();
					} else {
						user.setCount(user.getCount() + 1);
						if (user.getCount() > 3) {
							logger.info("激活失败超过3次{}", user);
						}
						req.setAttribute("title", "失败");
						req.setAttribute("message", "激活失败,请重试,有问题请联系管理员");
						return new Viewable("/message.jsp");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					conn.rollback();
					req.setAttribute("title", "出错");
					req.setAttribute("message", "出错,请联系管理员，错误原因：" + e.getMessage());
					return new Viewable("/message.jsp");
				} finally {
					conn.setAutoCommit(autoCommit);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				req.setAttribute("title", "出错");
				req.setAttribute("message", "出错,请联系管理员");
				return new Viewable("/message.jsp");
			}
		}
		req.setAttribute("title", "成功");
		req.setAttribute("message", "成功");
		return new Viewable("/message.jsp");
	}

	@POST
	@Path("/mail")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Viewable saveMail(@DefaultValue("") @FormParam("email") String email) {
		try {
			if ("".equals(email)) {
				req.setAttribute("message", "邮箱不能为空");
			} else {
				User user = getUser();
				UserDao userDao = new UserDaoImpl();
				if (email != null && !"".equals(email) && !email.equals(user.getEmail())) {
					if (userDao.getUserByMail(email) == null) {
						user.setEmail(email);
						user.setValidated(0);
						userDao.update(user);
						req.setAttribute("message", "修改成功");
					} else {
						req.setAttribute("message", "邮箱已被注册");
					}
				} else {
					req.setAttribute("message", "修改成功");
				}
			}
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
	public Viewable mailValidation(@QueryParam("id") int id, @QueryParam("acode") String acode) {
		try {
			UserDao userDao = new UserDaoImpl();
			UserMailValidation userM = userDao.getMailValidationById(id);
			req.setAttribute("title", "邮件验证结果");
			if (userM == null) {
				req.setAttribute("message", "验证链接无效");
			} else {
				if (userM.getExpired() - System.currentTimeMillis() > 0 && userM.getAcode().equals(acode)) {
					User user = userDao.getUserByID(id);
					if (user == null || !user.getEmail().equals(userM.getEmail())) {
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
