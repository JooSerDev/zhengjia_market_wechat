package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.TableURLs;
import com.joosure.server.mvc.wechat.entity.pojo.ItemType;
import com.joosure.server.mvc.wechat.entity.pojo.User;

public class HomePageInfo extends BasePageInfo {
	private TableURLs tableURLs;
	private List<User> top8User;
	private List<ItemInfo> top15Item;
	private List<ItemType> top8Type;

	public TableURLs getTableURLs() {
		return tableURLs;
	}

	public void setTableURLs(TableURLs tableURLs) {
		this.tableURLs = tableURLs;
	}

	public List<User> getTop8User() {
		return top8User;
	}

	public void setTop8User(List<User> top8User) {
		this.top8User = top8User;
	}

	public List<ItemInfo> getTop15Item() {
		return top15Item;
	}

	public void setTop15Item(List<ItemInfo> top15Item) {
		this.top15Item = top15Item;
	}

	public List<ItemType> getTop8Type() {
		return top8Type;
	}

	public void setTop8Type(List<ItemType> top8Type) {
		this.top8Type = top8Type;
	}
}
