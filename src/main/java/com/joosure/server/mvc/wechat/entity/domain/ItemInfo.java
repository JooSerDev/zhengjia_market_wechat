package com.joosure.server.mvc.wechat.entity.domain;

import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.User;

public class ItemInfo {

	private Item item;
	private User ownerInfo;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(User ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

}
