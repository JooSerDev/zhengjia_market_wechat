package com.joosure.server.mvc.wechat.service.db.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.database.ScoreDao;
import com.joosure.server.mvc.wechat.entity.pojo.Score;
import com.joosure.server.mvc.wechat.service.db.IScoreDbService;

@Service("scoreDbService")
public class ScoreDbService implements IScoreDbService{
	
	@Autowired
	private ScoreDao scoreDao;

	@Override
	public List<Score> getScoreList(int oId, int userId, String eventKey, int score, int startRow, int limitSize) {
		return scoreDao.getScoreList(oId, userId, eventKey, score, startRow, limitSize);
	}

	@Override
	public int getUserScore(int userId) {
		return scoreDao.getUserScore(userId);
	}

	@Override
	public int insertScore(Score record) {
		return scoreDao.insertScore(record);
	}

	@Override
	public int updateUserScore(int userId, int score) {
		return scoreDao.updateUserScore(userId, score);
	}

	@Override
	public int getSumScoreByCond(Map<String, Object> cond) {
		return scoreDao.getSumScoreByCond(cond);
	}

}
