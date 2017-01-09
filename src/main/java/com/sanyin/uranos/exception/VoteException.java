package com.sanyin.uranos.exception;

public class VoteException extends RuntimeException{

	private static final long serialVersionUID = 8503383545660858850L;

	public VoteException(String msg) {
		super(msg);
	}
	
	public VoteException(String msg, Throwable t) {
		super(msg, t);
	}
}
