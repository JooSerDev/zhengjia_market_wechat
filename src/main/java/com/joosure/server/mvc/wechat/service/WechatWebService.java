package com.joosure.server.mvc.wechat.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.jsapi.JsApiManager;
import org.sword.wechat4j.jsapi.JsApiParam;
import org.sword.wechat4j.oauth.OAuthException;
import org.sword.wechat4j.oauth.protocol.get_userinfo.GetUserinfoResponse;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.dao.database.UserDao;
import com.joosure.server.mvc.wechat.entity.domain.Redirecter;
import com.joosure.server.mvc.wechat.entity.domain.TableURLs;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.AddItemPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.HomePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MePageInfo;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.entity.pojo.UserWechatInfo;
import com.shawn.server.core.util.EncryptUtil;
import com.shawn.server.core.util.StringUtil;
import com.shawn.server.wechat.common.web.AuthUtil;

@Service
public class WechatWebService {

	@Autowired
	private ObjectStorageService objectStorageService;
	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;
	@Autowired
	private ItemDao itemDao;

	/**
	 * 多重定向，用于得到用户身份
	 * 
	 * @param request
	 * @return
	 * @throws OAuthException
	 * @throws MalformedURLException
	 * @throws UnsupportedEncodingException
	 */
	public Redirecter redirecter(HttpServletRequest request)
			throws OAuthException, MalformedURLException, UnsupportedEncodingException {
		Redirecter redirecter = new Redirecter();

		String redirectURL = request.getParameter("redirectURL");

		if (redirectURL == null || redirectURL.trim().equals("")) {
			throw new MalformedURLException("redirect url is cant read");
		}

		// redirectURL = URLDecoder.decode(redirectURL, "utf-8");

		UserInfo userInfo = createOrUpdateUserByWechatAuth(request);
		redirecter.setUserInfo(userInfo);

		try {
			String[] urls = redirectURL.split("\\?");
			if (urls.length > 1) {
				redirectURL = redirectURL + "&eo=" + userInfo.getEncodeOpenid();
			} else {
				redirectURL = redirectURL + "?eo=" + userInfo.getEncodeOpenid();
			}
		} catch (Exception e) {
			throw new OAuthException("0", e.getMessage());
		}
		redirecter.setRedirectURL(redirectURL);

		return redirecter;
	}

	/**
	 * 前往Home页面
	 * 
	 * @param request
	 * @throws OAuthException
	 */
	public HomePageInfo homePage(HttpServletRequest request) throws OAuthException {
		HomePageInfo homePageInfo = new HomePageInfo();

		UserInfo userInfo = createOrUpdateUserByWechatAuth(request);
		homePageInfo.setUserInfo(userInfo);

		return homePageInfo;
	}

	/**
	 * 前往Me页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public MePageInfo mePage(HttpServletRequest request) throws Exception {
		MePageInfo mePageInfo = null;
		try {
			String encodeOpenid = request.getParameter("eo");
			String decodeOpenid = EncryptUtil.decryptAES(encodeOpenid, WechatConstant.ENCODE_KEY_OPENID);
			String[] decodeOpenidInfo = decodeOpenid.split(";");
			if (decodeOpenidInfo.length == 2 && StringUtil.isNumber(decodeOpenidInfo[1])) {
				mePageInfo = new MePageInfo();

				String openid = decodeOpenidInfo[0];
				UserInfo userInfo = userService.getUserInfoByOpenid(openid);
				if (userInfo != null) {
					mePageInfo.setUserInfo(userInfo);
					mePageInfo.setTableURLs(buildTableURLs(request, userInfo.getEncodeOpenid()));
					String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
					JsApiParam jsApiParam = JsApiManager.signature(url);
					mePageInfo.setJsApiParam(jsApiParam);

				} else {
					throw new OAuthException();
				}

			} else {
				throw new OAuthException();
			}

		} catch (Exception e) {
			throw new Exception("WechatWebService.addItemPage decode encodeOpenid exception," + e.getMessage());
		}

		return mePageInfo;
	}

	/**
	 * 前往 addItem 页面
	 * 
	 * @param encodeOpenid
	 * @throws Exception
	 */
	public AddItemPageInfo addItemPage(HttpServletRequest request) throws Exception {
		AddItemPageInfo addItemPageInfo = null;
		try {
			String encodeOpenid = request.getParameter("eo");
			String decodeOpenid = EncryptUtil.decryptAES(encodeOpenid, WechatConstant.ENCODE_KEY_OPENID);
			String[] decodeOpenidInfo = decodeOpenid.split(";");
			if (decodeOpenidInfo.length == 2 && StringUtil.isNumber(decodeOpenidInfo[1])) {
				String openid = decodeOpenidInfo[0];
				UserInfo userInfo = userService.getUserInfoByOpenid(openid);
				if (userInfo != null) {
					String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
					addItemPageInfo = new AddItemPageInfo();
					JsApiParam jsApiParam = JsApiManager.signature(url);
					addItemPageInfo.setJsApiParam(jsApiParam);
					addItemPageInfo.setUserInfo(userInfo);
					addItemPageInfo.setItemTypes(itemDao.getItemTypes());
				} else {
					throw new OAuthException();
				}
			} else {
				throw new OAuthException();
			}

		} catch (Exception e) {
			throw new Exception("WechatWebService.addItemPage decode encodeOpenid exception," + e.getMessage());
		}
		return addItemPageInfo;
	}

	/**
	 * 生成table bar的链接
	 * 
	 * @param request
	 * @return
	 */
	private TableURLs buildTableURLs(HttpServletRequest request, String encodeOpenid) {
		TableURLs tableURLs = new TableURLs();

		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + WechatConstant.SCHEMA_MARKET + "/";

		// String homeURL = OAuthManager.generateRedirectURI(basePath + "home",
		// WechatConstant.SCOPE_SNSAPI_USERINFO, "");
		// String meURL = OAuthManager.generateRedirectURI(basePath + "me",
		// WechatConstant.SCOPE_SNSAPI_USERINFO, "");

		String homeURL = basePath + "home?eo=" + encodeOpenid;
		String meURL = basePath + "me?eo=" + encodeOpenid;

		tableURLs.setHomeUrl(homeURL);
		tableURLs.setMeUrl(meURL);

		return tableURLs;
	}

	/**
	 * 通过微信授权连接，添加或更新用户信息和用户微信信息
	 * 
	 * @param request
	 * @return
	 * @throws OAuthException
	 */
	private UserInfo createOrUpdateUserByWechatAuth(HttpServletRequest request) throws OAuthException {
		String[] authParams = AuthUtil.getCodeAndState(request);
		GetUserinfoResponse getUserinfoResponse = AuthUtil.getUserinfoByCodeInSnsinfo(authParams[0]);
		User user = userDao.getUserByOpenid(getUserinfoResponse.getOpenid());
		UserWechatInfo userWechatInfo = userDao.getUserWechatInfoByOpenid(getUserinfoResponse.getOpenid());

		// 若用户不存在（首次进入），则保存新用户。否则，若未自行修改昵称同时微信昵称修改了，则更新用户信息。
		if (user == null) {
			createUserByWechatInfo(getUserinfoResponse, request);
		} else {
			if (userWechatInfo != null && user.getNickname() != null
					&& user.getNickname().equals(userWechatInfo.getNickname())
					&& !user.getNickname().equals(getUserinfoResponse.getNickname())) {
				user.setNickname(getUserinfoResponse.getNickname());
				user.setLastModifyTime(new Date());
				userDao.updateUser(user);
			}
		}

		if (userWechatInfo == null) {
			userWechatInfo = new UserWechatInfo(getUserinfoResponse);
			Date nowDate = new Date();
			userWechatInfo.setCreateTime(nowDate);
			userWechatInfo.setLastUpdateTime(nowDate);
			userDao.saveUserWechatInfo(userWechatInfo);
		} else {
			userWechatInfo = new UserWechatInfo(getUserinfoResponse);
			Date nowDate = new Date();
			userWechatInfo.setLastUpdateTime(nowDate);
			userDao.updateUserWechatInfo(userWechatInfo);
		}
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(user);
		userInfo.setUserWechatInfo(userWechatInfo);

		return userInfo;
	}

	/**
	 * 通过微信用户基本信息，添加新用户
	 * 
	 * @param getUserinfoResponse
	 */
	private void createUserByWechatInfo(GetUserinfoResponse getUserinfoResponse, HttpServletRequest request) {
		User user = new User();

		String headImgUrl = null;
		if (getUserinfoResponse.getHeadimgurl() != null && !getUserinfoResponse.getHeadimgurl().trim().equals("")) {
			headImgUrl = objectStorageService.putHeadImg(getUserinfoResponse.getHeadimgurl(), request);
		}

		user.setHeadImgUrl(headImgUrl);
		user.setNickname(getUserinfoResponse.getNickname());
		user.setOpenid(getUserinfoResponse.getOpenid());
		user.setState(0);

		Date nowDate = new Date();
		user.setCreateTime(nowDate);
		user.setLastModifyTime(nowDate);

		userDao.saveUser(user);

	}

}
