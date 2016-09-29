package com.joosure.server.mvc.wechat.service.itf;

import java.util.List;

import com.joosure.server.mvc.wechat.entity.pojo.ItfTokenApp;
import com.joosure.server.mvc.wechat.entity.pojo.ItfTokenLog;

public interface ItfTokenService {

	List<ItfTokenApp> getTokenAppByPage(ItfTokenApp cond);

	int getTokenAppCountByPage(ItfTokenApp cond);

	/**
	 * 添加之前先检查是否已经存在 该公司名称
	 * @param cond
	 * @return
	 */
	int addTokenApp(ItfTokenApp cond);

	int insertTokenLog(ItfTokenLog tokenLog);

}
