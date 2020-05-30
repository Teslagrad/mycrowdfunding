package com.tesla.crowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tesla.crowdfunding.util.Const;

/**
 * 监听application对象创建和销毁
 * 
 * @author Tesla
 *
 *         2020年2月21日 上午10:52:23
 */
public class SystemUpInitListener implements ServletContextListener {

	Logger log = LoggerFactory.getLogger(ServletContextListener.class);

	// 当application创建时执行初始化方法
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//
		ServletContext application = sce.getServletContext();
		String contextPath = application.getContextPath();
		log.debug("当前上下文的路径:{}", contextPath);
		application.setAttribute(Const.PATH, contextPath);
	}

	// 当application销毁时执行销毁方法
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//
		log.debug("当前应用application对象被销毁");
	}

}
