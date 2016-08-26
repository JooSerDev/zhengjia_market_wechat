package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

/**
 * 用户 积分
 * @author Ted
 *
 */
public class Score {
	private int oId;
	private String eventKey;
	private int userId;
	private int score;
	private Date addTime;
	private String extdInfo;
	public String getExtdInfo() {
		return extdInfo;
	}
	public void setExtdInfo(String extdInfo) {
		this.extdInfo = extdInfo;
	}
	private String memo;
	public int getoId() {
		return oId;
	}
	public void setoId(int oId) {
		this.oId = oId;
	}
	public String getEventKey() {
		return eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
