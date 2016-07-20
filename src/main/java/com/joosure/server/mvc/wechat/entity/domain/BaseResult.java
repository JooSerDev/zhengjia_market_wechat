package com.joosure.server.mvc.wechat.entity.domain;

public class BaseResult {

	private Integer errCode;
	private String errMsg;

	public BaseResult(Integer errCode) {
		this.errCode = errCode;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
