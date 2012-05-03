package com.jike.mobile.sna.dao;

import java.util.List;
import java.util.Set;

import com.jike.mobile.sna.model.AppUser;
import com.jike.mobile.sna .model.AppUserGroup;

public interface AppUserGroupDao extends BaseDao<AppUserGroup>{
	List<AppUserGroup> findByUserIds(Set<AppUser> userIds);
}
