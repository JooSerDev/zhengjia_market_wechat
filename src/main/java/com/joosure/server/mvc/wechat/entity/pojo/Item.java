package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

import com.joosure.server.mvc.wechat.dao.cache.ItemCache;
import com.shawn.server.core.util.DateUtil;

public class Item {

	public static final String LOCK_NORMAL = "normal";
	public static final String LOCK_EXCHANGING = "exchanging";
	public static final String LOCK_EXCHANGED = "exchanged";

	public static final String STATUS_YES = "yes";
	public static final String STATUS_NO = "no";

	private Integer itemId;
	private String name;
	private String description;
	private String wishItem;
	private Integer itemType;
	private Integer ownerId;
	private String ownerNickname;

	private Integer itemImgNum;
	private String itemImgIds;
	private String itemImgUrls;
	private String itemCenterImgUrls;

	private Integer likeNum;
	private Integer unlikeNum;
	private Integer markNum;

	private Integer isRecommended; // 0-未被推荐 1-小编推荐a
	private Date recommendedTime;

	private String approvalStatus; // 审批情况
	private String lockStatus;// 锁定情况，有交换意向或已经交易就会锁定

	private Integer status; // 0-正常 1-下线
	private Date createTime;
	private Date lastModifyTime;
	private Date refreshTime;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerNickname() {
		return ownerNickname;
	}

	public void setOwnerNickname(String ownerNickname) {
		this.ownerNickname = ownerNickname;
	}

	public Integer getItemImgNum() {
		return itemImgNum;
	}

	public void setItemImgNum(Integer itemImgNum) {
		this.itemImgNum = itemImgNum;
	}

	public String getItemImgIds() {
		return itemImgIds;
	}

	public void setItemImgIds(String itemImgIds) {
		this.itemImgIds = itemImgIds;
	}

	public String getItemImgUrls() {
		return itemImgUrls;
	}

	public void setItemImgUrls(String itemImgUrls) {
		this.itemImgUrls = itemImgUrls;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public Integer getMarkNum() {
		return markNum;
	}

	public void setMarkNum(Integer markNum) {
		this.markNum = markNum;
	}

	public Integer getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(Integer isRecommended) {
		this.isRecommended = isRecommended;
	}

	public Date getRecommendedTime() {
		return recommendedTime;
	}

	public void setRecommendedTime(Date recommendedTime) {
		this.recommendedTime = recommendedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Date getRefreshTime() {
		return refreshTime;
	}

	public void setRefreshTime(Date refreshTime) {
		this.refreshTime = refreshTime;
	}

	public Integer getUnlikeNum() {
		return unlikeNum;
	}

	public void setUnlikeNum(Integer unlikeNum) {
		this.unlikeNum = unlikeNum;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getItemCenterImgUrls() {
		return itemCenterImgUrls;
	}

	public void setItemCenterImgUrls(String itemCenterImgUrls) {
		this.itemCenterImgUrls = itemCenterImgUrls;
	}

	public String getFirstItemImgUrl() {
		if (itemImgUrls != null) {
			String[] urls = itemImgUrls.split(";");
			return urls[0];
		}
		return null;
	}

	public String getFirstItemCenterImgUrl() {
		if (itemCenterImgUrls != null) {
			String[] urls = itemCenterImgUrls.split(";");
			return urls[0];
		}
		return null;
	}

	public String getDisplayTime() {
		if (refreshTime != null) {
			return DateUtil.defaultFormat(refreshTime);
		}
		return null;
	}

	public String getWishItem() {
		return wishItem;
	}

	public void setWishItem(String wishItem) {
		this.wishItem = wishItem;
	}

	public String getItemTypeName() {
		if (itemType != null) {
			ItemType it = ItemCache.getItemType(itemType);
			if (it != null) {
				return it.getName();
			}
		}
		return "";
	}

}
