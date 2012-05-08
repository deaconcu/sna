package com.jike.mobile.sna.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public class BaseController {
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * 将json格式的字符串输出到页面
	 * @param s 字符串
	 * @param statueCode 指定的http状态 
	 */
	ResponseEntity<byte[]> jsonToPage(String s, HttpStatus statueCode) {
		try {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Type", "application/json; charset=utf-8");
			
			return new ResponseEntity<byte[]>(s.getBytes("utf-8"), responseHeaders, statueCode);			
		} catch(UnsupportedEncodingException uee) {
			return null;
		}
	}
	
	/**
	 * 将键值对转成json格式
	 * @param key 
	 * @param value
	 */
	String stringToJson(String key, String value) {
		JSONObject item = new JSONObject();
		item.put(key, value);
		return item.toString();
	}
	
	/**
	 * 当内部程序出错时的处理方法，输出为HTTP
	 * @param log 出错的controller类的logger
	 * @param logMsg 写入日志的描述性字符串
	 * @param e 错误
	 * @param response 当前请求的HttpServletResponse
	 */
	ModelAndView dealWithIntenalErrorsHttp(Logger log, String logMsg, Exception e, HttpServletResponse response) {
		log.error(logMsg + ": ", e);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ModelAndView("error", "errMsg", "error.server.internal");
	}
	
	/**
	 * 请求参数不正确时的处理方法，输出为HTTP
	 * @param errMsg
	 * @param response
	 */
	ModelAndView dealWithBadRequestHttp(String errMsg, HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ModelAndView("error", "errMsg", errMsg);
	}
	
	
	/**
	 * @param result
	 * @param response
	 */
	ModelAndView dealWithBadRequestHttp(BindingResult result, HttpServletResponse response) {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		return new ModelAndView("error", "errMsg", "error." + result.getFieldError().getObjectName() + "." + result.getFieldError().getField());
	}
	
	/**
	 * @param log
	 * @param logMsg
	 * @param e
	 * @param locale
	 */
	ResponseEntity<byte[]> dealWithIntenalErrorsJson(Logger log, String logMsg, Exception e, Locale locale) {
		log.error(logMsg + ": ", e);
		return jsonToPage(stringToJson("msg", messageSource.getMessage("error.server.internal", null, locale)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 根据code处理请求错误，返回json格式
	 * @param errMsg
	 * @param locale
	 */
	ResponseEntity<byte[]> dealWithBadRequestJson(String errMsg, Locale locale) {
		return jsonToPage(stringToJson("msg", messageSource.getMessage(errMsg, null, locale)), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 根据类型转换或者校验的结果处理请求错误，返回json格式
	 * @param result
	 * @param locale
	 */
	ResponseEntity<byte[]> dealWithBadRequestJson(BindingResult result, Locale locale) {
		return jsonToPage(stringToJson("msg", getMessageSource().getMessage(
				"error." + result.getFieldError().getObjectName() + "." + result.getFieldError().getField(), null, locale)), HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * 向外写出文件
	 * @param is
	 * @param length
	 * @param response
	 * @param log
	 */
	void writeStream(InputStream is, long length, HttpServletResponse response, Logger log) {
		response.setContentType("application/octet-stream");  
		response.setHeader("Content-disposition", "attachment; filename=audio");  
		response.setHeader("Content-Length", String.valueOf(length));
		
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
          
        try {
        	bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(response.getOutputStream());
		
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }
	        
        } catch (IOException e) {
        	log.error("writeStream error");
        } finally {
			try {
				bis.close();  
		        bos.close();
			} catch(IOException e) {
				log.info("file is not closed: " + e.toString());
			}
		}
	}
	
	/**
	 * 返回根路径
	 * @param request
	 * @return
	 */
	String basePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}
	
	/**
	 * 将请求转发到处理消息的路径
	 * @param url
	 * @param code
	 * @param request
	 * @param response
	 * @param locale
	 * @param log
	 */
	void forwordMessage(String url, String code, HttpServletRequest request, HttpServletResponse response, Locale locale, Logger log) {
		try {
			request.getRequestDispatcher(url + URLEncoder.encode((codeToString(code, locale)), "utf-8")).forward(request, response);
		} catch(Exception e) {
			dealWithIntenalErrorsHttp(log, "error forwording message", e, response);
		}
	}
	
	/**
	 * 根据code获取国际化资源信息
	 * @param code
	 * @param locale
	 */
	String codeToString(String code, Locale locale) {
		return messageSource.getMessage(code, null, code, locale);
	}
	
	//setter && getter

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}





















