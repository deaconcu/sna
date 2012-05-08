package com.jike.mobile.sna.exception;

/**
 * service
 *
 */
public class InnerException extends Exception {

	private static final long serialVersionUID = -825403381542611137L;
	
	public InnerException() {
		super();
	}
	
	public InnerException(String msg) {
		super(msg);
	}
	
	public InnerException(Throwable t) {
		super(t);
	}
	
	public InnerException(String msg, Throwable t) {
		super(msg, t);
	}
}
