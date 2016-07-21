package com.joosure.server.mvc.wechat.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joosure.server.mvc.wechat.dao.database.SystemFunctionDao;
import com.joosure.server.mvc.wechat.entity.domain.BaseResult;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
import com.joosure.server.mvc.wechat.entity.pojo.CheckCode;
import com.shawn.server.core.util.StringUtil;

/**
 * 系统提供的一些基础服务 <br>
 * 1.验证码服务 <br>
 * 
 * @author shawn
 *
 */
@Service
public class SystemFunctionService {

	@Autowired
	private UserService userService;
	@Autowired
	private SystemLogStorageService logService;

	@Autowired
	private SystemFunctionDao systemFunctionDao;

	/**
	 * 发送验证码<br>
	 * bugs : <br>
	 * 1.需要添加防刷
	 * 
	 * @param mobile
	 * @return
	 */
	public BaseResult sendCheckCode(String mobile, String eo) {

		BaseResult result = new BaseResult("1001");
		result.setErrMsg("未知错误");

		try {
			UserInfo userInfo = userService.getUserInfoByEO(eo);
			if (userInfo == null) {
				result.setErrCode("1002");
				result.setErrMsg("发送短信鉴权失败");
				return result;
			}

		} catch (Exception e) {
			result.setErrCode("1002");
			result.setErrMsg("发送短信鉴权失败");
			return result;
		}

		if (StringUtil.isMobile(mobile)) {
			CheckCode checkCode = new CheckCode();
			checkCode.setCode(randomCheckCodeNum());
			checkCode.setMobile(mobile);
			checkCode.setTimestamp(System.currentTimeMillis());

			String content = "手机验证码是：" + checkCode.getCode();

			// 此处发送短信
			if (sendSMS(mobile, content)) {
				systemFunctionDao.deleteCheckCodeByMobile(mobile);
				systemFunctionDao.saveCheckCode(checkCode);
				result.setErrCode("0");
				result.setErrMsg("发送成功");
			} else {
				result.setErrCode("1003");
				result.setErrMsg("发送短信失败");
			}

		} else {
			result.setErrCode("2001");
			result.setErrMsg("手机号格式错误");
		}

		return result;
	}

	/**
	 * 验证验证码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	public BaseResult validCheckCode(String mobile, String code) {
		long timeout = 5 * 60 * 1000;

		BaseResult result = new BaseResult("1001");
		result.setErrMsg("未知错误");
		if (StringUtil.isMobile(mobile)) {
			long timestamp = System.currentTimeMillis();
			timestamp = timestamp - timeout;

			CheckCode checkCode = systemFunctionDao.getCheckCodeInTime(mobile, code, timestamp);
			if (checkCode != null) {
				systemFunctionDao.deleteCheckCodeByMobile(mobile);
				result.setErrCode("0");
			} else {
				result.setErrCode("2002");
				result.setErrMsg("无效验证码");
			}

		} else {
			result.setErrCode("2001");
			result.setErrMsg("手机号格式错误");
		}

		return result;
	}

	/**
	 * 生成随机验证码
	 * 
	 * @return
	 */
	private String randomCheckCodeNum() {
		int length = 4;
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	private boolean sendSMS(String mobile, String content) {
		logService.smsLogger(mobile, content);
		return true;
	}

}
