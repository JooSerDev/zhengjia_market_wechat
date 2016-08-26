package com.joosure.server.mvc.wechat;

import java.util.Calendar;

import org.sword.wechat4j.oauth.OAuthManager;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.service.SystemFunctionService;

public class AppTest {

	public static void main(String[] args) throws Exception {
		// System.out.println(makeSNSbaseApiUrl());
		// String redirectURL = "http://www.joosure.com/wechatTest/wechat/me";
		// String[] urls = redirectURL.split("/");
		// System.out.println(urls.length);
	}

	public static String makeSNSAPIurl() {
		String url = "http://www.joosure.com/wechatTest/wechat/redirecter?redirectURL=http://www.joosure.com/wechatTest/wechat/market";
		// 宝贝分享链接
		// String url =
		// "http://www.joosure.com/wechatTest/wechat/redirecter?redirectURL=http://www.joosure.com/wechatTest/wechat/item/item?ii=4";
		String reStr = OAuthManager.generateRedirectURI(url, WechatConstant.SCOPE_SNSAPI_USERINFO, "");
		return reStr;
	}

	public static String makeSNSbaseApiUrl() {
		String url = "http://www.joosure.com/wechatTest/wechat/item/toExchange?tii=9";
		String reStr = OAuthManager.generateRedirectURI(url, WechatConstant.SCOPE_SNSAPI_BASE, "");
		return reStr;
	}

	// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx41dc46a54f790917&redirect_uri=http%3A%2F%2Fwww.joosure.com%3A80%2FwechatTest%2Fwechat%2Fitem%2FtoExchange%3Ftii%3D9&response_type=code&scope=snsapi_base&state=#wechat_redirect
	// https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx41dc46a54f790917&redirect_uri=http%3A%2F%2Fwww.joosure.com%3A80%2FwechatTest%2Fwechat%2Fitem%2FtoExchange%3Ftii%3D9&response_type=code&scope=snsapi_base&state=#wechat_redirect

	/*
	 * 宝贝分享链接 https://open.weixin.qq.com/connect/oauth2/authorize?appid=
	 * wx41dc46a54f790917&redirect_uri=http%3A%2F%2Fwww.joosure.com%2FwechatTest
	 * %2Fwechat%2Fredirecter%3FredirectURL%3Dhttp%3A%2F%2Fwww.joosure.com%
	 * 2FwechatTest%2Fwechat%2Fitem%2Fitem%3Fii%3D4&response_type=code&scope=
	 * snsapi_userinfo&state=#wechat_redirect
	 */

	public static String getFileDatePath() {
		StringBuffer sb = new StringBuffer("/");
		Calendar cal = Calendar.getInstance();
		sb.append(cal.get(Calendar.YEAR));
		sb.append(cal.get(Calendar.MONTH));
		sb.append("/");
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}

}
