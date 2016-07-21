package com.joosure.server.mvc.wechat.entity.domain;

public class BaseResult {

	protected String errCode;
	protected String errMsg;

	public BaseResult(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
