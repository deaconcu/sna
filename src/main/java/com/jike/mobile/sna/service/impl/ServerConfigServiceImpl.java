package com.jike.mobile.sna.service.impl;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.service.ServerConfigService;

public class ServerConfigServiceImpl implements ServerConfigService {
	private Logger log = LoggerFactory.getLogger(ServerConfigServiceImpl.class);
	
	private Properties properties;
	
	public ServerConfigServiceImpl(String serverConfigLocation) throws ServiceException {
		try {
			InputStream in = (new ClassPathResource(serverConfigLocation)).getInputStream();
			properties = new Properties();
			properties.load(in);
			log.info("ServerConfig load Success");
		} catch (Exception e) {
			log.error("ServerConfig load Error" + e.toString());
			throw new ServiceException("system.internal.error");
		}
	}
	
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
	
}
