package com.jike.mobile.sna.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jike.mobile.sna.dao.AudioDao;
import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.model.Audio;
import com.jike.mobile.sna.model.UploadFile;
import com.jike.mobile.sna.service.AudioService;
import com.jike.mobile.sna.service.ThriftService;
import com.jike.mobile.sna.util.ServerConfig;
import com.jike.mobile.sna.util.Transfer;

public class AudioServiceImpl implements AudioService{
	private static final Logger log = LoggerFactory.getLogger(AudioServiceImpl.class);
	
	AudioDao audioDao;
	ThriftService thriftService;

	@Override
	public void uploadWithRandomStringMd5(UploadFile uFile, String md5) throws ServiceException {	
		if(md5 == null) throw new ServiceException("upload.failed.md5.empty");
		if(!check(md5, "randomChars", "uploadCode")) throw new ServiceException("upload.failed.md5.wrong");
		removeRandomChars("randomChars");
		thriftUpload(uFile);
		save(uFile);
	}

	@Override
	public void uploadWithFileBytesMd5(UploadFile uFile, String md5) throws ServiceException {
		byte[] b = null;
		try {
			b = getBytes(uFile, 128);
		}
		catch (IOException e) {
			throw new ServiceException("upload.failed.ioexception");
		}
		if(!check(md5, b, "uploadCode")){;
			throw new ServiceException("upload.failed.md5.wrong");
		}
		thriftUpload(uFile);
		save(uFile);
	}
	
	@Override
	public InputStream download(String key) throws ServiceException{
		return new ByteArrayInputStream(thriftService.read(key));
	}
	
	

	@Override
	public String setRandomChars(String sessionName, int length, int start,	int range) {
		/*
		String chars = Random.getChars(length, start, range);
		ActionContext.getContext().getSession().put(sessionName, chars);
		return chars;
		*/
		return null;
	}
	
	@SuppressWarnings("unused")
	private void upload(UploadFile uFile) throws ServiceException {	
		/*		
		ServletContext sc = ServletActionContext.getServletContext();
		String fileId = UUID.randomUUID().toString();
		String savePath = sc.getRealPath("/") + sc.getInitParameter("fileSavePath") + File.separator + fileId;
		
		uFile.setId(fileId);
		uFile.setSavePath(savePath);
		
		try {
			uFile.upload();
		} catch (IOException e) {
			log.error("upload.failed.ioexception");
			throw new ServiceException("upload.failed.ioexception", e);
		}
		*/
	}
	
	
	private void thriftUpload(UploadFile uFile) throws ServiceException {
		String fileId = UUID.randomUUID().toString();
		uFile.setId(fileId);
		thriftService.put(fileId, uFile.getFile());
	}
	
	private void save(UploadFile uFile) throws ServiceException {
		Audio audio = new Audio(uFile.getId());
		
		try {
			audioDao.save(audio);
		} catch (RuntimeException e) {
			throw new ServiceException("upload.failed.ioexception");
		}
	}

	private byte[] getBytes(UploadFile uFile, int n) throws IOException{
		byte[] b = new byte[n];
		InputStream iStream = new BufferedInputStream(new FileInputStream(uFile.getFile()));
		iStream.read(b);
		iStream.close();
		return b;
	}

	private boolean check(String md5Input, String sessionName, String codeName) {
		/*
		try {
			return check(md5Input, 
					(ServletActionContext.getServletContext().getInitParameter(codeName) 
					+ ActionContext.getContext().getSession().get(sessionName))
					.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			log.info(e.toString());
			return false;
		}
		*/
		return false;
	}
	
	private boolean check(String md5Input, byte[] fileBytes, String codeName) throws ServiceException {
		byte[] codeNameBytes = null;
		try {
			codeNameBytes = ServerConfig.get("uploadCode").getBytes("utf-8"); 
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		
		byte[] b = new byte[codeNameBytes.length + fileBytes.length];
		
		System.arraycopy(codeNameBytes, 0, b, 0, codeNameBytes.length);
		System.arraycopy(fileBytes, 0, b, codeNameBytes.length, fileBytes.length);
		
		return check(md5Input, b);
	}
	
	private boolean check(String md5Input, byte[] b) {
		String md5 = Transfer.getMD5(b);
		
		log.info("md5Client: " + md5Input + " | md5Server: " + md5);
		if(md5.equals(md5Input)) return true;
		else return false;
	}
	
	private void removeRandomChars(String sessionName) {
		//ActionContext.getContext().getSession().put(sessionName, null);
	}

	@SuppressWarnings("unused")
	private String getPlayUrlFromId(String fileId) {
		/*
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext context = ServletActionContext.getServletContext();
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() 
				+ request.getContextPath() + context.getInitParameter("playUrl") + "?id=" + fileId;
		*/
		return null;
	}
	
	//setter && getter
	public AudioDao getAudioDao() {
		return audioDao;
	}

	public void setAudioDao(AudioDao audioDao) {
		this.audioDao = audioDao;
	}
	
	public ThriftService getThriftService() {
		return thriftService;
	}

	public void setThriftService(ThriftService thriftService) {
		this.thriftService = thriftService;
	}

}













