package com.jike.mobile.sna.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jike.mobile.sna.dao.AppDao;
import com.jike.mobile.sna.dao.AppUserDao;
import com.jike.mobile.sna.dao.AppUserGroupDao;
import com.jike.mobile.sna.exception.ServiceException;
import com.jike.mobile.sna.model.App;
import com.jike.mobile.sna.model.AppUser;
import com.jike.mobile.sna.model.AppUserGroup;
import com.jike.mobile.sna.service.AppUserService;

public class AppUserServiceImpl implements AppUserService {
	
	Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

	private AppDao appDao;
	private AppUserDao appUserDao;
	private AppUserGroupDao appUserGroupDao;

	//Override
	
	@Override
	public void saveFriend(ArrayList<AppUser> appUserList) throws ServiceException {
		try {
			for(AppUser appUser : appUserList ) {
				App app = appDao.findById(appUser.getApp().getId());
				if(app == null) continue;
	
				Set<AppUser> friends = appUser.getFriends();
	
				Iterator<AppUser> iterator = friends.iterator();
				List<AppUser> friendsPersisted = new ArrayList<AppUser>();
				while(iterator.hasNext()) {
					AppUser friend = iterator.next();
					List<AppUser> list = appUserDao.findByUserIdAndApp(friend.getUserId(),app);
	
					if(list.size() == 0) {
						friend.setCreateTime(System.currentTimeMillis());
						appUserDao.save(friend);
					} else {
						friendsPersisted.add(list.get(0));
						iterator.remove();
					}
				}
				friends.addAll(friendsPersisted);
	
				List<AppUser> list = appUserDao.findByUserIdAndApp(appUser.getUserId(), app);
				if(list.size() == 0) {
					appUser.setCreateTime(System.currentTimeMillis());
					appUserDao.save(appUser);
				} else {
					AppUser appUserPersisted = list.get(0);
					appUserPersisted.setFriends(appUser.getFriends());
					appUserDao.update(appUserPersisted);
				}
			}
		} catch (RuntimeException re) {
			log.error("runtimeException " + re.toString());
			throw new ServiceException("system.internal.error");
		}
	}

	@Override
	public void saveGroup(HashSet<AppUser> appUserSet) throws ServiceException {
		Set<AppUser> contain = new HashSet<AppUser>();
		TreeMap<Integer, TreeSet<String>> map = new TreeMap<Integer, TreeSet<String>>();
		try {
			for(AppUser appUser: appUserSet) {
				App app = appDao.findById(appUser.getApp().getId());
				if(app != null) {
					List<AppUser> list = appUserDao.findByUserIdAndApp(appUser.getUserId(), app);
					if(list.size() == 0) {
						appUser.setCreateTime(System.currentTimeMillis());
						appUserDao.save(appUser);
						put(map, appUser);
						contain.add(appUser);
					} else {
						put(map, appUser);
						contain.add(list.get(0));
					}
				}
			}
			
			if(contain.size() > 1) {
				String containString = map.toString();
				List<AppUserGroup> list = appUserGroupDao.findByProperty("containString", containString);
	
				if(list.size() == 0) {
					AppUserGroup appUserGroup = new AppUserGroup();
					appUserGroup.setContainString(containString);
					appUserGroup.setCreateTime(System.currentTimeMillis());
					appUserGroup.setContains(contain);
					appUserGroupDao.save(appUserGroup);
				}
			}
		} catch(RuntimeException re) {
			log.error("runtimeException: " + re.toString());
			throw new ServiceException("system.internal.error");
		}
	}

	@Override
	public AppUser findAppUser(String userId, Integer appId) throws ServiceException {
		try {
			App app = appDao.findById(appId);
			if(app == null) throw new ServiceException("data.is.not.exist");
			
			List<AppUser> list = appUserDao.findByUserIdAndApp(userId, app);
			if(list.size() == 0) throw new ServiceException("data.is.not.exist");
			
			return list.get(0);
		} catch (RuntimeException re) {
			log.error("runtimeException: " + re.toString());
			throw new ServiceException("system.internal.error");
		}
	}

	//private

	private void put(TreeMap<Integer, TreeSet<String>> map, AppUser appUser) {
		Integer appId = appUser.getApp().getId();
		String userId = appUser.getUserId();
		TreeSet<String> set = map.get(appId);
		if(set == null) {
			set = new TreeSet<String>();
			set.add(userId);
			map.put(appId, set);
		}
		else {
			set.add(userId);
		}
	}

	//setter && getter
	
	public AppDao getAppDao() {
		return appDao;
	}

	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	public AppUserDao getAppUserDao() {
		return appUserDao;
	}

	public void setAppUserDao(AppUserDao appUserDao) {
		this.appUserDao = appUserDao;
	}

	public AppUserGroupDao getAppUserGroupDao() {
		return appUserGroupDao;
	}

	public void setAppUserGroupDao(AppUserGroupDao appUserGroupDao) {
		this.appUserGroupDao = appUserGroupDao;
	}
}
