package com.joosure.server.mvc.wechat.entity.domain.page;

import com.joosure.server.mvc.wechat.entity.pojo.User;

public class FinalAgreeExchangePageInfo extends BasePageInfo {

	private User changer;

	public User getChanger() {
		return changer;
	}

	public void setChanger(User changer) {
		this.changer = changer;
	}

}
