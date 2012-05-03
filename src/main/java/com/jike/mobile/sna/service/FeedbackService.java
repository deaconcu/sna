package com.jike.mobile.sna.service;

import java.util.List; 

import com.jike.mobile.sna.model.Feedback;

public interface FeedbackService {
	public List<Feedback> getList(int page, int size);
	
	public void add(Feedback instance);
	
	public boolean isNull(Feedback instance);
	
	public int getCount();
}
