package com.jike.mobile.sna.service;

import java.io.InputStream;

import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.model.UploadFile;

public interface AudioService {
	
	public void uploadWithRandomStringMd5(UploadFile uploadFile, String md5) throws ServiceException;
	
	public void uploadWithFileBytesMd5(UploadFile uploadFile, String md5) throws ServiceException;
	
	public InputStream download(String key) throws ServiceException;
	
	public String setRandomChars(String sessionName, int length, int start, int range);
}
