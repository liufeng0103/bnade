package com.bnade.wow.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销用户
 * 
 * @author liufeng0103
 */
@WebServlet(urlPatterns = { "/signOut.do" })
public class SignOutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		for(Cookie cookie : req.getCookies()) {
			if ("nickname".equals(cookie.getName())) {
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
//				System.out.println("删除cookie");
				break;
			}
		}
		req.getSession().removeAttribute("user");
		resp.setContentType("text/html;charset=utf-8");
		resp.sendRedirect("./");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
