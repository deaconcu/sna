package com.jike.mobile.sna.service.impl;

import java.util.List;

import com.jike.mobile.sna.dao.FeedbackDao;
import com.jike.mobile.sna.model.Feedback;
import com.jike.mobile.sna.service.FeedbackService;

public class FeedbackServiceImpl implements FeedbackService{
	FeedbackDao feedbackDao;

	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	@Override
	public List<Feedback> getList(int page, int size) {
		return feedbackDao.findByPage(page, size);
	}

	@Override
	public void add(Feedback instance) {
		instance.setPosttime(System.currentTimeMillis());
		feedbackDao.save(instance);
	}

	@Override
	public boolean isNull(Feedback instance) {
		if(instance.getTitle() == null || instance.getContent() == null
				|| instance.getTitle().trim().equals("")|| instance.getContent().trim().equals("")) return true;
		return false;
	}

	@Override
	public int getCount() {
		return 0;
	}
}
