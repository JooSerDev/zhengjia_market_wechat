package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

public class Item {

	public static final String LOCK_NORMAL = "normal";
	public static final String LOCK_DISPLAY = "display";

	public static final String STATUS_YES = "yes";
	public static final String STATUS_NO = "no";

	private Integer itemId;
	private String name;
	private String description;
	private Integer itemType;
	private Integer ownerId;
	private String ownerNickname;

	private Integer itemImgNum;
	private String itemImgIds;
	private String itemImgUrls;

	private Integer likeNum;
	private Integer unlikeNum;
	private Integer markNum;

	private Integer isRecommended; // 0-未被推荐 1-小编推荐a
	private Date recommendedTime;

	private String approvalStatus;
	private String lockStatus;

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

}
