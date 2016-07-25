package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.Item;

public class MyItemsPageInfo extends BasePageInfo {

	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
