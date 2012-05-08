package com.jike.mobile.sna.web;

import java.io.ByteArrayInputStream;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.jike.mobile.sna.exception.InnerException;
import com.jike.mobile.sna.exception.OuterException;
import com.jike.mobile.sna.model.UploadFile;
import com.jike.mobile.sna.service.AudioService;

@Controller
@RequestMapping(value="audio/")
public class AudioController extends BaseController{
	private Logger log = LoggerFactory.getLogger(AudioController.class);
	
	@Autowired
	private AudioService audioService;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView audioInput() {
		return new ModelAndView("audio/input");
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<byte[]> audioPost(@RequestParam("file") MultipartFile file, @RequestParam(value="md5") String md5, 
			HttpServletRequest request, Locale locale) {
		//validate
		if(file == null || file.isEmpty()) return dealWithBadRequestJson("error.audio.file", locale);
		if(md5 == null || "".equals(md5)) return dealWithBadRequestJson("error.audio.md5", locale);
		
		try {
			UploadFile uploadFile = new UploadFile(file);
			audioService.uploadWithFileBytesMd5(uploadFile, md5);
			return jsonToPage(stringToJson("playUrl", basePath(request) + "audio/player/" + uploadFile.getId()), HttpStatus.OK);
		} catch(OuterException e) {
			return dealWithBadRequestJson(e.getMessage(), locale);
		} catch(InnerException e) {
			return dealWithIntenalErrorsJson(log, "posting audio error", e, locale);
		}
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public void audioGet(@PathVariable(value="id") String id, HttpServletRequest request, HttpServletResponse response, Locale locale) {
		//validator
		if(id == null || (!id.matches("[a-f\\d]{8}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{4}-[a-f\\d]{12}"))) {
			forwordMessage("/message/error/", "error.audio.id", request, response, locale, log);
			return;
		}
			
		try {
			byte[] bytes = audioService.download(id);
			long length = bytes.length;
			writeStream(new ByteArrayInputStream(bytes), length, response, log);
			throw new InnerException();
		} catch(Exception e) {
			if(!response.isCommitted()) {
				forwordMessage("/message/error/", "error.audio.id", request, response, locale, log);
			} else return;
		}
	}
	
	@RequestMapping(value="player/{id}", method=RequestMethod.GET)
	public ModelAndView audioPlayerGet(@PathVariable(value="id") String id, HttpServletResponse response) {
		//validator
		if(id == null || (!id.matches("[a-f\\d]{8}_[a-f\\d]{4}_[a-f\\d]{4}_[a-f\\d]{4}_[a-f\\d]{12}")))
			dealWithBadRequestHttp("error.audio.id", response);
		
		return new ModelAndView("audio/player/get", "url", "audio/" + id);
	}

	public AudioService getAudioService() {
		return audioService;
	}

	public void setAudioService(AudioService audioService) {
		this.audioService = audioService;
	}

}
