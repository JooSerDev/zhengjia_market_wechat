package com.joosure.server.mvc.wechat.dao.database;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.joosure.server.mvc.wechat.entity.pojo.Score;

public interface ScoreDao {

	List<Score> getScoreList(@Param("oId")int oId, @Param("userId")int userId, 
			@Param("eventKey")String eventKey, @Param("score")int score, 
			@Param("startRow")int startRow, @Param("limitSize")int limitSize);

	int getUserScore(@Param("userId")int userId);

	int insertScore(Score record);

	int updateUserScore(@Param("userId")int userId, @Param("score")int score);
	
	int getSumScoreByCond(Map<String,Object> cond);

}
