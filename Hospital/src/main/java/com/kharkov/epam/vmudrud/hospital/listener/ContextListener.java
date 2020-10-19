package com.kharkov.epam.vmudrud.hospital.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		initLog4J(servletContext);
	}

	private void initLog4J(ServletContext servletContext) {
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}