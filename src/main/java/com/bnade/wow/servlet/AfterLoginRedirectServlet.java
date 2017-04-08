package com.bnade.wow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.MD5Util;
import com.bnade.wow.dao.UserDao;
import com.bnade.wow.dao.impl.UserDaoImpl;
import com.bnade.wow.po.User;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

/**
 * QQ登录后的回调处理
 * 
 * @author liufeng0103
 */
@WebServlet(urlPatterns = { "/afterLogin.do" })
public class AfterLoginRedirectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AfterLoginRedirectServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(req);
			String accessToken = null, openID = null;
			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权，做一些数据统计工作
				logger.error("AccessToken为空");
				throw new Exception("登录失败，请重试");
			} else {
				accessToken = accessTokenObj.getAccessToken();
//				long tokenExpireIn = accessTokenObj.getExpireIn();
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				
				UserDao userDao = new UserDaoImpl();
				User dbUser = userDao.getUserByOpenID(openID);
				String nickname;
				if (dbUser == null) {
					UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
	                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
	                if (userInfoBean.getRet() == 0) {
	                	nickname = userInfoBean.getNickname();
	                	userDao.save(new User(openID, nickname));
	                    dbUser = userDao.getUserByOpenID(openID);
	                } else {
	                	throw new Exception("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
	                }
				} else {
					nickname = dbUser.getNickname();
				}
				String newToken = MD5Util.MD5("" + dbUser.getId() + System.currentTimeMillis());
				userDao.updateUserToken(dbUser.getId(), newToken);
				Cookie item = new Cookie("token", newToken);
				item.setHttpOnly(true);
				item.setMaxAge(60 * 60 * 24 * 10);
				item.setPath("/");
				resp.addCookie(item);
				req.getSession().setAttribute("user", dbUser);
				logger.info("用户[{}]登录成功", openID);
				resp.sendRedirect("/");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println("登录失败，请重试，如果还有问题请联系管理员");
			out.println("<a href='https://www.bnade.com'>返回主页</a>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
