package com.joosure.server.mvc.wechat.entity.domain;

public class Redirecter {

	private UserInfo userInfo;
	private String redirectURL;

	public boolean isValid() {
		if (userInfo != null && userInfo.getUser() != null && userInfo.getUserWechatInfo() != null
				&& redirectURL != null)
			return true;
		return false;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

}
