package com.jike.mobile.sna.dao;

import java.util.List;

import com.jike.mobile.sna.model.App;
import com.jike.mobile.sna.model.AppUser;

public interface AppUserDao extends BaseDao<AppUser>{

	List<AppUser> findByUserIdAndApp(String userId, App app);
	
}
