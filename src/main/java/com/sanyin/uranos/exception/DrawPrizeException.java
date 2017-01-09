package com.sanyin.uranos.exception;

public class DrawPrizeException extends RuntimeException{

	private static final long serialVersionUID = 8503383545660858850L;

	public DrawPrizeException(String msg) {
		super(msg);
	}
	
	public DrawPrizeException(String msg, Throwable t) {
		super(msg, t);
	}
}
