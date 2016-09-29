package com.joosure.server.mvc.wechat.entity.itf;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ItfTokenAppInfo {

	private String appId;
	private String applyName;
	private String token;
	private String tokenFlag;
	private Date expireTime;
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	/*public Date getExpireTime() {
		return expireTime;
	}*/
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public String getTokenFlag() {
		return tokenFlag;
	}
	public void setTokenFlag(String tokenFlag) {
		this.tokenFlag = tokenFlag;
	}
	public String getExpireTime(){
		if(expireTime!=null){
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expireTime);
		}
		return "";
	}
	
}
