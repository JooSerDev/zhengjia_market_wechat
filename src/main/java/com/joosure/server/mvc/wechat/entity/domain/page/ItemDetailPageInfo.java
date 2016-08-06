package com.joosure.server.mvc.wechat.entity.domain.page;

import java.util.ArrayList;
import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.ItemComment;
import com.joosure.server.mvc.wechat.entity.pojo.User;

public class ItemDetailPageInfo extends BasePageInfo {

	private Item item;
	private User ownerInfo;
	private List<ItemComment> comments;
	private Integer hasNextCommentPage;
	private String toExchangeUrl;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<ItemComment> getComments() {
		return comments;
	}

	public void setComments(List<ItemComment> comments) {
		this.comments = comments;
	}

	public String getToExchangeUrl() {
		return toExchangeUrl;
	}

	public void setToExchangeUrl(String toExchangeUrl) {
		this.toExchangeUrl = toExchangeUrl;
	}

	public User getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(User ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	public List<String> getItemImgs() {
		List<String> list = new ArrayList<>();
		if (item != null && item.getItemImgUrls() != null && !item.getItemImgUrls().equals("")) {
			String[] imgUrls = item.getItemImgUrls().split(";");
			if (imgUrls.length > 0) {
				for (int i = 0; i < imgUrls.length; i++) {
					String imgUrl = imgUrls[i];
					if (!imgUrl.trim().equals("")) {
						list.add(imgUrl);
					}
				}
			}
		}
		return list;
	}

	public Integer getHasNextCommentPage() {
		return hasNextCommentPage;
	}

	public void setHasNextCommentPage(Integer hasNextCommentPage) {
		this.hasNextCommentPage = hasNextCommentPage;
	}

}
