package com.jike.mobile.sna.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jike.mobile.sna.exception.InnerException;
import com.jike.mobile.sna.exception.OuterException;
import com.jike.mobile.sna.model.App;
import com.jike.mobile.sna.model.AppUser;
import com.jike.mobile.sna.service.AppUserService;

@Controller
@RequestMapping("appUser")
public class AppUserController extends BaseController{
	private Logger log = LoggerFactory.getLogger(AppUserController.class);
	
	@Autowired
	private AppUserService appUserService;
	
	@RequestMapping(value="{appId}/{appUserId}", method = RequestMethod.GET)
	public ModelAndView appUserGet(@PathVariable("appUserId")String appUserId, @PathVariable("appId")int appId, 
			HttpServletResponse response, Locale locale) {
		//validate
		if(appUserId == null || "".equals(appUserId) || appUserId.length() > 64) 
			return dealWithBadRequestHttp("error.appUser.userId", response);
		if(appId <= 0 ) 
			return dealWithBadRequestHttp("error.app.Id", response);
		
		try {
			AppUser appUser = appUserService.findAppUser(appUserId, appId);
			return new ModelAndView("appUser/get", "appUser", appUser);
		} catch(OuterException oe) {
			return dealWithBadRequestHttp(oe.getMessage(), response);
		} catch(InnerException ie) {
			return dealWithIntenalErrorsHttp(log, "error getting appUser", ie, response);
		}
	}
	
	@RequestMapping("")
	public ResponseEntity<byte[]> appUserPost(@RequestParam(value="idAndFriends")String idAndFriends, Locale locale) {
		//convert and validate
		ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
		try {
			readFriendsFromJson(idAndFriends, appUserList);
		} catch (Exception e) {
			return dealWithBadRequestJson("error.appUser.idAndFriends", locale);
		}
		
		try {
			getAppUserService().saveFriend(appUserList);
			return jsonToPage(stringToJson("msg", codeToString("operation.success", locale)), HttpStatus.OK);
		} catch (Exception e) {
			return dealWithIntenalErrorsJson(log, "error postting appUser", e, locale);
		}
	}
	
	@RequestMapping(value = "group")
	public ResponseEntity<byte[]> groupPost(@RequestParam(value="group") String group, Locale locale) {
		HashSet<AppUser> appUserSet = new HashSet<AppUser>();
		try {
			readGroupFromJson(group, appUserSet);
		} catch(Exception e) {
			return dealWithBadRequestJson("error.appUser.group", locale);
		}
		
		try {
			appUserService.saveGroup(appUserSet);
			return jsonToPage(stringToJson("msg", codeToString("operation.success", locale)), HttpStatus.OK);
		} catch (Exception e) {
			return dealWithIntenalErrorsJson(log, "error postting group", e, locale);
		}
	}

	/**
	 * 将请求的json数据转成AppUser List
	 * @param s
	 * @param appUserList
	 * @throws InnerException
	 */
	public void readFriendsFromJson(String s, List<AppUser> appUserList)
			throws InnerException {
		try {
			JSONArray array = JSONArray.fromObject(s);
			for (int i = 0; i < array.size(); i++) {
				JSONObject item = array.getJSONObject(i);

				Integer appId = Integer.parseInt(item.getString("appId"));
				if (appId == null) continue;
				App app = new App(appId);

				String userId = item.getString("userId");
				if (userId == null || "".equals(userId) || userId.length() > 64) continue;

				JSONArray friendArray = item.getJSONArray("friends");
				HashSet<AppUser> friendSet = new HashSet<AppUser>();
				for (int j = 0; j < friendArray.size(); j++) {
					String friendUserId = friendArray.getString(j);
					if (friendUserId == null || "".equals(friendUserId) || friendUserId.length() > 64) 	continue;
					AppUser appUser = new AppUser();
					appUser.setUserId(friendUserId);
					appUser.setApp(app);
					friendSet.add(appUser);
				}
				if (friendSet.size() == 0) continue;

				AppUser appUser = new AppUser();
				appUser.setUserId(userId);
				appUser.setApp(app);
				appUser.setFriends(friendSet);

				appUserList.add(appUser);
			}
		} catch (Exception e) {
			log.error("Friends JSON String convert failed! exception: "	+ e.toString());
			throw new InnerException("input.argu.is.invalid");
		}
		if (appUserList.size() == 0)
			throw new InnerException("input.argu.is.invalid");
	}
	
	/**
	 * 从输入数据中读取用户组
	 * @param s
	 * @param appUserSet
	 * @throws InnerException
	 */
	private void readGroupFromJson(String s, HashSet<AppUser> appUserSet) throws InnerException {
		try {
			JSONArray array = JSONArray.fromObject(s);
			for (int i = 0; i < array.size(); i++) {
				JSONObject item = array.getJSONObject(i);
				Integer appId = Integer.parseInt(item.getString("appId"));
				if (appId == null) 	continue;
				App app = new App(appId);

				String userId = item.getString("userId");
				if (userId == null || "".equals(userId) || userId.length() > 64) continue;
				AppUser appUser = new AppUser();
				appUser.setUserId(userId);
				appUser.setApp(app);

				appUserSet.add(appUser);
			}
		} catch (Exception e) {
			log.error("Group JSON String convert failed! exception: " + e.toString());
			throw new InnerException("input.argu.is.invalid");
		}
		if (appUserSet.size() <= 1)	throw new InnerException("input.argu.is.invalid");
		System.out.println(appUserSet);
	}

	public AppUserService getAppUserService() {
		return appUserService;
	}

	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
}
