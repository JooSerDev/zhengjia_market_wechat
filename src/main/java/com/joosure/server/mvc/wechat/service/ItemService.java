package com.joosure.server.mvc.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.oauth.OAuthException;
import org.sword.wechat4j.oauth.OAuthManager;

import com.joosure.server.mvc.wechat.constant.StorageConstant;
import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.dao.database.UserDao;
import com.joosure.server.mvc.wechat.entity.domain.ItemCommentInfo;
import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.MyExchangeInfo;
import com.joosure.server.mvc.wechat.entity.domain.Pages;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;
import com.joosure.server.mvc.wechat.entity.pojo.ItemLike;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.exception.ItemIllegalException;
import com.joosure.server.mvc.wechat.exception.RequestParamsException;
import com.joosure.server.mvc.wechat.exception.UserIllegalException;
import com.shawn.server.core.util.EncryptUtil;
import com.shawn.server.core.util.StringUtil;

@Service
public class ItemService {

	@Autowired
	private UserService userService;

	@Autowired
	private ItemDao itemDao;
	@Autowired
	private UserDao userDao;

	/**
	 * 同意交换
	 * 
	 * @param encodeExchange
	 * @throws ItemIllegalException
	 */
	public void agreeExchange(String encodeExchange, String flag) throws ItemIllegalException {
		String decodeExchange = null;
		try {
			decodeExchange = EncryptUtil.decryptAES(encodeExchange, WechatConstant.ENCODE_KEY_OPENID);
		} catch (Exception e) {
			throw new ItemIllegalException("非法交换请求0001");
		}

		if (decodeExchange == null) {
			throw new ItemIllegalException("非法交换请求0002");
		}

		String[] exchangeInfos = decodeExchange.split(";");
		if (exchangeInfos.length != 6) {
			throw new ItemIllegalException("非法交换请求0003");
		}

		String userOpenid = exchangeInfos[0];
		String targetOpenid = exchangeInfos[1];
		String userItemIdStr = exchangeInfos[2];
		String targetItemIdStr = exchangeInfos[3];
		String exchangeIdStr = exchangeInfos[4];

		int userItemId = 0;
		int targetItemId = 0;
		int exchangeId = 0;

		try {
			userItemId = Integer.parseInt(userItemIdStr);
			targetItemId = Integer.parseInt(targetItemIdStr);
			exchangeId = Integer.parseInt(exchangeIdStr);
		} catch (Exception e) {
			throw new ItemIllegalException("非法交换请求0006");
		}

		UserInfo ownerInfo = userService.getUserInfoByOpenid(userOpenid);
		if (ownerInfo == null) {
			throw new ItemIllegalException("非法交换请求0004");
		}

		UserInfo changerInfo = userService.getUserInfoByOpenid(targetOpenid);
		if (changerInfo == null) {
			throw new ItemIllegalException("非法交换请求0005");
		}

		Exchange exchange = itemDao.getExchangeById(exchangeId);
		if (exchange == null) {
			throw new ItemIllegalException("非法交换请求0007");
		}

		if (exchange.getOwnerItemId() != userItemId || exchange.getChangerItemId() != targetItemId) {
			throw new ItemIllegalException("非法交换请求0008");
		}

		if (exchange.getExchangeState().equals(Exchange.EXCHANGE_STATE_ED)) {
			throw new ItemIllegalException("交换已完成");
		}

		Item Oitem = itemDao.getItemById(userItemId);
		if (Oitem == null) {
			throw new ItemIllegalException("非法交换请求0009");
		}

		Item Citem = itemDao.getItemById(targetItemId);
		if (Citem == null) {
			throw new ItemIllegalException("非法交换请求0009");
		}

		if (Oitem.getStatus() == 1 || Oitem.getLockStatus().equals(Item.LOCK_EXCHANGED)) {
			throw new ItemIllegalException("宝贝《" + Oitem.getName() + "》已经下线或已经交换");
		}

		if (Citem.getStatus() == 1 || Citem.getLockStatus().equals(Item.LOCK_EXCHANGED)) {
			throw new ItemIllegalException("宝贝《" + Citem.getName() + "》已经下线或已经交换");
		}

		if (flag.trim().equals("1")) {
			Date now = new Date();

			// 锁定当前交易及双方宝贝
			exchange.setExchangeState(Exchange.EXCHANGE_STATE_ED);
			exchange.setExchangeTime(now);
			Oitem.setLockStatus(Item.LOCK_EXCHANGED);
			Citem.setLockStatus(Item.LOCK_EXCHANGED);

			itemDao.updateItem(Oitem);
			itemDao.updateItem(Citem);
			itemDao.updateExchange(exchange);

			// 取消双方宝贝的其他交易
			itemDao.updateExchanges4cancelOthersWhenAgreeExchange(exchangeId, userItemId, targetItemId);
		} else {
			exchange.setExchangeState(Exchange.EXCHANGE_STATE_CANCEL);
			itemDao.updateExchange(exchange);
		}

	}

	/**
	 * 发起交换请求
	 * 
	 * @param eo
	 * @param myItemId
	 * @param targetItemId
	 * @throws OAuthException
	 * @throws ItemIllegalException
	 * @throws UserIllegalException
	 */
	public void executeExchange(String eo, int myItemId, int targetItemId)
			throws OAuthException, ItemIllegalException, UserIllegalException {

		UserInfo userInfo = null;
		try {
			userInfo = getUserInfoByEo(eo);
		} catch (Exception e) {
			throw new OAuthException();
		}

		if (userInfo == null) {
			throw new OAuthException();
		}

		Item myItem = itemDao.getItemById(myItemId);
		if (myItem == null) {
			throw new ItemIllegalException("my item = null");
		} else if (myItem.getStatus() == 1 || myItem.getApprovalStatus().equals(Item.STATUS_NO)
				|| myItem.getLockStatus().equals(Item.LOCK_EXCHANGED)) {
			throw new ItemIllegalException("my item unexchangeble");
		}

		Item targetItem = itemDao.getItemById(targetItemId);
		if (targetItem == null) {
			throw new ItemIllegalException("target item = null");
		} else if (targetItem.getStatus() == 1 || targetItem.getApprovalStatus().equals(Item.STATUS_NO)
				|| targetItem.getLockStatus().equals(Item.LOCK_EXCHANGED)) {
			throw new ItemIllegalException("target item unexchangeble");
		}

		if (myItem.getOwnerId() != userInfo.getUser().getUserId()) {
			throw new UserIllegalException("is not my item");
		}

		UserInfo ownerInfo = userService.getUserInfoById(targetItem.getOwnerId());
		if (ownerInfo == null) {
			throw new UserIllegalException("owner item No owner exception");
		}

		Exchange exchange = itemDao.getExchangeByBothSideItemId(targetItemId, myItemId);
		if (exchange != null) {
			throw new ItemIllegalException("the exchange is exist");
		}

		exchange = new Exchange();
		exchange.setChangerId(userInfo.getUser().getUserId());
		exchange.setChangerItemId(myItemId);
		exchange.setChangerItemName(myItem.getName());
		exchange.setCreateTime(new Date());
		exchange.setExchangeFinishStatus(Exchange.STATUS_NO);
		exchange.setExchangeState(Exchange.EXCHANGE_STATE_ING);
		exchange.setOwnerId(targetItem.getOwnerId());
		exchange.setOwnerItemId(targetItemId);
		exchange.setOwnerItemName(targetItem.getName());
		exchange.setState(0);

		myItem.setLockStatus(Item.LOCK_EXCHANGING);
		targetItem.setLockStatus(Item.LOCK_EXCHANGING);

		itemDao.saveExchange(exchange);
		itemDao.updateItem(myItem);
		itemDao.updateItem(targetItem);
	}

	public void likeItem(int itemId, String eo) throws OAuthException, ItemIllegalException, UserIllegalException {
		UserInfo userInfo = null;
		try {
			userInfo = getUserInfoByEo(eo);
		} catch (Exception e) {
			throw new OAuthException();
		}

		if (userInfo == null) {
			throw new OAuthException();
		}

		Item item = itemDao.getItemById(itemId);
		if (item == null) {
			throw new ItemIllegalException("item = null");
		}

		ItemLike itemLike = itemDao.getItemLike(item.getItemId(), userInfo.getUser().getUserId());
		if (itemLike != null) {
			throw new UserIllegalException("user had already LIKE");
		}

		itemLike = new ItemLike();
		itemLike.setItemId(item.getItemId());
		itemLike.setUserId(userInfo.getUser().getUserId());

		item.setLikeNum(item.getLikeNum() + 1);

		User user = userInfo.getUser();
		user.setLikeNum(user.getLikeNum() + 1);

		itemDao.saveItemLike(itemLike);
		itemDao.updateItem(item);
		userDao.updateUser(user);

	}

	/**
	 * 
	 * @param eo
	 * @param itemId
	 * @param content
	 * @throws OAuthException
	 * @throws RequestParamsException
	 */
	public void saveItemComment(String eo, Integer itemId, String content)
			throws OAuthException, RequestParamsException {
		if (eo == null || eo.trim().equals("")) {
			throw new OAuthException();
		}

		UserInfo userInfo = userService.getUserInfoByEO(eo);

		if (userInfo == null) {
			throw new OAuthException();
		}

		if (itemId == null) {
			throw new RequestParamsException("itemId is null");
		}

		Item item = itemDao.getItemById(itemId);
		if (item == null) {
			throw new RequestParamsException("item is null");
		}

		ItemComment ic = new ItemComment();
		ic.setFromUserId(userInfo.getUser().getUserId());
		ic.setCreateTime(new Date());
		ic.setComment(content);
		ic.setItemId(itemId);
		ic.setState(0);
		ic.setToUserId(item.getOwnerId());

		item.setMarkNum(item.getMarkNum() + 1);

		itemDao.updateItem(item);
		itemDao.saveItemComment(ic);
	}

	/**
	 * 保存新宝贝
	 * 
	 * @param eo
	 * @param itemName
	 * @param itemDesc
	 * @param itemType
	 * @param imgs
	 * @return
	 * @throws Exception
	 */
	public boolean saveItem(String eo, String itemName, String itemDesc, int itemType, String imgs, String wishItem)
			throws Exception {
		UserInfo userInfo = getUserInfoByEo(eo);
		if (userInfo != null) {
			String[] imgUrls = imgs.split(";");
			String centerImgUrls = "";

			if (imgs != null && !StringUtil.isBlank(imgs)) {
				for (int i = 0; i < imgUrls.length; i++) {
					String[] parts = imgUrls[i].split("/");
					if (parts.length > 4) {
						String centerImgUrl = "";
						for (int j = 0; j < parts.length; j++) {
							if (j == parts.length - 4) {
								centerImgUrl = centerImgUrl + StorageConstant.ITEM_IMG_FILE_PATH + "/";
							} else if (j == parts.length - 1) {
								centerImgUrl = centerImgUrl + parts[j];
							} else {
								centerImgUrl = centerImgUrl + parts[j] + "/";
							}
						}
						if (!centerImgUrl.trim().equals("")) {
							centerImgUrls = centerImgUrls + centerImgUrl + ";";
						}

					}
				}
			}

			Date nowDate = new Date();

			Item item = new Item();
			item.setName(itemName);
			item.setDescription(itemDesc);
			item.setItemType(itemType);
			item.setWishItem(wishItem);
			item.setItemImgNum(imgUrls.length);
			item.setItemImgUrls(centerImgUrls);
			item.setItemCenterImgUrls(imgs);
			item.setOwnerId(userInfo.getUser().getUserId());
			item.setOwnerNickname(userInfo.getUser().getNickname());
			item.setCreateTime(nowDate);

			// 初始化数据
			item.setIsRecommended(0);
			item.setLikeNum(0);
			item.setUnlikeNum(0);
			item.setMarkNum(0);
			item.setStatus(0);
			item.setLastModifyTime(nowDate);
			item.setRefreshTime(nowDate);
			item.setLockStatus(Item.LOCK_NORMAL);
			item.setApprovalStatus(Item.STATUS_NO);

			itemDao.saveItem(item);

			User user = userInfo.getUser();
			user.setItemNum(user.getItemNum() + 1);
			userDao.updateUser(user);

			return true;
		}
		return false;
	}

	public List<MyExchangeInfo> loadMyExchanges(String eo, int pageNum, String isOwner, HttpServletRequest request)
			throws OAuthException, RequestParamsException {
		List<MyExchangeInfo> infos = new ArrayList<>();

		UserInfo userInfo = null;
		try {
			userInfo = userService.getUserInfoByEO(eo);
		} catch (Exception e) {
			throw new OAuthException();
		}

		if (userInfo == null) {
			throw new OAuthException();
		}

		if (pageNum <= 0 || StringUtil.isBlank(isOwner) || isOwner.trim().equals("")) {
			throw new RequestParamsException("请求参数错误");
		}

		Pages pages = new Pages(pageNum);
		List<Exchange> exchanges = null;
		if (isOwner.trim().equals("1")) {
			exchanges = itemDao.getExchangesByUserIdInOwnerSidePages(userInfo.getUser().getUserId(), pages.getPageRow(),
					pages.getPageSize());
		} else {
			exchanges = itemDao.getExchangesByUserIdInChangerSidePages(userInfo.getUser().getUserId(),
					pages.getPageRow(), pages.getPageSize());
		}

		if (exchanges != null && exchanges.size() > 0) {
			for (Iterator<Exchange> iterator = exchanges.iterator(); iterator.hasNext();) {
				Exchange exchange = iterator.next();

				int ownerId = exchange.getOwnerId();
				int changerId = exchange.getChangerId();
				User owner = userService.getUserById(ownerId);
				User changer = userService.getUserById(changerId);

				int ownerItemId = exchange.getOwnerItemId();
				int changerItemId = exchange.getChangerItemId();
				Item ownerItem = itemDao.getItemById(ownerItemId);
				Item changerItem = itemDao.getItemById(changerItemId);

				MyExchangeInfo mei = new MyExchangeInfo();
				mei.setExchange(exchange);

				if (isOwner.trim().equals("1")) {
					mei.setUser(owner);
					mei.setTarget(changer);
					mei.setUserItem(ownerItem);
					mei.setTargetItem(changerItem);
					mei.setIsOwner(true);

					String toAgreeExchangePath = request.getScheme() + "://" + request.getServerName()
							+ request.getContextPath() + WechatConstant.SCHEMA_MARKET + "/item/toAgreeExchange?e="
							+ exchange.getExchangeId();
					String toAgreeExchangeUrl = OAuthManager.generateRedirectURI(toAgreeExchangePath,
							WechatConstant.SCOPE_SNSAPI_BASE, "");
					mei.setToAgreeExchangePath(toAgreeExchangeUrl);

				} else {
					mei.setUser(changer);
					mei.setTarget(owner);
					mei.setUserItem(changerItem);
					mei.setTargetItem(ownerItem);
					mei.setIsOwner(false);
					mei.setToAgreeExchangePath("");
				}

				infos.add(mei);
			}
		}

		return infos;
	}

	/**
	 * 分页加载我的宝贝
	 * 
	 * @param eo
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	public List<Item> loadMyItems(String eo, int pageNum) throws Exception {
		UserInfo userInfo = getUserInfoByEo(eo);
		if (userInfo != null) {
			Pages pages = new Pages(pageNum);
			List<Item> items = itemDao.getItemsByOwnerIdPages(userInfo.getUser().getUserId(), pages.getPageRow(),
					pages.getPageSize());
			return items;
		}

		return null;
	}

	public List<ItemInfo> loadItems(String eo, int pageNum, String keyword) throws OAuthException {
		List<ItemInfo> itemInfos = new ArrayList<>();
		UserInfo userInfo = null;
		try {
			userInfo = userService.getUserInfoByEO(eo);
		} catch (Exception e) {
			throw new OAuthException();
		}

		if (userInfo == null) {
			throw new OAuthException();
		}

		Pages pages = new Pages(pageNum);
		List<Item> items = itemDao.getMarketItemsPages(keyword, pages.getPageRow(), pages.getPageSize());
		if (items.size() > 0) {
			for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
				Item item = iterator.next();
				UserInfo owner = userService.getUserInfoById(item.getOwnerId());
				ItemInfo itemInfo = new ItemInfo();
				itemInfo.setItem(item);
				itemInfo.setOwnerInfo(owner.getUser());
				itemInfos.add(itemInfo);
			}
		}

		return itemInfos;
	}

	/**
	 * 
	 * @param pageNum
	 * @param itemId
	 * @return
	 */
	public List<ItemCommentInfo> loatComments(int pageNum, int itemId) {
		List<ItemCommentInfo> infos = new ArrayList<>();
		if (pageNum > 0 && itemId > 0) {
			Pages pages = new Pages(pageNum, WechatConstant.PAGE_SIZE_ITEM_COMMENT);
			List<ItemComment> comments = itemDao.getItemCommentByItemIdPages(itemId, pages.getPageRow(),
					pages.getPageSize());
			if (comments.size() > 0) {
				for (Iterator<ItemComment> iterator = comments.iterator(); iterator.hasNext();) {
					ItemComment itemComment = iterator.next();
					User fromUser = userService.getUserById(itemComment.getFromUserId());

					if (fromUser != null) {
						ItemCommentInfo ici = new ItemCommentInfo();
						ici.setComment(itemComment);
						ici.setUser(fromUser);
						infos.add(ici);
					}
				}
			}
		}

		return infos;
	}

	/**
	 * 通过加密后的openid，取得用户信息
	 * 
	 * @param encodeOpenid
	 * @return
	 * @throws Exception
	 */
	private UserInfo getUserInfoByEo(String encodeOpenid) throws Exception {
		try {
			String decodeOpenid = EncryptUtil.decryptAES(encodeOpenid, WechatConstant.ENCODE_KEY_OPENID);
			String[] decodeOpenidInfo = decodeOpenid.split(";");
			if (decodeOpenidInfo.length == 2 && StringUtil.isNumber(decodeOpenidInfo[1])) {
				String openid = decodeOpenidInfo[0];
				UserInfo userInfo = userService.getUserInfoByOpenid(openid);
				return userInfo;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception("ItemService.getUserInfoByEo decode encodeOpenid exception," + e.getMessage());
		}

	}

}
