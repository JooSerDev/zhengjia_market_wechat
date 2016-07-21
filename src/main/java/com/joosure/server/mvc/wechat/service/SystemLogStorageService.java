package com.joosure.server.mvc.wechat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 统一处理系统日志
 * 
 * @author Shawn
 *
 */
@Service
public class SystemLogStorageService {

	private static final Logger DefaultLogger = LoggerFactory.getLogger("DefaultLogger");
	private static final Logger SystemExceptionLogger = LoggerFactory.getLogger("SystemException");
	private static final Logger PageAccessLogger = LoggerFactory.getLogger("PageAccessLogger");
	private static final Logger SmsLogger = LoggerFactory.getLogger("SmsLogger");

	public void info(String msg) {
		DefaultLogger.info(msg);
	}

	public void warning(String msg) {
		DefaultLogger.warn(msg);
	}

	public void error(String msg) {
		DefaultLogger.error(msg);
	}

	public void debug(String msg) {
		DefaultLogger.debug(msg);
	}

	public void systemException(String msg, String module) {
		String log = "[" + module + "] " + msg;
		SystemExceptionLogger.info(log);
	}

	public void pageLogger(String URI, String ip, int userId) {
		String log = URI + "#*#" + ip + "#*#" + userId;
		PageAccessLogger.info(log);
	}

	public void smsLogger(String mobile, String content) {
		String log = mobile + "#*#" + content;
		SmsLogger.info(log);
	}

}
