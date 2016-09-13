package com.joosure.server.mvc.wechat.service.itf;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.entity.domain.Redirecter;
import com.joosure.server.mvc.wechat.entity.domain.page.AddItemPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.AgreeExchangePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.BasePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.ExchangePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.FinalAgreeExchangePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.HomePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.ItemDetailPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.ItemsPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MePageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MyExchangesPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.MyItemsPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.PostPageInfo;
import com.joosure.server.mvc.wechat.entity.domain.page.TaPageInfo;
import com.joosure.server.mvc.wechat.exception.ItemIllegalException;
import com.joosure.server.mvc.wechat.exception.RequestParamsException;

public interface IWechatWebService {

	public Redirecter redirecter(HttpServletRequest request)
			throws OAuthException, MalformedURLException, UnsupportedEncodingException ;
	
	public HomePageInfo homePage(HttpServletRequest request) throws OAuthException;
	
	public PostPageInfo postPage(String encodeOpenid, HttpServletRequest request) throws Exception ;
	
	public MePageInfo mePage(HttpServletRequest request) throws Exception ;
	
	public TaPageInfo taPage(HttpServletRequest request, String encodeOpenid, String openid)
			throws RequestParamsException, OAuthException ;
	
	public BasePageInfo registerPage(HttpServletRequest request) throws Exception ;
	
	public AddItemPageInfo addItemPage(HttpServletRequest request) throws Exception ;
	
	public MyItemsPageInfo myItemsPage(String encodeOpenid, HttpServletRequest request) throws Exception ;
	
	public MyExchangesPageInfo myExchangesPage(String encodeOpenid, HttpServletRequest request) throws OAuthException ;
	
	
	public ItemsPageInfo itemsPage(String encodeOpenid, String itemType, HttpServletRequest request)
			throws OAuthException ;
	
	public ItemDetailPageInfo itemDetailPage(String encodeOpenid, int itemId, HttpServletRequest request)
			throws Exception ;
	
	public ExchangePageInfo exchangePage(int targetItemId, HttpServletRequest request)
			throws OAuthException, ItemIllegalException ;
	
	public AgreeExchangePageInfo toAgreeExchangePage(int exchangeId, String eo)
			throws OAuthException, ItemIllegalException ;
	
	public FinalAgreeExchangePageInfo toFinalAgreeExchangePage(String encodeExchange, HttpServletRequest request)
			throws ItemIllegalException ;
}
