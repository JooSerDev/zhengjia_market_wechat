package com.joosure.server.mvc.wechat.entity.domain;

import java.util.Date;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.shawn.server.core.util.EncryptUtil;

public class MyExchangeInfo {

	private Exchange exchange;
	private User user;
	private User target;
	private Item userItem;
	private Item targetItem;
	private String toAgreeExchangePath;
	private Boolean isOwner;

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

	public String getToAgreeExchangePath() {
		return toAgreeExchangePath;
	}

	public void setToAgreeExchangePath(String toAgreeExchangePath) {
		this.toAgreeExchangePath = toAgreeExchangePath;
	}

	public String getEncodeExchange() throws Exception {
		String ee = "";

		if (isOwner && exchange != null && user != null && target != null && userItem != null && targetItem != null) {
			String decodeStr = user.getOpenid() + ";" + target.getOpenid() + ";" + userItem.getItemId() + ";"
					+ targetItem.getItemId() + ";" + exchange.getExchangeId() + ";" + new Date().getTime();
			try {
				ee = EncryptUtil.encryptAES(decodeStr, WechatConstant.ENCODE_KEY_OPENID);
			} catch (Exception e) {
				throw new Exception("ExchangeInfo.getEncodeExchange can not encrypt Exchange");
			}
		}

		return ee;
	}

	public Boolean getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
	}

}
