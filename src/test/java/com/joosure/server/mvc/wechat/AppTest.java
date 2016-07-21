package com.joosure.server.mvc.wechat;

import java.util.Calendar;

import org.sword.wechat4j.oauth.OAuthManager;

import com.joosure.server.mvc.wechat.constant.WechatConstant;

public class AppTest {

	public static void main(String[] args) throws Exception {
//		System.out.println(makeSNSAPIurl());
		String redirectURL = "http://www.joosure.com/wechatTest/wechat/me";
		String[] urls = redirectURL.split("/");
		System.out.println(urls.length);
	}

	public static String makeSNSAPIurl() {
		String url = "http://www.joosure.com/wechatTest/wechat/redirecter?redirectURL=http://www.joosure.com/wechatTest/wechat/me";
		String reStr = OAuthManager.generateRedirectURI(url, WechatConstant.SCOPE_SNSAPI_USERINFO, "");
		return reStr;
	}

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
