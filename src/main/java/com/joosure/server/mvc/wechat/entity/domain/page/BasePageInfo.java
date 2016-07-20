package com.joosure.server.mvc.wechat.entity.domain.page;

import org.sword.wechat4j.jsapi.JsApiParam;

import com.joosure.server.mvc.wechat.entity.domain.UserInfo;

public class BasePageInfo {

	protected UserInfo userInfo;
	protected JsApiParam jsApiParam;

	public JsApiParam getJsApiParam() {
		return jsApiParam;
	}

	public void setJsApiParam(JsApiParam jsApiParam) {
		this.jsApiParam = jsApiParam;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
