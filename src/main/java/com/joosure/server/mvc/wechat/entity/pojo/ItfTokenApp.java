package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

import com.joosure.server.mvc.wechat.entity.common.PagenationBean;

public class ItfTokenApp extends PagenationBean{
	private String appId;
	private String applyName;
	private Integer oprId;
	private String oprLoginId;
	private Date applyTime;
	private Date updateTime;
	private Date invalidTime;
	private String status;
	private String remark;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOprId() {
		return oprId;
	}
	public void setOprId(Integer oprId) {
		this.oprId = oprId;
	}
	public String getOprLoginId() {
		return oprLoginId;
	}
	public void setOprLoginId(String oprLoginId) {
		this.oprLoginId = oprLoginId;
	}
	
}
