package com.joosure.server.mvc.wechat.service.db.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sword.wechat4j.token.AccessToken;
import org.sword.wechat4j.token.Token;
import org.sword.wechat4j.token.server.CustomerServer;

import com.joosure.server.mvc.wechat.constant.DictsConstant;
import com.joosure.server.mvc.wechat.entity.pojo.Dict;
import com.joosure.server.mvc.wechat.service.db.IDictDbService;
import com.shawn.server.core.util.SpringUtil;

@Service("accessTokenDbService")
public class AccessTokenDbService extends CustomerServer{
	
	private static Logger log = Logger.getLogger(AccessTokenDbService.class);

	@Autowired
	private IDictDbService dictDbService;

	@Override
	protected String find() {
		//首先从数据库获取
		Dict cond = new Dict();
		cond.setParamGroup(DictsConstant.GROUP_WXTOKEN);
		cond.setParamId(DictsConstant.WXTOKEN_AccessToken);
		List<Dict> data = dictDbService.getAllDict(cond);
		if(data != null && data.size() > 0){
			//存在则检查是否过期，如果未过期则直接返回，否则重新获取，更新，返回
			Dict dict = data.get(0);
			if(isExpire(Dict.Expires, Dict.Redundance,dict.getAddTime().getTime())){
				//如果过期则重新获取并且保存
				String accessToken = "";
				AccessToken at = new AccessToken();
				if(at.request()){  //重新请求
					accessToken = at.getToken();
				}
				dict.setParamvalue(accessToken);
				dict.setAddTime(new Date());
				dictDbService.updateDict(dict);
				log.info("accessToken 过期，重新获取返回："+accessToken);
				return accessToken;
			}else{
				//如果没过期则直接返回
				log.info("accessToken 未过期，直接返回："+dict.getParamvalue());
				return dict.getParamvalue();
			}
		}else{
			//如果为空，则重新获取，并且插入到数据库
			String accessToken = "";
			AccessToken at = new AccessToken();
			if(at.request()){  //重新请求
				accessToken = at.getToken();
			}
			cond.setParamvalue(accessToken);
			cond.setAddTime(new Date());
			cond.setParamName("微信 accessToken");
			cond.setParamDesc("微信 accessToken");
			cond.setUpdateTime(new Date());
			cond.setStatus(1);
			log.info("accessToken 不存在，获取插入并返回："+accessToken);
			dictDbService.insertDict(cond);
			return accessToken;
		}
	}

	@Override
	public boolean save(Token token) {
		if(token == null){
			return false;
		}
		if(dictDbService==null){
			dictDbService = SpringUtil.getBean("dictDbService");
		}
		Dict cond = new Dict();
		cond.setParamGroup(DictsConstant.GROUP_WXTOKEN);
		cond.setParamId(DictsConstant.WXTOKEN_AccessToken);
		List<Dict> data = dictDbService.getAllDict(cond);
		if(data != null && data.size() > 0){//存在则更新
			Dict dict = data.get(0);
			dict.setParamvalue(token.getToken());
			dict.setAddTime(new Date());
			dictDbService.updateDict(dict);//更新
		}else{//不存在则创建
			cond.setParamvalue(token.getToken());
			cond.setAddTime(new Date());
			cond.setParamName("微信 accessToken");
			cond.setParamDesc("微信 accessToken");
			cond.setUpdateTime(new Date());
			cond.setStatus(1);
			dictDbService.insertDict(cond);
		}
		return true;
	}
	
	private boolean isExpire(Long expires,Long redundance,Long tokenTime) {
		if(redundance == null){
			redundance = 0L;
		}
		Date currentDate = new Date();
		long currentTime = currentDate.getTime();
		long expiresTime = expires * 1000L - redundance;
		return (tokenTime + expiresTime <= currentTime);
	}

}
