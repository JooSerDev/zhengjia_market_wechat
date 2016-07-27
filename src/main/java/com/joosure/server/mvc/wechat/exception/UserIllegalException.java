package com.joosure.server.mvc.wechat.exception;

public class UserIllegalException extends Exception {

	private static final long serialVersionUID = -6324856610423280361L;

	public UserIllegalException() {
		super("this user is illegal");
	}

	public UserIllegalException(String msg) {
		super(msg);
	}

}
