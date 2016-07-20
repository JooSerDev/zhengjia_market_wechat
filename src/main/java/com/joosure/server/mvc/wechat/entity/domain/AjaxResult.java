package com.joosure.server.mvc.wechat.entity.domain;

import java.util.HashMap;
import java.util.Map;

public class AjaxResult {

	private String errCode;
	private String errMsg;
	private Map<String, Object> data = new HashMap<>();

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

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void putData(String name, Object obj) {
		data.put(name, obj);
	}

}
