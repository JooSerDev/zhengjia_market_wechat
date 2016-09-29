package com.joosure.server.mvc.wechat.controller;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joosure.server.mvc.wechat.constant.DictsConstant;
import com.joosure.server.mvc.wechat.entity.itf.ItfBaseInfo;
import com.joosure.server.mvc.wechat.entity.itf.ItfTokenAppInfo;
import com.joosure.server.mvc.wechat.entity.pojo.Dict;
import com.joosure.server.mvc.wechat.entity.pojo.ItfTokenApp;
import com.joosure.server.mvc.wechat.entity.pojo.ItfTokenLog;
import com.joosure.server.mvc.wechat.service.db.IDictDbService;
import com.joosure.server.mvc.wechat.service.itf.ItfTokenService;
import com.shawn.server.core.http.RequestHandler;

@Controller
@RequestMapping("/itf")
public class ItfReqController {
	
	private static Logger log = Logger.getLogger(ItfReqController.class);
	
	@Autowired
	private ItfTokenService itfTokenService;
	@Autowired
	private IDictDbService dictDbService;
/*********************** 以下为对外访问，提供appid，获取accessToken或者ticket  *********************************/
	/**
	 * 获取 accessToken
	 * @param appId
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getAccessToken",method=RequestMethod.POST)
	@ResponseBody
	public ItfBaseInfo getAccessToken(HttpServletRequest req){
		String appId = req.getParameter("appId");
		return getToken(DictsConstant.WXTOKEN_AccessToken,appId,req);
	}
	
	/**
	 * 获取 ticket
	 * @param appId
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getTicket",method=RequestMethod.POST)
	@ResponseBody
	public ItfBaseInfo getTicket(HttpServletRequest req){
		String appId = req.getParameter("appId");
		return getToken(DictsConstant.WXTOKEN_Ticket,appId,req);
	}
	
	private ItfBaseInfo getToken(String tokenFlag,String appId, HttpServletRequest req){
		String ip = "";
		try {
			ip = RequestHandler.getIpAddr(req);
		} catch (UnknownHostException e) {
			ip = "";
			log.error("获取ip 失败："+e.getMessage());
		}
		log.info(ip+": appId开始获取"+tokenFlag);
		ItfBaseInfo itfInfo = new ItfBaseInfo();
		if(StringUtils.isBlank(appId)){
			itfInfo.setData(null);
			itfInfo.setRetCode("10001");
			itfInfo.setRetMsg("传入的 appId 不合法");
		}else{
			//1. 检查是否存在该 appId
			ItfTokenApp tmp = new ItfTokenApp();
			tmp.setAppId(appId);
			List<ItfTokenApp> tokenApps = itfTokenService.getTokenAppByPage(tmp);
			if(tokenApps!=null && tokenApps.size()>0){
				//存在该appid，获取 tokenFlag 指定的 token
				Dict info = new Dict();
				info.setParamGroup(DictsConstant.GROUP_WXTOKEN);
//				info.setParamId(DictsConstant.WXTOKEN_AccessToken);
				info.setParamId(tokenFlag);
				List<Dict> tokenInfo = dictDbService.getAllDict(info);
				if(tokenInfo!=null && tokenInfo.size()>0){
					ItfTokenAppInfo data = new ItfTokenAppInfo();
					data.setAppId(appId);
					data.setApplyName(tokenApps.get(0).getApplyName());
					data.setTokenFlag(tokenFlag);
					data.setToken(tokenInfo.get(0).getParamvalue());
					Date updateTime = tokenInfo.get(0).getUpdateTime();
					Calendar cal = Calendar.getInstance();
					cal.setTime(updateTime);
					cal.add(Calendar.HOUR, 2);  //2个小时
					cal.add(Calendar.SECOND, -200);//提前 200s
					Date expireTime = cal.getTime();
					data.setExpireTime(expireTime);
					itfInfo.setData(data);
					itfInfo.setRetCode("10000");
					itfInfo.setRetMsg("获取"+tokenFlag+"成功，过期时间:"+data.getExpireTime());
					//成功获取 token ，写入日志
					ItfTokenLog tokenLog = new ItfTokenLog();
					tokenLog.setAccessTime(new Date());
					tokenLog.setAppId(appId);
					tokenLog.setApplyName(data.getApplyName());
					tokenLog.setExpireTime(expireTime);
					tokenLog.setFlag(tokenFlag);
					tokenLog.setIp(ip);
					tokenLog.setToken(data.getToken());
					itfTokenService.insertTokenLog(tokenLog);
				}else{
					itfInfo.setData(null);
					itfInfo.setRetCode("10003");
					itfInfo.setRetMsg("无法查询到数据");
				}
			}else{
				//不存在该appid
				itfInfo.setData(null);
				itfInfo.setRetCode("10002");
				itfInfo.setRetMsg("传入的 appId错误或不存在");
			}
		}
		return itfInfo;
	}
/***********************###################################****************************/
}
