package com.trgt.casestdy.ns.exception;

public class NotSentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotSentException() {
		super();
	}
	
	public NotSentException(String message){
		super(message);
	}
	
	public NotSentException(Throwable t){
		super(t);
	}

}
