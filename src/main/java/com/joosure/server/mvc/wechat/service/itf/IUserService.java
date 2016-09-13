package com.joosure.server.mvc.wechat.service.itf;

import javax.servlet.http.HttpServletRequest;

import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.entity.domain.BaseResult;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;

public interface IUserService {
	/**
	 * 注册
	 * 
	 * @param mobile
	 * @param code
	 * @param eo
	 * @return
	 * @throws Exception
	 */
	public BaseResult register(String mobile, String code, String eo) throws Exception ;


	/**
	 * 
	 * @param encodeOpenid
	 * @return
	 * @throws Exception
	 */
	public String decodeEO(String encodeOpenid) throws Exception ;

	/**
	 * 通过openid获得用户信息和用户微信信息
	 * 
	 * @param openid
	 * @return
	 */
	public UserInfo getUserInfoByOpenid(String openid) ;

	/**
	 * 通过userId获得用户信息和用户微信信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserInfo getUserInfoById(int userId) ;

	/**
	 * 通过密文openid获得用户信息
	 * 
	 * @param encodeOpenid
	 * @return
	 * @throws OAuthException
	 * @throws Exception
	 */
	public UserInfo getUserInfoByEO(String encodeOpenid) throws OAuthException ;

	/**
	 * 通过snsapi_base方式取得用户信息
	 * 
	 * @param request
	 * @return
	 * @throws OAuthException
	 */
	public UserInfo getUserInfoBySnsbase(HttpServletRequest request) throws OAuthException ;

}
