package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

/**
 * 目前访问结果只有“成功”与“失败”，下一步需要加上“权限不足”、
 * 
 * @author shawn
 *
 */
public class PageViewLog {

	public final static String ACCESS_STATE_SUCCESS = "success";
	public final static String ACCESS_STATE_FAIL = "fail";

	private Integer logId;
	private String uri;
	private String ip;
	private Integer userId;
	private String accessState; // 访问结果
	private Date createTime;
	private String remark;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccessState() {
		return accessState;
	}

	public void setAccessState(String accessState) {
		this.accessState = accessState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
