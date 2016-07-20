package com.joosure.server.mvc.wechat.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.shawn.server.core.util.EncryptUtil;
import com.shawn.server.core.util.StringUtil;

@Service
public class ItemService {

	@Autowired
	private UserService userService;

	@Autowired
	private ItemDao itemDao;

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
			Date nowDate = new Date();

			Item item = new Item();
			item.setName(itemName);
			item.setDescription(itemDesc);
			item.setItemType(itemType);
			item.setItemImgNum(imgUrls.length);
			item.setItemImgUrls(imgs);
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
