package com.jike.mobile.sna.service;

import java.io.InputStream;

import com.jike.mobile.sna.exception.InnerException;
import com.jike.mobile.sna.exception.OuterException;

public interface ThriftService {
	
	public int put(String key, InputStream is) throws InnerException;
	
	public byte[] read(String key) throws InnerException, OuterException;
}
