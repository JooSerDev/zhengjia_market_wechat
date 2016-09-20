package com.joosure.server.mvc.wechat.constant;

public class DictsConstant {
	/**
	 * 积分奖惩字典组
	 */
	public static final String GROUP_SCORE_EVENT = "ScoreEvent";
	public static final String SCORE_EVENT_MSG = "SEMsg";// 留言
	public static final String SCORE_EVENT_USER_UP = "SEUserUp";// 用户点赞行为
	public static final String SCORE_EVENT_ITEM_UP = "SEItemUp";// 宝贝获取点赞
	public static final String SCORE_EVENT_ITEM_RCM = "SEItemRcm";// 宝贝获得推荐
	public static final String SCORE_EVENT_APPROVAL = "SEApproval";// 审核通过
	public static final String SCORE_EVENT_FACE_EXG = "SEFaceExg";// 完成认证交易
	public static final String SCORE_EVENT_RES_EXG = "SEResExg";// 响应请求交易
	public static final String SCORE_EVENT_AGR_EXG = "SEAgrExg";// 同意请求交易
	public static final String SCORE_EVENT_REGIST = "SERegist";// 注册成功
	public static final String SCORE_EVENT_ITEM_DOWN = "SEItemDown";// 制下架
	public static final String SCORE_EVENT_CLEAR_CMT = "SEClearCmt";// 清除评论
	public static final String SCORE_EVENT_ItemInfoNotReal = "SEItemInfoNotReal";// 宝贝信息不实
	public static final String SCORE_EVENT_UserNotReach = "SEUserNotReach";// 用户爽约为
	
	/**
	 * 微信 token 字典组
	 */
	public static final String GROUP_WXTOKEN = "WXToken";
	public static final String WXTOKEN_AccessToken = "accessToken";
	public static final String WXTOKEN_Ticket = "ticket";//jsApiTicket
	
	/**
	 * 微信 配置 字典组
	 */
	public static final String GROUP_WXCONFIG = "WXConfig";
	public static final String WXCONFIG_Appid = "appid";
	public static final String WXCONFIG_Secret = "secret";
	public static final String WXCONFIG_Token = "token";
	
	/**
	 * 防刷参数
	 */
	public static final String GROUP_CLICKFARMING = "ClickFarming";
	public static final String CF_ChgItemTimes = "CFChgItemTimes";//置换物品次数
	public static final String CF_ExgReqTimes = "CFExgReqTimes";//交易请求次数
	
	
}