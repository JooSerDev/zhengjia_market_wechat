package com.joosure.server.mvc.wechat.entity.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Exchange {

	public static final String STATUS_YES = "yes";
	public static final String STATUS_NO = "no";
	public static final String STATUS_WAIT = "wait";

	public static final String EXCHANGE_STATE_ING = "exchanging";
	public static final String EXCHANGE_STATE_ED = "exchanged";
	public static final String EXCHANGE_STATE_CANCEL = "cancel";

	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm");

	private Integer exchangeId;

	private Integer ownerItemId;
	private String ownerItemName;
	private Integer ownerId;

	private Integer changerItemId;
	private String changerItemName;
	private Integer changerId;

	private Integer state; // 0-normal 1-offline
	private String exchangeFinishStatus;// 正佳人员见证完成交换状态
	private String exchangeState; // 交易情况

	private Date createTime;
	private Date exchangeTime; // 同意交换时间
	private Date exchangeFinishTime; // 正佳人员认证时间

	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Integer getOwnerItemId() {
		return ownerItemId;
	}

	public void setOwnerItemId(Integer ownerItemId) {
		this.ownerItemId = ownerItemId;
	}

	public String getOwnerItemName() {
		return ownerItemName;
	}

	public void setOwnerItemName(String ownerItemName) {
		this.ownerItemName = ownerItemName;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getChangerItemId() {
		return changerItemId;
	}

	public void setChangerItemId(Integer changerItemId) {
		this.changerItemId = changerItemId;
	}

	public String getChangerItemName() {
		return changerItemName;
	}

	public void setChangerItemName(String changerItemName) {
		this.changerItemName = changerItemName;
	}

	public Integer getChangerId() {
		return changerId;
	}

	public void setChangerId(Integer changerId) {
		this.changerId = changerId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getExchangeFinishStatus() {
		return exchangeFinishStatus;
	}

	public void setExchangeFinishStatus(String exchangeFinishStatus) {
		this.exchangeFinishStatus = exchangeFinishStatus;
	}

	public String getExchangeState() {
		return exchangeState;
	}

	public void setExchangeState(String exchangeState) {
		this.exchangeState = exchangeState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public Date getExchangeFinishTime() {
		return exchangeFinishTime;
	}

	public void setExchangeFinishTime(Date exchangeFinishTime) {
		this.exchangeFinishTime = exchangeFinishTime;
	}

	public String getDisplayTime() {
		if (createTime != null) {
			return DATE_FORMAT.format(createTime);
		}
		return "";
	}

}
