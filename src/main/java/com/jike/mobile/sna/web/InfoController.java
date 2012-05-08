package com.jike.mobile.sna.web;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.jike.mobile.sna.model.Info;
import com.jike.mobile.sna.util.ServerConfig;

@Controller
@RequestMapping("info")
public class InfoController extends BaseController{
	Logger log = LoggerFactory.getLogger(InfoController.class);
	
	@RequestMapping("andriod")
	public ResponseEntity<byte[]> infoAndriodget(Locale locale) {
		Info infoAndroid = new Info();
		try {
			infoAndroid.setVersion(ServerConfig.get("androidVersion"));
			infoAndroid.setVersionCode(ServerConfig.get("androidVersionCode"));
			infoAndroid.setDownloadUrl(ServerConfig.get("androidDownloadUrl"));
			infoAndroid.setVersionDesc(ServerConfig.get("androidVersionDesc"));
			
			return jsonToPage(infoAndroid.toJson(), HttpStatus.OK);
		} catch(Exception e) {
			return dealWithIntenalErrorsJson(log, "info andriod get failed", e, locale);
		}
	}
}
