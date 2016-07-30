package com.joosure.server.mvc.wechat.entity.domain.page;

import com.joosure.server.mvc.wechat.entity.domain.ExchangeInfo;

public class AgreeExchangePageInfo extends BasePageInfo {

	private ExchangeInfo exchangeInfo;

	public ExchangeInfo getExchangeInfo() {
		return exchangeInfo;
	}

	public void setExchangeInfo(ExchangeInfo exchangeInfo) {
		this.exchangeInfo = exchangeInfo;
	}

}
