package com.sanyin.uranos.web.controller;

import java.io.Serializable;

public class AjaxResponse implements Serializable {

	private static final long serialVersionUID = -773058344437465211L;

	private final static int CODE_SUCCESS = 0;

	private final static int CODE_EXCEPTION = 1;

	private final static int CODE_ERROR = -1;

	public static AjaxResponse createSuccessResp() {
		return createSuccessResp(null);
	}
	public static AjaxResponse createSuccessResp(Object content) {
		return new AjaxResponse(CODE_SUCCESS, content);
	}
	public static AjaxResponse createExceptionResp() {
		return createExceptionResp(null);
	}
	public static AjaxResponse createExceptionResp(String content) {
		return new AjaxResponse(CODE_EXCEPTION, content);
	}
	public static AjaxResponse createErrorResp() {
		return new AjaxResponse(CODE_ERROR, null);
	}

	private int code;
	private Object content;

	public AjaxResponse(int code, Object content) {
		super();
		this.code = code;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
