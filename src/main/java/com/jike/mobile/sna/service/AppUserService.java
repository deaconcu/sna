package com.jike.mobile.sna.service;

import java.util.ArrayList; 
import java.util.HashSet;

import com.jike.mobile.sna.exception.InnerException;
import com.jike.mobile.sna.exception.OuterException;
import com.jike.mobile.sna.model.AppUser;

public interface AppUserService {

	public void saveFriend(ArrayList<AppUser> appUserList) throws InnerException;

	public void saveGroup(HashSet<AppUser> appUserSet) throws InnerException;

	public AppUser findAppUser(String userId, Integer appId) throws InnerException, OuterException;
	
}
