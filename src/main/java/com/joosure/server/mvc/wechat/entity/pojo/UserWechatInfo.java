package com.joosure.server.mvc.wechat.entity.pojo;

import java.util.Date;

import org.sword.wechat4j.oauth.protocol.get_userinfo.GetUserinfoResponse;

import com.shawn.server.core.util.JsonUtil;

public class UserWechatInfo {

	// wechat standard info
	private String openid;
	private String nickname;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private String[] privilege;
	private String unionid;

	// something more
	private Date createTime;
	private Date lastUpdateTime;

	public UserWechatInfo() {
	}

	public UserWechatInfo(GetUserinfoResponse getUserinfoResponse) {
		this.openid = getUserinfoResponse.getOpenid();
		this.nickname = getUserinfoResponse.getNickname();
		this.sex = getUserinfoResponse.getSex();
		this.privilege = getUserinfoResponse.getPrivilege();
		this.province = getUserinfoResponse.getProvince();
		this.city = getUserinfoResponse.getCity();
		this.country = getUserinfoResponse.getCountry();
		this.headimgurl = getUserinfoResponse.getHeadimgurl();
		this.unionid = getUserinfoResponse.getUnionid();
	}

	@Override
	public String toString() {
		String jsonStr = JsonUtil.Object2JsonStr(this);
		return jsonStr;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

}
