package com.joosure.server.mvc.wechat.exception;

public class ItemIllegalException extends Exception {

	private static final long serialVersionUID = 1754514687103024049L;

	public ItemIllegalException() {
		super("this item is illegal");
	}

	public ItemIllegalException(String msg) {
		super(msg);
	}

}
