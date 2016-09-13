package com.joosure.server.mvc.wechat.service.itf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IWechatNativeService {
	public List<String> downloadItemImagesByMediaIds(String mediaIds, HttpServletRequest request) ;
	
	public String getAccessToken() ;

	public String getJsApiTicket() ;
}
