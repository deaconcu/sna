package com.jike.mobile.sna.service;

import java.io.File;

import com.jike.mobile.sna.exception.ServiceException;

public interface ThriftService {
	
	public int put(String key, File file) throws ServiceException;
	
	public byte[] read(String key) throws ServiceException;
}
