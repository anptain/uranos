package com.sanyin.uranos.exception;

import org.springframework.security.core.AuthenticationException;

public class BadCaptchaException extends AuthenticationException {
	
	private static final long serialVersionUID = -4803009301738858197L;
	
	public BadCaptchaException(String msg) {
		super(msg);
	}
	
	public BadCaptchaException(String msg, Throwable t) {
		super(msg, t);
	}
}