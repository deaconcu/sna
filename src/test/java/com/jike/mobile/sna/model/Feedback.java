package com.jike.mobile.sna.model;

/**
 * Feedback entity. @author MyEclipse Persistence Tools
 */

public class Feedback implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String title;
	private String content;
	private long posttime;
	private Integer categoryid;
	private Integer projectid;
	private String email;

	// Constructors

	/** default constructor */
	public Feedback() {
	}

	/** minimal constructor */
	public Feedback(String title, String content, long posttime,
			Integer categoryid, Integer projectid) {
		this.title = title;
		this.content = content;
		this.posttime = posttime;
		this.categoryid = categoryid;
		this.projectid = projectid;
	}

	/** full constructor */
	public Feedback(String title, String content, long posttime,
			Integer categoryid, Integer projectid, String email) {
		this.title = title;
		this.content = content;
		this.posttime = posttime;
		this.categoryid = categoryid;
		this.projectid = projectid;
		this.email = email;
	}

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

	public Integer getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public Integer getProjectid() {
		return this.projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return "title: " + title + " content: " + content  + " email: " + email;
	}

}