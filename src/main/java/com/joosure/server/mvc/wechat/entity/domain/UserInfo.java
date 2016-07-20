package com.joosure.server.mvc.wechat.entity.domain;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.entity.pojo.UserWechatInfo;
import com.shawn.server.core.util.EncryptUtil;

public class UserInfo {

	private User user;
	private UserWechatInfo userWechatInfo;

	/**
	 * 是否注册（填写手机号码）
	 * 
	 * @return
	 */
	public boolean isRegisteredMobile() {
		if (user != null && user.getMobile() != null) {
			return true;
		}
		return false;
	}

	public String getEncodeOpenid() throws Exception {
		String openid = null;
		String encodeOpenid = null;
		if (user != null && user.getOpenid() != null && !user.getOpenid().equals("")) {
			openid = user.getOpenid();
		} else if (userWechatInfo != null) {
			openid = userWechatInfo.getOpenid();
		}

		if (openid != null) {
			try {
				encodeOpenid = EncryptUtil.encryptAES(openid + ";" + System.currentTimeMillis(),
						WechatConstant.ENCODE_KEY_OPENID);
			} catch (Exception e) {
				throw new Exception("UserInfo.getEncodeOpenid can not encrypt openid");
			}
		}

		return encodeOpenid;

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserWechatInfo getUserWechatInfo() {
		return userWechatInfo;
	}

	public void setUserWechatInfo(UserWechatInfo userWechatInfo) {
		this.userWechatInfo = userWechatInfo;
	}

}
