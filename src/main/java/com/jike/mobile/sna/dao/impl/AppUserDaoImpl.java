package com.jike.mobile.sna.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jike.mobile.sna.dao.AppUserDao;
import com.jike.mobile.sna.model.App;
import com.jike.mobile.sna.model.AppUser;

public class AppUserDaoImpl extends BaseDaoImpl<AppUser> implements AppUserDao {
	
	Logger log = LoggerFactory.getLogger(AppUserDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AppUser> findByUserIdAndApp(String userId, App app) {
		try {
			String queryString = "from AppUser as user where user.userId = ? and user.app = ?";
			return getHibernateTemplate().find(queryString, userId, app);
		} catch (RuntimeException re) {
			log.error("find by userId and app failed: " + re.toString());
			throw re;
		}
	}
}
