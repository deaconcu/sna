package com.jike.mobile.sna.model;

public class Audio {
	private int id;
	private String uuid;
	
	public Audio() {}
	
	public Audio(String uuid) {
		this.uuid = uuid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String toString() {
		return uuid;
	}
}	
