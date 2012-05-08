package com.jike.mobile.sna.exception;

public class OuterException extends Exception{
	private static final long serialVersionUID = 3845815579702273432L;

	public OuterException() {
		super();
	}
	
	public OuterException(String msg) {
		super(msg);
	}
	
	public OuterException(Throwable t) {
		super(t);
	}
	
	public OuterException(String msg, Throwable t) {
		super(msg, t);
	}
}
