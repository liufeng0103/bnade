package com.bnade.wow.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class BnadeContextListener implements ServletContextListener {
	
	private static Logger logger = LoggerFactory.getLogger(BnadeContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("ServletContextListener starting");
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				SessionFilter3.USER_API_LIMITION.clear();
				logger.info("重置用户调用");
			}
		}, 0, 1000 * 60);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
