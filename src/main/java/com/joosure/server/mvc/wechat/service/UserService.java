package com.joosure.server.mvc.wechat.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.dao.database.UserDao;
import com.joosure.server.mvc.wechat.entity.domain.BaseResult;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.entity.pojo.UserWechatInfo;
import com.shawn.server.core.util.EncryptUtil;
import com.shawn.server.core.util.StringUtil;

@Service
public class UserService {
	@Autowired
	private SystemFunctionService systemFunctionService;

	@Autowired
	private UserDao userDao;

	/**
	 * 注册
	 * 
	 * @param mobile
	 * @param code
	 * @param eo
	 * @return
	 * @throws Exception
	 */
	public BaseResult register(String mobile, String code, String eo) throws Exception {
		BaseResult result = new BaseResult("1001");
		User user = getUserByMobile(mobile);
		if (user == null) {
			String openid = decodeEO(eo);
			user = userDao.getUserByOpenid(openid);
			if (user != null) {
				BaseResult baseResult = systemFunctionService.validCheckCode(mobile, code);
				if (baseResult.getErrCode().equals("0")) {
					user.setMobile(mobile);
					user.setLastModifyTime(new Date());
					userDao.updateUser(user);
					result.setErrCode("0");
					result.setErrMsg("注册成功");
				} else {
					result = baseResult;
				}
			} else {
				result.setErrCode("2002");
				result.setErrMsg("用户鉴权失败");
			}
		} else {
			result.setErrCode("2001");
			result.setErrMsg("手机号码已经注册");
		}
		return result;
	}

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	public User getUserByMobile(String mobile) {
		return userDao.getUserByMobile(mobile);
	}

	/**
	 * 
	 * @param encodeOpenid
	 * @return
	 * @throws Exception
	 */
	public String decodeEO(String encodeOpenid) throws Exception {
		String decodeOpenid = EncryptUtil.decryptAES(encodeOpenid, WechatConstant.ENCODE_KEY_OPENID);
		String[] decodeOpenidInfo = decodeOpenid.split(";");
		if (decodeOpenidInfo.length == 2 && StringUtil.isNumber(decodeOpenidInfo[1])) {
			String openid = decodeOpenidInfo[0];
			return openid;
		} else {
			throw new OAuthException();
		}
	}

	/**
	 * 通过openid获得用户信息和用户微信信息
	 * 
	 * @param openid
	 * @return
	 */
	public UserInfo getUserInfoByOpenid(String openid) {
		User user = userDao.getUserByOpenid(openid);
		UserWechatInfo userWechatInfo = userDao.getUserWechatInfoByOpenid(openid);

		UserInfo userInfo = null;
		if (user != null && userWechatInfo != null) {
			userInfo = new UserInfo();
			userInfo.setUser(user);
			userInfo.setUserWechatInfo(userWechatInfo);
		}

		return userInfo;
	}

	/**
	 * 通过密文openid获得用户信息
	 * 
	 * @param encodeOpenid
	 * @return
	 * @throws Exception
	 */
	public UserInfo getUserInfoByEO(String encodeOpenid) throws Exception {
		String openid = decodeEO(encodeOpenid);
		User user = userDao.getUserByOpenid(openid);
		UserWechatInfo userWechatInfo = userDao.getUserWechatInfoByOpenid(openid);

		UserInfo userInfo = null;
		if (user != null && userWechatInfo != null) {
			userInfo = new UserInfo();
			userInfo.setUser(user);
			userInfo.setUserWechatInfo(userWechatInfo);
		}

		return userInfo;
	}

}
