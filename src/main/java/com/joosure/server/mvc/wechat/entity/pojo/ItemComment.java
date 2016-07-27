package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

public class ItemComment {

	private Integer fromUserId;
	private Integer toUserId;
	private Integer itemId;
	private String comment;
	private Integer state; // 0-正常 1-封号屏蔽 2-违禁删除
	private Date createTime;

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	/**
	 * 0-正常 1-封号屏蔽 2-违禁删除
	 * 
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

}
