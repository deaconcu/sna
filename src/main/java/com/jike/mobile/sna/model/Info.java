package com.jike.mobile.sna.model;

import net.sf.json.JSONObject;

public class Info {
	private String version;
	private String versionCode;
	private String downloadUrl;
	private String versionDesc;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	
	public String toJson() {
		JSONObject item = new JSONObject();
		item.put("version", version);
		item.put("versionCode", versionCode);
		item.put("downloadUrl", downloadUrl);
		item.put("versionDesc", versionDesc);
		return item.toString();
	}

}






