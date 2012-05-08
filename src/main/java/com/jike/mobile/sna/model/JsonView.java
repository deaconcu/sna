package com.jike.mobile.sna.model;

import java.util.List;

public class JsonView {

	private int code;
	private List<String> details;
	private List<Object> objs;
	
	public JsonView(int code) {
		this(code, null, null);
	}
	
	public JsonView(int code, List<String> details, List<Object> objs) {
		this.code = code;
		this.details = details;
		this.objs = objs;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public List<Object> getObjs() {
		return objs;
	}

	public void setObjs(List<Object> objs) {
		this.objs = objs;
	}

}
