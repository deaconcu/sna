package com.jike.mobile.sna.model;

import java.io.Serializable;

public class App implements Serializable {
	
	private static final long serialVersionUID = -4731449132413871710L;
	
	private Integer id;
	private String appName;
	
	public App() {}
	
	public App(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String toString() {
		return this.appName;
	}
	
}
