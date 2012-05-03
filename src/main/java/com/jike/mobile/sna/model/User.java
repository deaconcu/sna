package com.jike.mobile.sna.model;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String nickName;
	private String email;
	private String sex;
	private Integer birthday;
	private String mobile;
	private String image;
	private String description;
	private Integer regTime;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String nickName, String email, String sex, Integer birthday,
			String mobile, String image, String description, Integer regTime) {
		this.nickName = nickName;
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.mobile = mobile;
		this.image = image;
		this.description = description;
		this.regTime = regTime;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Integer birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getRegTime() {
		return this.regTime;
	}

	public void setRegTime(Integer regTime) {
		this.regTime = regTime;
	}

}