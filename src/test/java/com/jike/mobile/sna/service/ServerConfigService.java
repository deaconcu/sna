package com.jike.mobile.sna.service;

import com.jike.mobile.sna.exception.ServiceException;

public interface ServerConfigService {
	public String get(String key) throws ServiceException;
	
	public Integer getInteger(String key) throws ServiceException;
	
	public void set(String key, String value);
}
