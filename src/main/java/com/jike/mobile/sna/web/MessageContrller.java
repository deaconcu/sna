package com.jike.mobile.sna.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("message")
public class MessageContrller extends BaseController {
	
	@RequestMapping("error/{msg}")
	public ModelAndView errorGet(@PathVariable("msg") String msg, Locale locale) {
		try {
			return new ModelAndView("error", "errMsg", URLDecoder.decode(msg, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			return new ModelAndView("error", "errMsg", msg);
		}
	}
}
