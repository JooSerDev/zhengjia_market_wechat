package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.domain.ExchangeInfo;

public class MyExchangesPageInfo extends BasePageInfo {

	private List<ExchangeInfo> exchanges;

	public List<ExchangeInfo> getExchanges() {
		return exchanges;
	}

	public void setExchanges(List<ExchangeInfo> exchanges) {
		this.exchanges = exchanges;
	}

}
