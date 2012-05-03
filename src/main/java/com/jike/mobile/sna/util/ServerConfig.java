package com.jike.mobile.sna.util;

import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.service.ServerConfigService;

public class ServerConfig {
	private static ServerConfigService serverConfigService;
	
	public void setServerConfigService(ServerConfigService serverConfigService) {
		ServerConfig.serverConfigService = serverConfigService;
	}
	
	public static String get(String key) throws ServiceException {
		return serverConfigService.get(key);
	}
	
	public static Integer getInteger(String key) throws ServiceException {
		return serverConfigService.getInteger(key);
	}
}

















