package com.joosure.server.mvc.wechat.dao.database;

import org.apache.ibatis.annotations.Param;

import com.joosure.server.mvc.wechat.entity.pojo.CheckCode;
import com.joosure.server.mvc.wechat.entity.pojo.PageViewLog;

public interface SystemFunctionDao {

	public void saveCheckCode(CheckCode checkCode);

	public void deleteCheckCodeByMobile(@Param("mobile") String mobile);

	public CheckCode getCheckCodeInTime(@Param("mobile") String mobile, @Param("code") String code,
			@Param("timestamp") Long timestamp);
	
	public void savePageViewLog(PageViewLog pageViewLog);

}
