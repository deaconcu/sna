package com.jike.mobile.sna.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFile {
	
	Logger log = LoggerFactory.getLogger(UploadFile.class);
	
	File file;
	String ContentType;
	String filename;
	String savePath;
	String id;

	public UploadFile() {}
	
	public UploadFile(File file, String contentType, String filename) {
		this.file = file;
		this.ContentType = contentType;
		this.filename = filename;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}	
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void upload() throws IOException {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		
		try{
			fos = new FileOutputStream(savePath);
			fis = new FileInputStream(file);
			
			log.info("uploadPath: " + savePath);
			
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
}



















