package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.TableURLs;

public class ItemsPageInfo extends BasePageInfo {

	private List<ItemInfo> itemInfos;
	private TableURLs tableURLs;

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
