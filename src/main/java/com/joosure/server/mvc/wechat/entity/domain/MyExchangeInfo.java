package com.joosure.server.mvc.wechat.entity.domain;

import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.User;

public class MyExchangeInfo {

	private Exchange exchange;
	private User user;
	private User target;
	private Item userItem;
	private Item targetItem;

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getTarget() {
		return target;
	}

	public void setTarget(User target) {
		this.target = target;
	}

	public Item getUserItem() {
		return userItem;
	}

	public void setUserItem(Item userItem) {
		this.userItem = userItem;
	}

	public Item getTargetItem() {
		return targetItem;
	}

	public void setTargetItem(Item targetItem) {
		this.targetItem = targetItem;
	}

}
