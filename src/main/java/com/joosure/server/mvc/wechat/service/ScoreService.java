package com.joosure.server.mvc.wechat.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.constant.CommonConstant;
import com.joosure.server.mvc.wechat.constant.ExceptionConstant;
import com.joosure.server.mvc.wechat.dao.cache.DictsCache;
import com.joosure.server.mvc.wechat.entity.pojo.Score;
import com.joosure.server.mvc.wechat.entity.pojo.User;
import com.joosure.server.mvc.wechat.service.db.IScoreDbService;
import com.joosure.server.mvc.wechat.service.db.IUserDbService;
import com.joosure.server.mvc.wechat.service.itf.IScoreService;

@Service("scoreService")
public class ScoreService implements IScoreService{

	private Logger log = Logger.getLogger(ScoreService.class);

	@Autowired
	private IScoreDbService scoreDbService;
	@Autowired
	private IUserDbService userDbService;

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
			scoreList = scoreDbService.getScoreList(oId, userId, eventKey, score, startRow, limitSize);
		}
		return scoreList;
	}

	/**
	 * 获取用户总积分
	 */
	public int getUserScore(int userId) {
		return scoreDbService.getUserScore(userId);
	}

	/**
	 * 插入积分记录
	 */
	public int insertScoreRecored(Score record) {
		int ret = scoreDbService.insertScore(record);
		if (ret > 0) {
			// 查用户总积分
			int score = scoreDbService.getUserScore(record.getUserId());
			// 更新用户总积分
			score += record.getScore();
			ret = scoreDbService.updateUserScore(record.getUserId(), score);
		}
		return ret;
	}

	/**
	 * 通过不同的事件进行积分插入操作
	 * 
	 * @param userId
	 * @param eventKey
	 * @return
	 */
	public int updateScoreByEvent(int userId, String eventKey) {
		// 检查是否存在该用户：
		User user = userDbService.getUserById(userId);
		if (user == null) {
			log.warn("进行积分操作时,检测用户id：" + userId + " 不存在");
			return ExceptionConstant.userNotExistException;
		}
		int ret = 0;
		Map<String, String> scoreEvents = DictsCache.getScoreEvent();
		if (scoreEvents != null) {
			ret = specialEventKey(userId, eventKey);// 特殊操作特殊处理
			if (ret != 0) {
				return ret;
			}
			String eventScore = scoreEvents.get(eventKey);
			if (eventScore != null && !"".equals(eventScore)) {
				int score = Integer.parseInt(eventScore);
				// 获取原积分
				int oldScore = getUserScore(userId);
				// 更新用户总积分
				score += oldScore;
				ret = scoreDbService.updateUserScore(userId, score);
				if (ret != 1) {
					log.warn("进行积分操作时,更新用户积分出错");
					return ExceptionConstant.updateUserScoreException;
				}
				// 插入积分记录
				Score record = new Score();
				record.setUserId(userId);
				record.setEventKey(eventKey);
				record.setScore(Integer.parseInt(eventScore));
				ret = scoreDbService.insertScore(record);
				if (ret != 1) {
					log.warn("进行积分操作时,插入用户积分出错");
					return ExceptionConstant.insertScoreException;
				}
			}
		}
		return ret;
	}

	/**
	 * 留言和用户点赞有单日累计量
	 * 
	 * @param eventKey
	 */
	private int specialEventKey(int userId, String eventKey) {
		if (CommonConstant.SCORE_EVENT_MSG.equals(eventKey) || CommonConstant.SCORE_EVENT_USER_UP.equals(eventKey)) {
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put("userId", userId);
			cond.put("eventKey", eventKey);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			Date startTime = cal.getTime();// 开始时间
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			Date endTime = cal.getTime();// 结束时间
			cond.put("startTime", startTime);
			cond.put("endTime", endTime);
			int sumScore = scoreDbService.getSumScoreByCond(cond);
			if (sumScore > CommonConstant.DayMaxScore - 1) {// 这两个事件单日积分最多为5
				log.warn("进行积分操作时,用户点赞或留言 单日数量达到最大值");
				return ExceptionConstant.msgOrUpCountToMax;
			}
		}
		return 0;
	}
}
