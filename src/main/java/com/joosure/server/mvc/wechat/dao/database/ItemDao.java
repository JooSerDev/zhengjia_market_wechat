package com.joosure.server.mvc.wechat.dao.database;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;

public interface ItemDao {

	public void saveItem(Item item);

	public List<Item> getItemsByOwnerId(@Param("ownerId") int ownerId);

	public List<Item> getItemsByOwnerIdPages(@Param("ownerId") int ownerId, @Param("startRow") int startRow,
			@Param("limitSize") int limitSize);

	public List<ItemType> getItemTypes();

}
