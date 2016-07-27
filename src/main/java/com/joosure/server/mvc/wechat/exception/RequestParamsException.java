package com.joosure.server.mvc.wechat.exception;

public class RequestParamsException extends Exception {

	private static final long serialVersionUID = -8519712510062590427L;

	public RequestParamsException() {
		super("request params illegal");
	}

	public RequestParamsException(String msg) {
		super(msg);
	}
}
