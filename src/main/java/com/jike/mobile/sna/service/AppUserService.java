package com.jike.mobile.sna.service;

import java.util.ArrayList; 
import java.util.HashSet;

import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.model.AppUser;

public interface AppUserService {

	public void saveFriend(ArrayList<AppUser> appUserList) throws ServiceException;

	public void saveGroup(HashSet<AppUser> appUserSet) throws ServiceException;

	public AppUser findAppUser(String userId, Integer appId) throws ServiceException;
	
}
