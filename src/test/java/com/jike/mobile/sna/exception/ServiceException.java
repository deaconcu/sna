package com.jike.mobile.sna.exception;

/**
 * service层错误
 *
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -825403381542611137L;
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String msg) {
		super(msg);
	}
	
	public ServiceException(Throwable t) {
		super(t);
	}
	
	public ServiceException(String msg, Throwable t) {
		super(msg, t);
	}
}
