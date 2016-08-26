package com.joosure.server.mvc.wechat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.cache.DictsCache;
import com.joosure.server.mvc.wechat.dao.database.ScoreDao;
import com.joosure.server.mvc.wechat.entity.pojo.Score;

@Service("scoreService")
public class ScoreService {

	@Autowired
	private ScoreDao scoreDao;

	/**
	 * 获取用户积分列表记录
	 */
	public List<Score> getScoreRecordByUserId(Score cond, int startRow, int limitSize) {
		List<Score> scoreList = new ArrayList<Score>();
		if (cond != null) {
			int userId = cond.getUserId();
			int oId = cond.getoId();
			String eventKey = cond.getEventKey();
			int score = cond.getScore();
			scoreList = scoreDao.getScoreList(oId, userId, eventKey, score, startRow, limitSize);
		}
		return scoreList;
	}

	/**
	 * 获取用户总积分
	 */
	public int getUserScore(int userId) {
		return scoreDao.getUserScore(userId);
	}

	/**
	 * 插入积分记录
	 */
	public int insertScoreRecored(Score record) {
		int ret = scoreDao.insertScore(record);
		if (ret > 0) {
			// 查用户总积分
			int score = getUserScore(record.getUserId());
			// 更新用户总积分
			score += record.getScore();
			ret = updateUserScore(record.getUserId(), score);
		}
		return ret;
	}

	/**
	 * 更新用户积分
	 */
	public int updateUserScore(int userId, int score) {
		return scoreDao.updateUserScore(userId, score);
	}

	/**
	 * 通过不同的事件进行积分插入操作
	 * 
	 * @param userId
	 * @param eventKey
	 * @return
	 */
	public int updateScoreByEvent(int userId, String eventKey) {
		int ret = 0;
		Map<String, String> scoreEvents = DictsCache.getScoreEvent();
		if (scoreEvents != null) {
			String eventScore = scoreEvents.get(eventKey);
			if (eventScore != null && !"".equals(eventScore)) {
				int score = Integer.parseInt(eventScore);
				// 获取原积分
				int oldScore = getUserScore(userId);
				// 更新用户总积分
				score += oldScore;
				ret = updateUserScore(userId, score);
				// 插入积分记录
				Score record = new Score();
				record.setUserId(userId);
				record.setEventKey(eventKey);
				record.setScore(Integer.parseInt(eventScore));
				ret = scoreDao.insertScore(record);
			}
		}
		return ret;
	}

}
