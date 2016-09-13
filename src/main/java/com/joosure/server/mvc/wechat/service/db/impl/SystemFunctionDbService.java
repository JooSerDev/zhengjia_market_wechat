package com.joosure.server.mvc.wechat.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.database.SystemFunctionDao;
import com.joosure.server.mvc.wechat.entity.pojo.CheckCode;
import com.joosure.server.mvc.wechat.entity.pojo.PageViewLog;
import com.joosure.server.mvc.wechat.service.db.ISystemFunctionDbService;

@Service("systemFunctionDbService")
public class SystemFunctionDbService implements ISystemFunctionDbService{
	
	@Autowired
	private SystemFunctionDao systemFunctionDao;

	@Override
	public void saveCheckCode(CheckCode checkCode) {
		systemFunctionDao.saveCheckCode(checkCode);
	}

	@Override
	public void deleteCheckCodeByMobile(String mobile) {
		systemFunctionDao.deleteCheckCodeByMobile(mobile);
	}

	@Override
	public CheckCode getCheckCodeInTime(String mobile, String code, Long timestamp) {
		return systemFunctionDao.getCheckCodeInTime(mobile, code, timestamp);
	}

	@Override
	public void savePageViewLog(PageViewLog pageViewLog) {
		systemFunctionDao.savePageViewLog(pageViewLog);
	}

}
