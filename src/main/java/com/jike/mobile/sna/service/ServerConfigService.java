package com.jike.mobile.sna.service;

import com.jike.mobile.sna.exception.InnerException;

public interface ServerConfigService {
	public String get(String key) throws InnerException;
	
	public Integer getInteger(String key) throws InnerException;
	
	public void set(String key, String value);
}
