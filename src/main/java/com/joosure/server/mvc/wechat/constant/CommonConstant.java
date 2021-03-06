package com.joosure.server.mvc.wechat.constant;

/**
 * 
 * @author Ted
 * 
 */
public class CommonConstant {

	/**
	 * 每日最大阈值
	 */
	public static final int DayMaxScore = 5;// 单日某个操作累计积分 阈值

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

	public static final String OWN = "owner";
	public static final String TARGET = "target";
	
	/**
	 * 用户重置密码所使用的默认密码
	 */
	public static final String PASSWORD = "88888888";//默认密码 重置后所使用的密码

	public static final int INT_0 = 0;

	public static final int STATUS_1 = 1;

	/**
	 * 当前登陆用户 key
	 */
	public static final String CurrentSysUser = "CurrentSysUser";
	
	
	/**
	 * 用户消息类型-收到交易请求
	 */
	public static final String UserMsgType_Req = "UserMsgTypeOfRequest";
	/**
	 * 用户消息类型-收到交易响应
	 */
	public static final String UserMsgType_Rep = "UserMsgTypeOfResponse";
	/**
	 * 用户消息类型-物品被审核
	 */
	public static final String UserMsgType_Apr = "UserMsgTypeOfApproval";
}
