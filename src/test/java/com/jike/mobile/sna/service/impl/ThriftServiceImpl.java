package com.jike.mobile.sna.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jike.mobile.sna.common.thrift.Connection;
import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.service.ThriftService;
import com.jike.mobile.sna.util.ServerConfig;

public class ThriftServiceImpl implements ThriftService {
	
	Logger log = LoggerFactory.getLogger(ThriftServiceImpl.class);
	private Connection connection;
	
	public ThriftServiceImpl() throws NumberFormatException, ServiceException {
		connection = new Connection(ServerConfig.get("thrift_addr"), 
				Integer.parseInt(ServerConfig.get("thrift_port")), ServerConfig.get("thrift_dbName"));
	}
	
	public int put(String id, File file) throws ServiceException {
		byte[] bytes = new byte[1024];
		StringBuffer stringBuffer = new StringBuffer();
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			
			int length = 0;
			while((length = inputStream.read(bytes)) > 0){		
				for(int i = 0; i < length; i++) {
					String hex = Integer.toHexString(bytes[i] & 0xFF);
					if (hex.length() == 1) {
						hex = '0' + hex;
					}
					stringBuffer.append(hex.toUpperCase());
				}
			}
		} catch (IOException e) {
			log.error("io Exception");
			throw new ServiceException("upload.failed.ioexception");
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("Can't close file");
			}
		}
		
		try {
			connection.open();	
			//int result = 0;
			int result = connection.put(id, stringBuffer.toString(), Integer.parseInt(ServerConfig.get("thrift_expire_time")));
			
			if(result != 0) {
				log.error("thrift upload error, error code:" + result);
				throw new ServiceException("upload.failed.ioexception");
			}
			
			connection.close();
			return result;
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException("upload.failed.ioexception");
		}
	}
	
	public byte[] read(String id) throws ServiceException {
		String value;
		try {
			connection.open();	
			value = connection.read(id);
			connection.close();
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException("upload.failed.ioexception");
		}
		
		if("".equals(value)) {
			log.warn("download file is not exist, file id: " + id);
			throw new ServiceException("download.file.is.not.exist");
		}
		
		char[] chars = new char[value.length()];
		byte[] bytes = new byte[value.length()/2];
		value.getChars(0, value.length(), chars, 0);

		for(int i = 0; i < chars.length; i+=2) {
			
			byte high = (byte) (Byte.parseByte(new String(new char[]{chars[i]}), 16) << 4);
			byte low = Byte.parseByte(new String(new char[]{chars[i+1]}), 16);			
			bytes[i/2] = (byte) (low | high);
		}
		
		return bytes;
	}
}




















