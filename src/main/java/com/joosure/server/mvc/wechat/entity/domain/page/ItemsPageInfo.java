package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.TableURLs;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;

public class ItemsPageInfo extends BasePageInfo {

	private List<ItemInfo> itemInfos;
	private TableURLs tableURLs;
	private List<ItemType> itemTypes;

	public List<ItemType> getItemTypes() {
		return itemTypes;
	}

	public void setItemTypes(List<ItemType> itemTypes) {
		this.itemTypes = itemTypes;
	}

	public TableURLs getTableURLs() {
		return tableURLs;
	}

	public void setTableURLs(TableURLs tableURLs) {
		this.tableURLs = tableURLs;
	}

	public List<ItemInfo> getItemInfos() {
		return itemInfos;
	}

	public void setItemInfos(List<ItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
	}

}
