package com.jike.mobile.sna.web;

import java.util.List; 
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jike.mobile.sna.model.Feedback;
import com.jike.mobile.sna.model.validate.Input;
import com.jike.mobile.sna.service.FeedbackService;
import com.jike.mobile.sna.util.ServerConfig;

@Controller
@RequestMapping("feedback")
public class FeedbackController extends BaseController{
	Logger log = LoggerFactory.getLogger(FeedbackController.class);
	
	@Autowired
	private FeedbackService feedbackService;
	
	//feedback input
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView add_input() {
		return new ModelAndView("feedback/input");
	}
	
	//feedback post
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<byte[]> feedbackAdd(@Validated(Input.class) Feedback feedback, BindingResult result, HttpServletResponse response, Locale locale) {
		if(result.hasErrors()) return dealWithBadRequestJson(result, locale);
		try {
			feedbackService.add(feedback);
			return jsonToPage("", HttpStatus.OK);
		} catch (Exception e) {
			return dealWithIntenalErrorsJson(log, "error putting feedback", e, locale);
		}
	}

	//feedback list get
	@RequestMapping("/list/{page}")
	public ModelAndView feedbackListGet(@ModelAttribute @PathVariable("page")int page, BindingResult result, HttpServletResponse response) {
		if(result.hasErrors() || page < 1) page = 1;
		try {
			int size = ServerConfig.getInteger("feedback_page_size");
			List<Feedback> list = feedbackService.getList(page, size);
			return new ModelAndView("feedback/list/get", "list", list);
		} catch (Exception e) {
			return dealWithIntenalErrorsHttp(log, "error getting feedback list", e, response);
		}
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
}
