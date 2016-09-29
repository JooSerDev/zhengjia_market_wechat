package com.joosure.server.mvc.wechat.entity.itf;

import java.io.Serializable;

public class ItfBaseInfo implements Serializable{
	private static final long serialVersionUID = 4805164593457603626L;
	
	private String retCode;
	private String retMsg;
	private Object data;
	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
