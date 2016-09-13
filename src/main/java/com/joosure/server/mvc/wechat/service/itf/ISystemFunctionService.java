package com.joosure.server.mvc.wechat.service.itf;

import java.io.IOException;

import com.joosure.server.mvc.wechat.entity.domain.BaseResult;

public interface ISystemFunctionService {
	/**
	 * 发送验证码<br>
	 * bugs : <br>
	 * 1.需要添加防刷
	 * 
	 * @param mobile
	 * @return
	 */
	BaseResult sendCheckCode(String mobile, String eo) ;

	/**
	 * 验证验证码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	BaseResult validCheckCode(String mobile, String code) ;

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 * @throws IOException
	 */
	boolean sendSMS(String mobile, String content) throws IOException ;

}
