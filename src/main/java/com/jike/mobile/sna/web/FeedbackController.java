package com.jike.mobile.sna.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jike.mobile.sna.model.Feedback;
import com.jike.mobile.sna.service.FeedbackService;

@Controller
@RequestMapping("feedback")
public class FeedbackController extends BaseController{
	
	@Autowired
	private FeedbackService feedbackService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String add_input() {
		return "feedback/input";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView add(Feedback feedback) {
		ModelAndView view = new ModelAndView(); 
		try{
			feedback.setCategoryid(1);
			feedback.setProjectid(1);
			feedbackService.add(feedback);
			
			view.addObject("succMsg", "feedback.add.success");
			view.setViewName("success");
			return view;
		} catch (RuntimeException re) {
			view.addObject("errMsg", "feedback.add.error");
			view.setViewName("error");
			return view;
		}
	}
	
	@RequestMapping("/list")
	public ModelAndView getList() {
		return null;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
}
