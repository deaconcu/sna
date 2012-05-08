package com.jike.mobile.sna.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class UploadFile {
	
	Logger log = LoggerFactory.getLogger(UploadFile.class);
	
	private String id;
	private MultipartFile file;
	private String savePath;
	
	public UploadFile(MultipartFile file) {
		this.setFile(file);
	}
	
	public void upload() throws IOException {
		OutputStream fos = null;
		InputStream fis = null;
		
		try{
			fos = new FileOutputStream(getSavePath());
			fis = file.getInputStream();
			
			log.info("uploadPath: " + getSavePath());
			
			byte[] buffer = new byte[1024];
			int len = 0;
			
			while((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		}
		catch (IOException e) {
			log.error(e.toString());
			throw e;
		}
		finally{
			try {
				fos.close();
				fis.close();
			} catch (IOException e) {
				log.error("file not close");
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
}



















