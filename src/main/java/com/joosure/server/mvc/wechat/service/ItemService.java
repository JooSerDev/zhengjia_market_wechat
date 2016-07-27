package com.joosure.server.mvc.wechat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.constant.StorageConstant;
import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.entity.domain.Pages;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.exception.ItemIllegalException;
import com.joosure.server.mvc.wechat.exception.UserIllegalException;
import com.shawn.server.core.util.EncryptUtil;
import com.shawn.server.core.util.StringUtil;

@Service
public class ItemService {

	@Autowired
	private UserService userService;

	@Autowired
	private ItemDao itemDao;

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

		System.out.println("myItemId::targetItemId :: " + myItemId + " -- " + targetItemId);

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

		myItem.setLockStatus(Item.LOCK_EXCHANGED);
		targetItem.setLockStatus(Item.LOCK_EXCHANGED);

		itemDao.saveExchange(exchange);
		itemDao.updateItem(myItem);
		itemDao.updateItem(targetItem);
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
	public boolean saveItem(String eo, String itemName, String itemDesc, int itemType, String imgs) throws Exception {
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
			return true;
		}
		return false;
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
