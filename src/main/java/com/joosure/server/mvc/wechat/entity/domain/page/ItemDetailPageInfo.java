package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;

public class ItemDetailPageInfo extends BasePageInfo {

	private Item item;
	private UserInfo ownerInfo;
	private List<ItemComment> comments;
	private String toExchangeUrl;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public UserInfo getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(UserInfo ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	public List<ItemComment> getComments() {
		return comments;
	}

	public void setComments(List<ItemComment> comments) {
		this.comments = comments;
	}

	public String getToExchangeUrl() {
		return toExchangeUrl;
	}

	public void setToExchangeUrl(String toExchangeUrl) {
		this.toExchangeUrl = toExchangeUrl;
	}

}
