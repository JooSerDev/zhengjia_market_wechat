package com.joosure.server.mvc.wechat.dao.database;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.ItfTokenApp;
import com.joosure.server.mvc.wechat.entity.pojo.ItfTokenLog;

public interface ItfTokenDao {

	/**
	 * 分页条件获取tokenApp记录
	 * @param cond
	 * @return
	 */
	List<ItfTokenApp> getTokenAppByPage(ItfTokenApp cond);

	/**
	 * 分页条件获取记录总量
	 * @param cond
	 * @return
	 */
	int getTokenAppCountByPage(ItfTokenApp cond);

	/**
	 * 插入tokenApp记录
	 * @param record
	 * @return
	 */
	int insertTokenApp(ItfTokenApp record);
	
	/**
	 * 更新 tokenApp记录
	 * @param record
	 * @return
	 */
	int updateTokenApp(ItfTokenApp record);
	
	/****** 以上为 tokenApp， 以下为 tokenApp 的访问记录  tokenTokenLog *******/
	
	
	ItfTokenLog getTokenAppLogByPage(ItfTokenLog cond);
	
	int getTokenAppLogCountByPage(ItfTokenLog cond);
	
	int insertTokenAppLog(ItfTokenLog record);
	
}
