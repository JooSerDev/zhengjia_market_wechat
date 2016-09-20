package com.joosure.server.mvc.wechat.dao.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.joosure.server.mvc.wechat.entity.pojo.ItemType;
import com.joosure.server.mvc.wechat.service.db.IItemDbService;
import com.shawn.server.core.util.SpringUtil;

public class ItemCache {

	private static Timer timer = new Timer();
	private static List<ItemType> ItemTypes = new ArrayList<>();
	private static Map<Integer, ItemType> ItemTypeMap = new HashMap<>();

	public static void inti() {
		loadData();
	}

	public static void loadData() {
		timer.schedule(new LoadDataTask(), 0);
	}

	private static class LoadDataTask extends TimerTask {

		@Override
		public void run() {
			IItemDbService itemDao = SpringUtil.getBean("itemDbService");
			ItemCache.ItemTypes = itemDao.getItemTypes();
			resetItemTypeMap(ItemTypes);
			timer.schedule(new LoadDataTask(), 5 * 60 * 1000);
		}

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
