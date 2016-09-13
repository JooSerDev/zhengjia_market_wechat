package com.joosure.server.mvc.wechat.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.constant.CommonConstant;
import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.entity.domain.BaseResult;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.entity.pojo.UserWechatInfo;
import com.joosure.server.mvc.wechat.service.db.IUserDbService;
import com.joosure.server.mvc.wechat.service.itf.IScoreService;
import com.joosure.server.mvc.wechat.service.itf.ISystemFunctionService;
import com.joosure.server.mvc.wechat.service.itf.IUserService;
import com.shawn.server.core.util.EncryptUtil;
import com.shawn.server.core.util.StringUtil;
import com.shawn.server.wechat.common.web.AuthUtil;

@Service("userService")
public class UserService implements IUserService{
	@Autowired
	private ISystemFunctionService systemFunctionService;
	@Autowired
	private IScoreService scoreService;
	@Autowired
	private IUserDbService userDbService;

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
		User user = userDbService.getUserByMobile(mobile);
		if (user == null) {
			String openid = decodeEO(eo);
			user = userDbService.getUserByOpenid(openid);
			if (user != null) {
				BaseResult baseResult = systemFunctionService.validCheckCode(mobile, code);
				if (baseResult.getErrCode().equals("0")) {
					user.setMobile(mobile);
					user.setLastModifyTime(new Date());
					userDbService.updateUser(user);
					result.setErrCode("0");
					result.setErrMsg("注册成功");
					scoreService.updateScoreByEvent(user.getUserId(), CommonConstant.SCORE_EVENT_REGIST);
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
		User user = userDbService.getUserByOpenid(openid);
		UserWechatInfo userWechatInfo = userDbService.getUserWechatInfoByOpenid(openid);

		UserInfo userInfo = null;
		if (user != null && userWechatInfo != null) {
			userInfo = new UserInfo();
			userInfo.setUser(user);
			userInfo.setUserWechatInfo(userWechatInfo);
		}

		return userInfo;
	}

	/**
	 * 通过userId获得用户信息和用户微信信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfoById(int userId) {
		UserInfo userInfo = null;

		User user = userDbService.getUserById(userId);
		if (user != null) {
			UserWechatInfo userWechatInfo = userDbService.getUserWechatInfoByOpenid(user.getOpenid());
			if (userWechatInfo != null) {
				userInfo = new UserInfo();
				userInfo.setUser(user);
				userInfo.setUserWechatInfo(userWechatInfo);
			}
		}

		return userInfo;
	}

	/**
	 * 通过密文openid获得用户信息
	 * 
	 * @param encodeOpenid
	 * @return
	 * @throws OAuthException
	 * @throws Exception
	 */
	public UserInfo getUserInfoByEO(String encodeOpenid) throws OAuthException {
		String openid = null;
		try {
			openid = decodeEO(encodeOpenid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuthException();
		}
		User user = userDbService.getUserByOpenid(openid);
		UserWechatInfo userWechatInfo = userDbService.getUserWechatInfoByOpenid(openid);

		UserInfo userInfo = null;
		if (user != null && userWechatInfo != null) {
			userInfo = new UserInfo();
			userInfo.setUser(user);
			userInfo.setUserWechatInfo(userWechatInfo);
		}

		return userInfo;
	}

	/**
	 * 通过snsapi_base方式取得用户信息
	 * 
	 * @param request
	 * @return
	 * @throws OAuthException
	 */
	public UserInfo getUserInfoBySnsbase(HttpServletRequest request) throws OAuthException {
		String[] authParams = AuthUtil.getCodeAndState(request);
		String openid = AuthUtil.getOpenidByCodeInSnsbase(authParams[0]);
		UserInfo userInfo = getUserInfoByOpenid(openid);
		return userInfo;
	}

}
