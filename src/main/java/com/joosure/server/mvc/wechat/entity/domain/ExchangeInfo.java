package com.joosure.server.mvc.wechat.entity.domain;

import java.util.Date;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.shawn.server.core.util.EncryptUtil;

public class ExchangeInfo {

	private Exchange exchange;
	private UserInfo owner;
	private UserInfo changer;
	private Item ownerItem;
	private Item changerItem;
	private String toAgreeExchangePath;

	public Exchange getExchange() {
		return exchange;
	}

	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}

	public UserInfo getOwner() {
		return owner;
	}

	public void setOwner(UserInfo owner) {
		this.owner = owner;
	}

	public UserInfo getChanger() {
		return changer;
	}

	public void setChanger(UserInfo changer) {
		this.changer = changer;
	}

	public Item getOwnerItem() {
		return ownerItem;
	}

	public void setOwnerItem(Item ownerItem) {
		this.ownerItem = ownerItem;
	}

	public Item getChangerItem() {
		return changerItem;
	}

	public void setChangerItem(Item changerItem) {
		this.changerItem = changerItem;
	}

	public String getToAgreeExchangePath() {
		return toAgreeExchangePath;
	}

	public void setToAgreeExchangePath(String toAgreeExchangePath) {
		this.toAgreeExchangePath = toAgreeExchangePath;
	}

	public String getEncodeExchange() throws Exception {
		String ee = "";

		if (exchange != null && owner != null && changer != null && ownerItem != null && changerItem != null) {
			String decodeStr = owner.getUser().getOpenid() + ";" + changer.getUser().getOpenid() + ";"
					+ ownerItem.getItemId() + ";" + changerItem.getItemId() + ";" + exchange.getExchangeId() + ";"
					+ new Date().getTime();
			try {
				ee = EncryptUtil.encryptAES(decodeStr, WechatConstant.ENCODE_KEY_OPENID);
			} catch (Exception e) {
				throw new Exception("ExchangeInfo.getEncodeExchange can not encrypt Exchange");
			}
		}

		return ee;
	}

}
