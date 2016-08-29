package com.joosure.server.mvc.wechat.constant;

public class ExceptionConstant {
	public static final int msgOrUpCountToMax = -2;//用户点赞或留言 单日数量达到最大值
	public static final int updateUserScoreException = -3;//更新用户积分出错
	public static final int insertScoreException = -4;//插入积分记录出错
	public static final int userNotExistException = -5;//用户不存在
}
