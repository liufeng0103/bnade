package com.bnade.wow.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bnade.util.BnadeProperties;
import com.bnade.util.DBUtil;

//@WebFilter(urlPatterns = "/wow/auction/*")
public class SessionFilter3 implements Filter {

	private static Logger logger = LoggerFactory.getLogger(SessionFilter3.class);
	public static final Map<String, Integer> USER_API_LIMITION = new HashMap<>();
	private int limit = Integer.valueOf(BnadeProperties.getValue("api_limit", "60"));
	private String apikey = BnadeProperties.getValue("api_unlimit_key");
	private List<String> blockIPs;

	public SessionFilter3() {
		QueryRunner run = new QueryRunner(DBUtil.getDataSource());
		try {
			blockIPs = run.query("select ip from t_user_block_ip", new ColumnListHandler<String>());
			logger.info("limit {} apikey {} ips {}", limit, apikey, blockIPs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String userIp = request.getHeader("remote-user-ip");
//		userIp = "192.168.1.1";
		String key = req.getParameter("apikey");
//		logger.info("{} apikey {}", userIp, apikey);
		if (key != null && key.equals(apikey)) {
//			logger.info("apikey correct");
			chain.doFilter(req, resp);
		} else {
			if (blockIPs != null && blockIPs.contains(userIp)) {
				logger.info("{} 已被封杀", userIp);
				resp.setContentType("application/json; charset=utf-8");
				resp.getWriter().println("[]");
			} else {
				Integer userCallCount = USER_API_LIMITION.get(userIp);
				if (userCallCount == null) {
					USER_API_LIMITION.put(userIp, 1);
					chain.doFilter(req, resp);
				} else {
					USER_API_LIMITION.put(userIp, ++userCallCount);
					if (userCallCount >= limit) {
						logger.info("{} 已调用 {} 超过限制 {}", userIp, userCallCount, limit);
						resp.setContentType("application/json; charset=utf-8");
						resp.getWriter().println("[]");
					} else {
						chain.doFilter(req, resp);
					}
				}
//				 logger.info("{} 已调用 {}", userIp, userCallCount);
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
