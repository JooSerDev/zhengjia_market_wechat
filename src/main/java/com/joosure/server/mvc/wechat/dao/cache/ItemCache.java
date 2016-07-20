package com.joosure.server.mvc.wechat.dao.cache;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.ItemType;

public class ItemCache {

	private static List<ItemType> ItemTypes;

	public static List<ItemType> getItemTypes() {
		if (ItemTypes == null) {
			// ItemDao itemDao = WebApplicationContextUtils.get
			// ItemTypes = itemd
		}
		return ItemTypes;
	}

}
