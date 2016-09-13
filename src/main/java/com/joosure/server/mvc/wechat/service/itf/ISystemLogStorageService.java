package com.joosure.server.mvc.wechat.service.itf;


public interface ISystemLogStorageService {
	
	public void info(String msg) ;
	
	public void warning(String msg) ;

	public void error(String msg) ;

	public void debug(String msg) ;

	public void systemException(String msg, String module) ;

	public void pageLogger(String URI, String ip, int userId);

	public void smsLogger(String mobile, String content) ;
}
