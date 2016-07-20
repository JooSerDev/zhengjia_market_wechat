package com.joosure.server.mvc.wechat.dao.database;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;

public interface ItemDao {

	public void saveItem(Item item);

	public List<ItemType> getItemTypes();

}
