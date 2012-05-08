package com.jike.mobile.sna.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.jike.mobile.sna.model.validate.Input;

public class Feedback implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields
	
	private Integer id;

	@NotEmpty(groups = Input.class)
	@Length(min = 1, max = 40, groups = Input.class)
	private String title;
	
	@NotEmpty(groups = Input.class)
	@Length(min = 1, max = 200, groups = Input.class)
	private String content;
	
	@NotNull
	private long posttime;
	
	@Email(groups = Input.class)
	private String email;

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getPosttime() {
		return this.posttime;
	}

	public void setPosttime(long posttime) {
		this.posttime = posttime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "title: " + title + " content: " + content  + " email: " + email;
	}
}










