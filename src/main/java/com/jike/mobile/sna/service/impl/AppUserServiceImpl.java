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
import com.jike.mobile.sna.exception.InnerException;
import com.jike.mobile.sna.exception.OuterException;
import com.jike.mobile.sna.model.App;
import com.jike.mobile.sna.model.AppUser;
import com.jike.mobile.sna.model.AppUserGroup;
import com.jike.mobile.sna.service.AppUserService;

public class AppUserServiceImpl implements AppUserService {
	
	Logger log = LoggerFactory.getLogger(AppUserServiceImpl.class);

	private AppDao appDao;
	private AppUserDao appUserDao;
	private AppUserGroupDao appUserGroupDao;
	
	@Override
	public void saveFriend(ArrayList<AppUser> appUserList) throws InnerException {
		try {
			//循环取appUser
			for(AppUser appUser : appUserList ) {
				App app = appDao.findById(appUser.getApp().getId());
				//跳过app不存在的appUser
				if(app == null) continue;
	
				Set<AppUser> friends = appUser.getFriends();
	
				Iterator<AppUser> iterator = friends.iterator();
				List<AppUser> friendsPersisted = new ArrayList<AppUser>();
				//循环取朋友名
				while(iterator.hasNext()) {
					AppUser friend = iterator.next();
					//查找朋友是否已存在数据库
					List<AppUser> list = appUserDao.findByUserIdAndApp(friend.getUserId(),app);
	
					//保存不存在的朋友，若存在，删除列表中的脱管对象，插入持久态对象
					if(list.size() == 0) {
						friend.setCreateTime(System.currentTimeMillis());
						appUserDao.save(friend);
					} else {
						friendsPersisted.add(list.get(0));
						iterator.remove();
					}
				}
				friends.addAll(friendsPersisted);
	
				//如果appUser不存在，保存；若存在，更新朋友列表
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
			log.error("error saving friend: " + re.toString());
			throw new InnerException();
		}
	}

	@Override
	public void saveGroup(HashSet<AppUser> appUserSet) throws InnerException {
		Set<AppUser> contain = new HashSet<AppUser>();
		TreeMap<Integer, TreeSet<String>> map = new TreeMap<Integer, TreeSet<String>>();
		try {
			//循环取appUser，插入不存在的appUser，然后将组插入TreeMap（为了排序），
			//TreeMap按key(appid)排序，value(appUserIds:可能一个应用同时等两个号)按自然排序
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
			
			//如果这个组大于1个用户，通过containString查看之前有无相同的组，没有就存入
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
			log.error("error saving group: " + re.toString());
			throw new InnerException();
		}
	}

	@Override
	public AppUser findAppUser(String userId, Integer appId) throws InnerException, OuterException {
		try {
			App app = appDao.findById(appId);
			if(app == null) throw new OuterException("error.app.not.exist");
			
			List<AppUser> list = appUserDao.findByUserIdAndApp(userId, app);
			if(list.size() == 0) throw new OuterException("error.appUser.not.exist");
			
			return list.get(0);
		} catch (RuntimeException re) {
			log.error("runtimeException: " + re.toString());
			throw new InnerException("system.internal.error");
		}
	}

	//private

	
	/**
	 * 按下面方式存数据
	 * map: 
	 * appidA : userIds(TreeSet)
	 * appidB : userIds(TreeSet)
	 * @param map
	 * @param appUser
	 */
	private void put(TreeMap<Integer, TreeSet<String>> map, AppUser appUser) {
		Integer appId = appUser.getApp().getId();
		String userId = appUser.getUserId();
		TreeSet<String> set = map.get(appId);
		if(set == null) {
			set = new TreeSet<String>();
			set.add(userId);
			map.put(appId, set);
		} else {
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
