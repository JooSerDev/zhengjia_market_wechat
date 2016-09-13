package com.joosure.server.mvc.wechat.service.itf;

import javax.servlet.http.HttpServletRequest;

public interface IObjectStorageService {
	void putObject() ;

	void putImg() ;

	/**
	 * 通过url本地化头像图片,返回本地图片URL
	 * 
	 * @param imgUrl
	 * @return
	 */
	String putHeadImg(String imgUrl, HttpServletRequest request) ;

	/**
	 * 本地化宝贝图片
	 * 
	 * @param fileData
	 * @param request
	 * @return
	 */
	String[] putItemImg(byte[] fileData, HttpServletRequest request);

}
