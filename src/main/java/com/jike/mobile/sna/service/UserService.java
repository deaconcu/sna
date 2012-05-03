package com.jike.mobile.sna.service;

import com.jike.mobile.sna.model.UserLogin;

public interface UserService {
	
	public String login(UserLogin userLogin);
	
	public String logOut(UserLogin userLogin);
	
	public String signIn(UserLogin userLogin);
}
