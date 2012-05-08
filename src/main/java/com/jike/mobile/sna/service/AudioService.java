package com.jike.mobile.sna.service;

import com.jike.mobile.sna.exception.InnerException;
import com.jike.mobile.sna.exception.OuterException;
import com.jike.mobile.sna.model.UploadFile;

public interface AudioService {
	
	public void uploadWithRandomStringMd5(UploadFile uploadFile, String md5) throws InnerException;
	
	public void uploadWithFileBytesMd5(UploadFile uploadFile, String md5) throws InnerException, OuterException;
	
	public byte[] download(String key) throws InnerException, OuterException;
	
	public String setRandomChars(String sessionName, int length, int start, int range);
}
