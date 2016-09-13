package com.joosure.server.mvc.wechat.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.entity.pojo.Exchange;
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;
import com.joosure.server.mvc.wechat.entity.pojo.ItemLike;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;
import com.joosure.server.mvc.wechat.entity.pojo.WxUserCpt;
import com.joosure.server.mvc.wechat.service.db.IItemDbService;

@Service("itemDbService")
public class ItemDbService implements IItemDbService{
	
	@Autowired
	private ItemDao itemDao;

	@Override
	public void updateItem(Item item) {
		itemDao.updateItem(item);
	}

	@Override
	public void updateExchange(Exchange exchange) {
		itemDao.updateExchange(exchange);
	}

	@Override
	public void updateExchanges4cancelOthersWhenAgreeExchange(int exchangeId, int ownerItemId, int changerItemId) {
		itemDao.updateExchanges4cancelOthersWhenAgreeExchange(exchangeId, ownerItemId, changerItemId);
	}

	@Override
	public Exchange getExchangeByBothSideItemId(int ownerItemId, int changerItemId) {
		return itemDao.getExchangeByBothSideItemId(ownerItemId, changerItemId);
	}

	@Override
	public void saveItem(Item item) {
		itemDao.saveItem(item);
	}

	@Override
	public Item getItemById(int itemId) {
		return itemDao.getItemById(itemId);
	}

	@Override
	public List<Item> getItemsByOwnerId(int ownerId) {
		return itemDao.getItemsByOwnerId(ownerId);
	}

	@Override
	public List<Item> getExchangeableItemsByOwnerId(int ownerId) {
		return itemDao.getExchangeableItemsByOwnerId(ownerId);
	}

	@Override
	public List<Item> getItemsByOwnerIdPages(int ownerId, int startRow, int limitSize) {
		return itemDao.getItemsByOwnerIdPages(ownerId, startRow, limitSize);
	}

	@Override
	public List<Item> getItemsRecommended() {
		return itemDao.getItemsRecommended();
	}

	@Override
	public List<Item> getMarketItemsPages(String keyword, int itemType, int isRecommended, int startRow, int limitSize) {
		return itemDao.getMarketItemsPages(keyword, itemType, isRecommended, startRow, limitSize);
	}

	@Override
	public List<ItemType> getItemTypes() {
		return itemDao.getItemTypes();
	}

	@Override
	public void saveItemComment(ItemComment itemComment) {
		itemDao.saveItemComment(itemComment);
	}

	@Override
	public List<ItemComment> getItemCommentByItemIdPages(int itemId, int startRow, int limitSize) {
		return itemDao.getItemCommentByItemIdPages(itemId, startRow, limitSize);
	}

	@Override
	public void saveExchange(Exchange exchange) {
		itemDao.saveExchange(exchange);
	}

	@Override
	public Exchange getExchangeById(int exchangeId) {
		return itemDao.getExchangeById(exchangeId);
	}

	@Override
	public List<Exchange> getExchangesByUserIdPages(int userId, int startRow, int limitSize) {
		return itemDao.getExchangesByUserIdPages(userId, startRow, limitSize);
	}

	@Override
	public List<Exchange> getExchangesByUserIdInOwnerSidePages(int userId, int startRow, int limitSize) {
		return itemDao.getExchangesByUserIdInOwnerSidePages(userId, startRow, limitSize);
	}

	@Override
	public List<Exchange> getExchangesByUserIdInChangerSidePages(int userId, int startRow, int limitSize) {
		return itemDao.getExchangesByUserIdInChangerSidePages(userId, startRow, limitSize);
	}

	@Override
	public void saveItemLike(ItemLike itemLike) {
		itemDao.saveItemLike(itemLike);
	}

	@Override
	public ItemLike getItemLike(int itemId, int userId) {
		return itemDao.getItemLike(itemId,userId);
	}

	@Override
	public void saveItemReport(WxUserCpt wxUserCpt) {
		itemDao.saveItemReport(wxUserCpt);
	}

}
