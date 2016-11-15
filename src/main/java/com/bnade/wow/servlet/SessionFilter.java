package com.bnade.wow.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.bnade.wow.po.User;

@WebFilter(urlPatterns = "/page/user/*")
public class SessionFilter implements Filter {
	
	private static List<String> ignorePathList = new ArrayList<>();
	static {
		ignorePathList.add("/user/login");
		ignorePathList.add("/user/signOut");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
//		System.out.println(request.getContextPath());
//		System.out.println(request.getServletPath());
//		System.out.println(request.getPathInfo());
//		System.out.println(request.getRequestURI());
//		System.out.println(request.getRequestURL());
		if (!ignorePath(request.getPathInfo())) {
			User user = (User)request.getSession().getAttribute("user");
			if (user == null) {
				req.setAttribute("title", "用户未登录");
				req.setAttribute("message", "用户未登录");
				req.getServletContext().getRequestDispatcher("/message.jsp").forward(req, resp);
			} else {
				chain.doFilter(req, resp);
			}
		} else {
			chain.doFilter(req, resp);
		}		
	}
	
	private boolean ignorePath(String path) {
		if (ignorePathList.contains(path)) {
			return true;
		}
		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
