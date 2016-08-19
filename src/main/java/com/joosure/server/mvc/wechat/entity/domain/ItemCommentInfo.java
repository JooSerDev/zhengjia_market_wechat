package com.joosure.server.mvc.wechat.entity.domain;

import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;
import com.joosure.server.mvc.wechat.entity.pojo.User;

public class ItemCommentInfo {

	private ItemComment comment;
	private User user;

	public ItemComment getComment() {
		return comment;
	}

	public void setComment(ItemComment comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
