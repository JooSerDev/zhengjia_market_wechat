package com.joosure.server.mvc.wechat.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;

public interface ItemDao {

	/**
	 * 保存宝贝
	 * 
	 * @param item
	 */
	public void saveItem(Item item);

	public void updateItem(Item item);

	public Item getItemById(@Param("itemId") int itemId);

	public List<Item> getItemsByOwnerId(@Param("ownerId") int ownerId);

	public List<Item> getExchangeableItemsByOwnerId(@Param("ownerId") int ownerId);

	public List<Item> getItemsByOwnerIdPages(@Param("ownerId") int ownerId, @Param("startRow") int startRow,
			@Param("limitSize") int limitSize);

	/**
	 * 集市宝贝列表分页查询
	 * 
	 * @param startRow
	 * @param limitSize
	 * @return
	 */
	public List<Item> getMarketItemsPages(@Param("startRow") int startRow, @Param("limitSize") int limitSize);

	public List<ItemComment> getItemCommentByItemIdPages(@Param("itemId") int itemId, @Param("startRow") int startRow,
			@Param("limitSize") int limitSize);

	/**
	 * 宝贝分类
	 * 
	 * @return
	 */
	public List<ItemType> getItemTypes();

	public void saveExchange(Exchange exchange);
	
	public void updateExchange(Exchange exchange);

	public void updateExchanges4cancelOthersWhenAgreeExchange(@Param("exchangeId") int exchangeId,
			@Param("ownerItemId") int ownerItemId, @Param("changerItemId") int changerItemId);

	public Exchange getExchangeById(@Param("exchangeId") int exchangeId);

	/**
	 * 通过双方物品查询交易<br>
	 * 传入两个物品id，取得该两个物品有关系的交易，不论owner与changer
	 * 
	 * @param ownerItemId
	 * @param changerItemId
	 * @return
	 */
	public Exchange getExchangeByBothSideItemId(@Param("ownerItemId") int ownerItemId,
			@Param("changerItemId") int changerItemId);

	/**
	 * 获得与当前userid相关的交易
	 * 
	 * @param userId
	 * @param startRow
	 * @param limitSize
	 * @return
	 */
	public List<Exchange> getExchangesByUserIdPages(@Param("userId") int userId, @Param("startRow") int startRow,
			@Param("limitSize") int limitSize);

}
