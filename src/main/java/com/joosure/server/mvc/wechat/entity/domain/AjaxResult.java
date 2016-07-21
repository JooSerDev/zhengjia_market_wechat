package com.joosure.server.mvc.wechat.entity.domain;

import java.util.HashMap;
import java.util.Map;

public class AjaxResult extends BaseResult {

	public AjaxResult() {
		super("0");
	}

	private Map<String, Object> data = new HashMap<>();

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
