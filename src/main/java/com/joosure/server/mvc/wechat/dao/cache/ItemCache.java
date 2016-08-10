package com.joosure.server.mvc.wechat.dao.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.joosure.server.mvc.wechat.dao.database.ItemDao;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;
import com.shawn.server.core.util.SpringUtil;

public class ItemCache {

	private static List<ItemType> ItemTypes = new ArrayList<>();
	private static Map<Integer, ItemType> ItemTypeMap = new HashMap<>();

	static {
		System.out.println("##############");
		System.out.println("init item cache");
		System.out.println("##############");
		ItemDao itemDao = SpringUtil.getBean(ItemDao.class);
		ItemCache.ItemTypes = itemDao.getItemTypes();
		resetItemTypeMap(ItemTypes);
		System.out.println("##############");
		System.out.println("init item cache end [itemType size : " + ItemCache.ItemTypes.size() + "]");
		System.out.println("##############");
	}

	public static List<ItemType> getItemTypes() {
		return ItemTypes;
	}

	public static void setItemTypes(List<ItemType> itemTypes) {
		ItemCache.ItemTypes = itemTypes;
		resetItemTypeMap(itemTypes);
	}

	private static void resetItemTypeMap(List<ItemType> itemTypes) {
		if (itemTypes != null && itemTypes.size() > 0) {
			ItemTypeMap.clear();
			for (Iterator<ItemType> iterator = itemTypes.iterator(); iterator.hasNext();) {
				ItemType itemType = iterator.next();
				ItemTypeMap.put(itemType.getTypeId(), itemType);
			}
		}
	}

	public static ItemType getItemType(int typeId) {
		return ItemTypeMap.get(typeId);
	}

}
