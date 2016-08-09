package com.joosure.server.mvc.wechat.entity.domain;

public class Pages {

	public static final int DEFAULT_PAGE_SIZE = 10;

	private int page = 1;
	private int pageSize = DEFAULT_PAGE_SIZE;

	public Pages() {
	}

	public Pages(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

	public Pages(int page) {
		this.page = page;
	}

	public int getPageRow() {
		return getPageRow(page, pageSize);
	}

	public int getPageRow(int page) {
		return getPageRow(page, pageSize);
	}

	public int getPageRow(int page, int pageSize) {
		page -= 1;
		if (page >= 0 && pageSize >= 0) {
			return page * pageSize;
		}
		return 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
