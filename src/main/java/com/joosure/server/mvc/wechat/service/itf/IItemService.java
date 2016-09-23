package com.joosure.server.mvc.wechat.service.itf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.entity.domain.ItemCommentInfo;
import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.MyExchangeInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;
import com.joosure.server.mvc.wechat.exception.ItemIllegalException;
import com.joosure.server.mvc.wechat.exception.RequestParamsException;
import com.joosure.server.mvc.wechat.exception.UserIllegalException;

public interface IItemService {

	/*******************************/
	/**
	 * 同意交换
	 * 
	 * @param encodeExchange
	 * @throws ItemIllegalException
	 */
	void agreeExchange(String encodeExchange, String flag) throws ItemIllegalException;

	void exchangeLocation(String encodeExchange, String location) throws ItemIllegalException;

	void rePublish(String encodeExchange, int isOwner) throws ItemIllegalException;

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
	void executeExchange(String eo, int myItemId, int targetItemId)
			throws OAuthException, ItemIllegalException, UserIllegalException;

	void likeItem(int itemId, String eo) throws OAuthException, ItemIllegalException, UserIllegalException;

	void sendReport(String eo, int itemid, String msg) throws OAuthException, ItemIllegalException;

	/**
	 * 
	 * @param eo
	 * @param itemId
	 * @param content
	 * @throws OAuthException
	 * @throws RequestParamsException
	 */
	void saveItemComment(String eo, Integer itemId, String content) throws OAuthException, RequestParamsException;

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
	boolean saveItem(String eo, String itemName, String itemDesc, int itemType, String imgs, String wishItem)
			throws Exception;

	List<MyExchangeInfo> loadMyExchanges(String eo, int pageNum, String isOwner, HttpServletRequest request)
			throws OAuthException, RequestParamsException;

	/**
	 * 分页加载我的宝贝
	 * 
	 * @param eo
	 * @param pageNum
	 * @return
	 * @throws Exception
	 */
	List<Item> loadMyItems(String eo, int pageNum) throws Exception;

	List<ItemInfo> loadItems(String eo, int pageNum, String keyword, int itemType, int isRecommended)
			throws OAuthException;

	/**
	 * 
	 * @param pageNum
	 * @param itemId
	 * @return
	 */
	List<ItemCommentInfo> loatComments(int pageNum, int itemId);

	/**
	 * 获得推荐前15的宝贝
	 * 
	 * @return
	 */
	List<ItemInfo> getItemsRecommended();

	/**
	 * 热门社区
	 * 
	 * @return
	 */
	List<ItemType> getHotItemType();
}
