package com.joosure.server.mvc.wechat.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.jsapi.JsApiManager;
import org.sword.wechat4j.jsapi.JsApiParam;
import org.sword.wechat4j.oauth.OAuthException;
import org.sword.wechat4j.oauth.OAuthManager;
import org.sword.wechat4j.oauth.protocol.get_userinfo.GetUserinfoResponse;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.dao.cache.ItemCache;
import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.dao.database.UserDao;
import com.joosure.server.mvc.wechat.entity.domain.ExchangeInfo;
import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.Pages;
import com.joosure.server.mvc.wechat.entity.domain.Redirecter;
import com.joosure.server.mvc.wechat.entity.domain.TableURLs;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.domain.WxShareParam;
import com.joosure.server.mvc.wechat.entity.domain.page.AddItemPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.AgreeExchangePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.BasePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.ExchangePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.HomePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.ItemDetailPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.ItemsPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MyExchangesPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MyItemsPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.TaPageInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.entity.pojo.UserWechatInfo;
import com.joosure.server.mvc.wechat.exception.ItemIllegalException;
import com.joosure.server.mvc.wechat.exception.RequestParamsException;
import com.joosure.server.mvc.wechat.exception.UserIllegalException;
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
	private ItemService itemService;

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
		String encodeOpenid = request.getParameter("eo");
		UserInfo userInfo = userService.getUserInfoByEO(encodeOpenid);
		if (userInfo == null) {
			throw new OAuthException();
		}

		homePageInfo.setUserInfo(userInfo);
		try {
			homePageInfo.setTableURLs(buildTableURLs(request, userInfo.getEncodeOpenid()));
		} catch (Exception e) {
			throw new OAuthException();
		}
		String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
		JsApiParam jsApiParam = JsApiManager.signature(url);
		homePageInfo.setJsApiParam(jsApiParam);

		List<User> userTop8 = userService.getUsersOrderByItemNumTop8();
		homePageInfo.setTop8User(userTop8);

		List<ItemInfo> itemTop15 = itemService.getItemsRecommended();
		homePageInfo.setTop15Item(itemTop15);

		List<ItemType> itemTypeHot = itemService.getHotItemType();
		homePageInfo.setTop8Type(itemTypeHot);

		return homePageInfo;
	}

	public TaPageInfo taPage(HttpServletRequest request, String encodeOpenid, String openid)
			throws RequestParamsException, OAuthException {
		TaPageInfo pageInfo = null;
		UserInfo userInfo;
		try {
			userInfo = userService.getUserInfoByEO(encodeOpenid);
		} catch (OAuthException e) {
			throw new OAuthException();
		}
		if (userInfo == null) {
			throw new OAuthException();
		}

		UserInfo taInfo = userService.getUserInfoByOpenid(openid);
		if (taInfo == null) {
			throw new RequestParamsException();
		}

		pageInfo = new TaPageInfo();
		pageInfo.setUserInfo(userInfo);
		pageInfo.setUser(taInfo.getUser());
		String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid + "&oi=" + openid;
		JsApiParam jsApiParam = JsApiManager.signature(url);
		pageInfo.setJsApiParam(jsApiParam);

		List<Item> items = itemService.getItemsByOwnerId(taInfo.getUser().getUserId());
		pageInfo.setItems(items);

		return pageInfo;
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
	 * 前往register页面
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public BasePageInfo registerPage(HttpServletRequest request) throws Exception {
		BasePageInfo pageInfo = null;
		try {
			String encodeOpenid = request.getParameter("eo");
			String decodeOpenid = EncryptUtil.decryptAES(encodeOpenid, WechatConstant.ENCODE_KEY_OPENID);
			String[] decodeOpenidInfo = decodeOpenid.split(";");
			if (decodeOpenidInfo.length == 2 && StringUtil.isNumber(decodeOpenidInfo[1])) {
				pageInfo = new BasePageInfo();

				String openid = decodeOpenidInfo[0];
				UserInfo userInfo = userService.getUserInfoByOpenid(openid);
				if (userInfo != null) {
					pageInfo.setUserInfo(userInfo);
					String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
					JsApiParam jsApiParam = JsApiManager.signature(url);
					pageInfo.setJsApiParam(jsApiParam);

				} else {
					throw new OAuthException();
				}

			} else {
				throw new OAuthException();
			}

		} catch (Exception e) {
			throw new Exception("WechatWebService.addItemPage decode encodeOpenid exception," + e.getMessage());
		}

		return pageInfo;
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
			UserInfo userInfo = userService.getUserInfoByEO(encodeOpenid);
			if (userInfo != null) {
				String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
				addItemPageInfo = new AddItemPageInfo();
				JsApiParam jsApiParam = JsApiManager.signature(url);
				addItemPageInfo.setJsApiParam(jsApiParam);
				addItemPageInfo.setUserInfo(userInfo);
				addItemPageInfo.setItemTypes(ItemCache.getItemTypes());
			} else {
				throw new OAuthException();
			}

		} catch (Exception e) {
			throw new Exception("WechatWebService.addItemPage decode encodeOpenid exception," + e.getMessage());
		}
		return addItemPageInfo;
	}

	/**
	 * 我的宝贝列表页面
	 * 
	 * @param encodeOpenid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public MyItemsPageInfo myItemsPage(String encodeOpenid, HttpServletRequest request) throws Exception {
		UserInfo userInfo = userService.getUserInfoByEO(encodeOpenid);
		if (userInfo != null) {
			MyItemsPageInfo myItemPageInfo = new MyItemsPageInfo();

			String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
			JsApiParam jsApiParam = JsApiManager.signature(url);

			Pages pages = new Pages(1, WechatConstant.PAGE_SIZE_MY_ITEM);

			myItemPageInfo.setJsApiParam(jsApiParam);
			myItemPageInfo.setUserInfo(userInfo);
			myItemPageInfo.setItems(itemDao.getItemsByOwnerIdPages(userInfo.getUser().getUserId(), pages.getPageRow(),
					pages.getPageSize()));

			return myItemPageInfo;
		} else {
			throw new OAuthException();
		}
	}

	/**
	 * 我的交换列表页面
	 * 
	 * @param encodeOpenid
	 * @param request
	 * @return
	 * @throws OAuthException
	 */
	public MyExchangesPageInfo myExchangesPage(String encodeOpenid, HttpServletRequest request) throws OAuthException {
		UserInfo userInfo = null;
		try {
			userInfo = userService.getUserInfoByEO(encodeOpenid);
		} catch (Exception e) {
			throw new OAuthException();
		}
		if (userInfo != null) {
			MyExchangesPageInfo pageInfo = new MyExchangesPageInfo();

			Pages pages = new Pages(1, WechatConstant.PAGE_SIZE_MY_ITEM);
			List<Exchange> exchanges = itemDao.getExchangesByUserIdPages(userInfo.getUser().getUserId(),
					pages.getPageRow(), pages.getPageSize());

			List<ExchangeInfo> exchangeInfos = new ArrayList<>();

			if (exchanges != null && exchanges.size() > 0) {
				for (int i = 0; i < exchanges.size(); i++) {
					Exchange exchange = exchanges.get(i);
					Item ownerItem = itemDao.getItemById(exchange.getOwnerItemId());
					Item changerItem = itemDao.getItemById(exchange.getChangerItemId());

					if (ownerItem != null && changerItem != null) {
						ExchangeInfo info = new ExchangeInfo();
						info.setExchange(exchange);
						info.setChangerItem(changerItem);
						info.setOwnerItem(ownerItem);

						String toAgreeExchangePath = request.getScheme() + "://" + request.getServerName()
								+ request.getContextPath() + WechatConstant.SCHEMA_MARKET + "/item/toAgreeExchange?e="
								+ exchange.getExchangeId();
						String toAgreeExchangeUrl = OAuthManager.generateRedirectURI(toAgreeExchangePath,
								WechatConstant.SCOPE_SNSAPI_BASE, "");
						info.setToAgreeExchangePath(toAgreeExchangeUrl);

						exchangeInfos.add(info);
					}

				}
			}

			String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid;
			JsApiParam jsApiParam = JsApiManager.signature(url);

			pageInfo.setExchanges(exchangeInfos);
			pageInfo.setUserInfo(userInfo);
			pageInfo.setJsApiParam(jsApiParam);
			return pageInfo;
		} else {
			throw new OAuthException();
		}
	}

	/**
	 * 集市宝贝列表
	 * 
	 * @param encodeOpenid
	 * @param request
	 * @return
	 * @throws OAuthException
	 * @throws Exception
	 */
	public ItemsPageInfo itemsPage(String encodeOpenid, String itemType, HttpServletRequest request)
			throws OAuthException {
		try {
			UserInfo userInfo;
			try {
				userInfo = userService.getUserInfoByEO(encodeOpenid);
			} catch (OAuthException e) {
				throw new OAuthException();
			}
			if (userInfo != null) {
				ItemsPageInfo pageInfo = new ItemsPageInfo();

				String itemTypeUrl = "";
				if (itemType != null && !itemType.trim().equals("")) {
					itemTypeUrl = "&it=" + itemType;
				}
				String url = request.getRequestURL().toString() + "?eo=" + encodeOpenid + itemTypeUrl;
				JsApiParam jsApiParam = JsApiManager.signature(url);

				pageInfo.setJsApiParam(jsApiParam);
				pageInfo.setUserInfo(userInfo);
				try {
					pageInfo.setTableURLs(buildTableURLs(request, userInfo.getEncodeOpenid()));
				} catch (Exception e) {
					throw new OAuthException();
				}
				pageInfo.setItemTypes(ItemCache.getItemTypes());

				return pageInfo;
			} else {
				throw new OAuthException();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 宝贝详情（集市入口进入）
	 * 
	 * @param encodeOpenid
	 * @param itemId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ItemDetailPageInfo itemDetailPage(String encodeOpenid, int itemId, HttpServletRequest request)
			throws Exception {

		UserInfo userInfo = userService.getUserInfoByEO(encodeOpenid);

		if (userInfo == null) {
			throw new OAuthException();
		}

		Item item = itemDao.getItemById(itemId);
		if (item != null) {
			if (item.getStatus() == 1) {
				// 宝贝被下线
				throw new ItemIllegalException("this item is offline");
			}

			if (item.getApprovalStatus().equals(Item.STATUS_NO)) {
				// 宝贝未审批
				throw new ItemIllegalException("this item is disapproval");
			}

			int ownerId = item.getOwnerId();
			UserInfo ownerInfo = userService.getUserInfoById(ownerId);

			if (ownerInfo == null) {
				throw new ItemIllegalException("item owner is null");
			}

			if (ownerInfo.getUser().getState() == 1) {
				throw new UserIllegalException("item owner is in blacklist");
			}

			// 第一页评论
			Pages pages = new Pages(1, WechatConstant.PAGE_SIZE_ITEM_COMMENT);
			List<ItemComment> comments = itemDao.getItemCommentByItemIdPages(itemId, pages.getPageRow(),
					pages.getPageSize());
			int hasNextCommentPage = 0;
			if (comments.size() == 10) {
				hasNextCommentPage = 1;
			}

			// 前往兑换页面链接
			String toExchangePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath()
					+ WechatConstant.SCHEMA_MARKET + "/item/toExchange?tii=" + itemId;
			String toExchangeUrl = OAuthManager.generateRedirectURI(toExchangePath, WechatConstant.SCOPE_SNSAPI_BASE,
					"");

			// 微信jsapi签名
			String url = request.getRequestURL().toString() + "?ii=" + itemId + "&eo=" + encodeOpenid;
			JsApiParam jsApiParam = JsApiManager.signature(url);

			// 宝贝分享
			String toRedirecterPath = request.getScheme() + "://" + request.getServerName() + request.getContextPath()
					+ WechatConstant.SCHEMA_MARKET + "/redirecter?redirectURL=" + request.getRequestURL().toString()
					+ "?ii=" + itemId;
			String shareLink = OAuthManager.generateRedirectURI(toRedirecterPath, WechatConstant.SCOPE_SNSAPI_USERINFO,
					"");
			WxShareParam wxShareParam = new WxShareParam();
			wxShareParam.setDesc(item.getDescription());
			wxShareParam.setImgUrl(item.getFirstItemCenterImgUrl());
			wxShareParam.setLink(shareLink);

			ItemDetailPageInfo pageInfo = new ItemDetailPageInfo();
			pageInfo.setJsApiParam(jsApiParam);
			pageInfo.setItem(item);
			pageInfo.setOwnerInfo(ownerInfo.getUser());
			pageInfo.setComments(comments);
			pageInfo.setUserInfo(userInfo);
			pageInfo.setToExchangeUrl(toExchangeUrl);
			pageInfo.setHasNextCommentPage(hasNextCommentPage);
			pageInfo.setWxShareParam(wxShareParam);
			return pageInfo;
		} else {
			throw new NullPointerException("item is null");
		}
	}

	public AgreeExchangePageInfo toAgreeExchangePage(int exchangeId, HttpServletRequest request)
			throws OAuthException, ItemIllegalException {
		AgreeExchangePageInfo pageInfo = new AgreeExchangePageInfo();

		UserInfo userInfo = userService.getUserInfoBySnsbase(request);
		if (userInfo == null) {
			throw new OAuthException();
		}

		Exchange exchange = itemDao.getExchangeById(exchangeId);
		if (exchange == null) {
			throw new ItemIllegalException();
		}

		Item ownerItem = itemDao.getItemById(exchange.getOwnerItemId());
		Item changerItem = itemDao.getItemById(exchange.getChangerItemId());
		UserInfo ownerInfo = userService.getUserInfoById(exchange.getOwnerId());
		UserInfo changerInfo = userService.getUserInfoById(exchange.getChangerId());

		if (ownerItem != null && changerItem != null && ownerInfo != null && changerInfo != null
				&& ownerInfo.getUser().getUserId() == userInfo.getUser().getUserId()) {
			ExchangeInfo info = new ExchangeInfo();
			info.setExchange(exchange);
			info.setChangerItem(changerItem);
			info.setOwnerItem(ownerItem);
			info.setChanger(changerInfo.getUser());
			info.setOwner(ownerInfo.getUser());

			pageInfo.setExchangeInfo(info);
			pageInfo.setUserInfo(userInfo);
		} else {
			throw new ItemIllegalException();
		}

		return pageInfo;
	}

	/**
	 * 交换页面
	 * 
	 * @param targetItemId
	 * @param request
	 * @return
	 * @throws OAuthException
	 * @throws ItemIllegalException
	 */
	public ExchangePageInfo exchangePage(int targetItemId, HttpServletRequest request)
			throws OAuthException, ItemIllegalException {
		UserInfo userInfo = userService.getUserInfoBySnsbase(request);
		if (userInfo == null) {
			throw new OAuthException();
		}

		Item targetItem = itemDao.getItemById(targetItemId);
		if (targetItem == null) {
			throw new ItemIllegalException();
		}

		User targetUser = userService.getUserById(targetItem.getOwnerId());

		List<Item> items = itemDao.getExchangeableItemsByOwnerId(userInfo.getUser().getUserId());

		ExchangePageInfo pageInfo = new ExchangePageInfo();
		pageInfo.setItems(items);
		pageInfo.setTargetItem(targetItem);
		pageInfo.setUserInfo(userInfo);
		pageInfo.setTargetUser(targetUser);
		return pageInfo;
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
		String marketURL = basePath + "market?eo=" + encodeOpenid;
		String meURL = basePath + "me?eo=" + encodeOpenid;

		tableURLs.setHomeUrl(homeURL);
		tableURLs.setMarketUrl(marketURL);
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
			user = createUserByWechatInfo(getUserinfoResponse, request);
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
	private User createUserByWechatInfo(GetUserinfoResponse getUserinfoResponse, HttpServletRequest request) {
		User user = new User();

		String headImgUrl = null;
		if (getUserinfoResponse.getHeadimgurl() != null && !getUserinfoResponse.getHeadimgurl().trim().equals("")) {
			headImgUrl = objectStorageService.putHeadImg(getUserinfoResponse.getHeadimgurl(), request);
		}

		user.setHeadImgUrl(headImgUrl);
		user.setNickname(getUserinfoResponse.getNickname());
		user.setOpenid(getUserinfoResponse.getOpenid());
		user.setSex(getUserinfoResponse.getSex());
		user.setState(0);
		user.setLikeNum(0);
		user.setItemNum(0);
		user.setFinishNum(0);
		user.setExchangeNum(0);

		Date nowDate = new Date();
		user.setCreateTime(nowDate);
		user.setLastModifyTime(nowDate);

		userDao.saveUser(user);
		return user;
	}

}
