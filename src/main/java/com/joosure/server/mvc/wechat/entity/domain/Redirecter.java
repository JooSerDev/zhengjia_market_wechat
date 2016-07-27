package com.joosure.server.mvc.wechat.entity.domain;

public class Redirecter {

	private UserInfo userInfo;
	private String redirectURL;

	public boolean isValid() {
		if (userInfo != null && userInfo.getUser() != null && userInfo.getUserWechatInfo() != null
				&& redirectURL != null) {
			return true;
		} else {
			System.out.println("Redirecter ###########");
			System.out.println("userInfo :: " + (userInfo != null));
			if (userInfo != null) {
				System.out.println("userInfo.getUser :: " + (userInfo.getUser() != null));
				System.out.println("userInfo.getUserWechatInfo() :: " + (userInfo.getUserWechatInfo() != null));
			}
			System.out.println("redirectURL :: " + (redirectURL != null));
			System.out.println("Redirecter ###########");
			return false;
		}

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
