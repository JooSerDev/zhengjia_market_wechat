package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.User;

public class ExchangePageInfo extends BasePageInfo {

	private User targetUser;
	private Item targetItem;
	private List<Item> items;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item getTargetItem() {
		return targetItem;
	}

	public void setTargetItem(Item targetItem) {
		this.targetItem = targetItem;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

}
