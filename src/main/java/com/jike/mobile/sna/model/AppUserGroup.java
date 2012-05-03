package com.jike.mobile.sna.model;

import java.io.Serializable;
import java.util.Set;

public class AppUserGroup implements Serializable{

	private static final long serialVersionUID = -7787208685678855647L;
	
	private Integer id;
	private Long createTime;
	private String containString;
	private Set<AppUser> contains;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContainString() {
		return containString;
	}
	public void setContainString(String containString) {
		this.containString = containString;
	}
	public Set<AppUser> getContains() {
		return contains;
	}
	public void setContains(Set<AppUser> contains) {
		this.contains = contains;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return id.toString();
	}

}
