package com.jike.mobile.sna.service.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.service.ServerConfigService;

public class ServerConfigServiceImpl implements ServerConfigService, ServletContextAware{
	private Logger log = LoggerFactory.getLogger(ServerConfigServiceImpl.class);
	
	private ServletContext servletContext;
	private Properties properties;
	
	public ServerConfigServiceImpl() {}
	
	@Override
	public String get(String key) throws ServiceException {
		String value = properties.getProperty(key);
		if(value == null) {
			log.error("can't get serverConfig value: " + key);
			throw new ServiceException("system.internal.error");
		}
		return value;
	}
	
	@Override
	public Integer getInteger(String key) throws ServiceException {
		try {
			return Integer.parseInt(get(key));
		} catch (NumberFormatException e) {
			log.error(key + " is not a number!");
			throw new ServiceException("system.internal.error");
		}
	}

	@Override
	public void set(String key, String value) {
		properties.setProperty(key, value);
	}

	@Override
	public void setServletContext(ServletContext sc) {
		this.servletContext = sc;
		initProperties();
	}
	
	private void initProperties() {
		try {
			InputStream in = servletContext.getResourceAsStream(servletContext.getInitParameter("serverConfigLocation")); 
			properties = new Properties();
			properties.load(in);

			properties.setProperty("real_root_path", servletContext.getRealPath("/"));
			
			String baseUrl = servletContext.getRealPath("/");
			
			properties.setProperty("base_url", baseUrl);
			properties.setProperty("serverConfigLocation", servletContext.getInitParameter("serverConfigLocation"));
			log.info("ServerConfig load Success");
		}
		catch (Exception e) {
			log.error("ServerConfig load Error");
			e.printStackTrace();
		}
	}


	
}
