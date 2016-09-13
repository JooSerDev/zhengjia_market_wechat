package com.joosure.server.mvc.wechat.service.itf;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.Score;

public interface IScoreService {
	/**
	 * 获取用户积分列表记录
	 */
	public List<Score> getScoreRecordByUserId(Score cond, int startRow, int limitSize) ;

	/**
	 * 插入积分记录
	 */
	public int insertScoreRecored(Score record) ;

	/**
	 * 通过不同的事件进行积分插入操作
	 * 
	 * @param userId
	 * @param eventKey
	 * @return
	 */
	public int updateScoreByEvent(int userId, String eventKey);
}