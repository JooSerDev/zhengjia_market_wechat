package com.joosure.server.mvc.wechat.controller;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sword.wechat4j.oauth.OAuthException;

import com.joosure.server.mvc.wechat.constant.WechatConstant;
import com.joosure.server.mvc.wechat.entity.domain.AjaxResult;
import com.joosure.server.mvc.wechat.entity.domain.BaseResult;
import com.joosure.server.mvc.wechat.entity.domain.ItemCommentInfo;
import com.joosure.server.mvc.wechat.entity.domain.ItemInfo;
import com.joosure.server.mvc.wechat.entity.domain.MyExchangeInfo;
import com.joosure.server.mvc.wechat.entity.domain.Pages;
import com.joosure.server.mvc.wechat.entity.domain.Redirecter;
import com.joosure.server.mvc.wechat.entity.domain.UserInfo;
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
import com.joosure.server.mvc.wechat.entity.pojo.Item;
import com.joosure.server.mvc.wechat.entity.pojo.WxUserMsg;
import com.joosure.server.mvc.wechat.exception.ItemIllegalException;
import com.joosure.server.mvc.wechat.exception.RequestParamsException;
import com.joosure.server.mvc.wechat.exception.UserIllegalException;
import com.joosure.server.mvc.wechat.service.db.IItemDbService;
import com.joosure.server.mvc.wechat.service.db.ISystemFunctionDbService;
import com.joosure.server.mvc.wechat.service.db.IUserDbService;
import com.joosure.server.mvc.wechat.service.db.IWxUserMsgDbService;
import com.joosure.server.mvc.wechat.service.itf.IItemService;
import com.joosure.server.mvc.wechat.service.itf.ISystemFunctionService;
import com.joosure.server.mvc.wechat.service.itf.ISystemLogStorageService;
import com.joosure.server.mvc.wechat.service.itf.IUserService;
import com.joosure.server.mvc.wechat.service.itf.IWechatNativeService;
import com.joosure.server.mvc.wechat.service.itf.IWechatWebService;
import com.shawn.server.core.http.RequestHandler;
import com.shawn.server.core.http.ResponseHandler;
import com.shawn.server.core.util.JsonUtil;
import com.shawn.server.core.util.StringUtil;

/**
 * 处理Wechat端来自页面的请求<br>
 * 第一期全部链接使用手动授权，第二期更新为先静默授权再手动授权 <br>
 * <br>
 * bugs：<br>
 * 1.Ajax接口目前没有接入日志
 * 
 * @author Shawnpoon
 */
@RequestMapping("/wechat")
@Controller
public class WechatWebController {

	@Autowired
	private IWechatWebService wechatWebService;
	@Autowired
	private IWechatNativeService wechatNativeService;
	@Autowired
	private ISystemLogStorageService logService;
	@Autowired
	private IItemService itemService;
	@Autowired
	private ISystemFunctionService systemFunctionService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IItemDbService itemDbService;
	@Autowired
	private ISystemFunctionDbService systemFunctionDbService;
	@Autowired
	private IUserDbService userDbService;
	@Autowired
	private IWxUserMsgDbService wxUserMsgDbService;

	/**
	 * 多重跳转路由
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/redirecter")
	public String redirecter(HttpServletRequest request, HttpServletResponse response, Model model) {
		String redirectURL = "error/404";
		try {
			Redirecter redirecter = wechatWebService.redirecter(request);
			if (redirecter.isValid()) {
				redirectURL = "redirect:" + redirecter.getRedirectURL();
			} else {
			}
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.redirecter");
		}
		return redirectURL;
	}

	/**
	 * 发送checkcode - ajax
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/sendCheckCode")
	public void sendCheckCode(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			String mobile = request.getParameter("mobile");
			if (StringUtil.isBlank(mobile) || StringUtil.isBlank(eo)) {
				ar.setErrCode("9002");
			} else {
				if (userDbService.getUserByMobile(mobile) != null) {
					ar.setErrCode("9003");
					ar.setErrMsg("该号码已经被注册");
				} else {
					BaseResult baseResult = systemFunctionService.sendCheckCode(mobile, eo);
					ar.setErrCode(baseResult.getErrCode() + "");
					ar.setErrMsg(baseResult.getErrMsg());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("9001");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 发送checkcode - ajax
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/sendReport")
	public void sendReport(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			String itemIdStr = request.getParameter("ii");
			String msg = request.getParameter("msg");

			Integer itemId = null;
			try {
				itemId = Integer.parseInt(itemIdStr);
			} catch (Exception e) {
				throw new RequestParamsException();
			}

			itemService.sendReport(eo, itemId, msg);
			ar.setErrCode("0");

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("9001");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			HomePageInfo homePageInfo = wechatWebService.homePage(request);
			model.addAttribute("tableUrls", homePageInfo.getTableURLs());
			model.addAttribute("jsapi", homePageInfo.getJsApiParam());
			model.addAttribute("users", homePageInfo.getTop8User());
			model.addAttribute("items", homePageInfo.getTop15Item());
			model.addAttribute("eo", homePageInfo.getUserInfo().getEncodeOpenid());
			model.addAttribute("types", homePageInfo.getTop8Type());
			model.addAttribute("share", homePageInfo.getWxShareParam());

			pageLogger(request, "/wechat/home", homePageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.home");
		}
		return "home";
	}

	@RequestMapping("/post")
	public String post(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String eo = request.getParameter("eo");
			PostPageInfo PageInfo = wechatWebService.postPage(eo, request);
			model.addAttribute("tableUrls", PageInfo.getTableURLs());
			model.addAttribute("jsapi", PageInfo.getJsApiParam());

			pageLogger(request, "/wechat/post", PageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.post");
		}
		return "post";
	}

	/**
	 * 个人页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/me")
	public String me(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			MePageInfo pageInfo = wechatWebService.mePage(request);
			model.addAttribute("tableUrls", pageInfo.getTableURLs());
			model.addAttribute("eo", pageInfo.getUserInfo().getEncodeOpenid());
			model.addAttribute("user", pageInfo.getUserInfo().getUser());
			model.addAttribute("jsapi", pageInfo.getJsApiParam());

			String redirectUrl = registeredValidAndRedirect(pageInfo.getUserInfo(), request);
			if (redirectUrl != null) {
				System.out.println("me.redirect to :: " + redirectUrl);
				return redirectUrl;
			}

			pageLogger(request, "/wechat/me", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.me");
		}
		return "me";
	}

	/**
	 * ta的信息页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/ta")
	public String ta(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String eo = request.getParameter("eo");
			String openid = request.getParameter("oi");
			TaPageInfo pageInfo = wechatWebService.taPage(request, eo, openid);

			model.addAttribute("user", pageInfo.getUser());
			model.addAttribute("items", pageInfo.getItems());
			model.addAttribute("jsapi", pageInfo.getJsApiParam());

			pageLogger(request, "/wechat/ta", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.ta");
		}
		return "user/ta";
	}

	/**
	 * 注册页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/me/register")
	public String register(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			BasePageInfo pageInfo = wechatWebService.registerPage(request);
			model.addAttribute("eo", pageInfo.getUserInfo().getEncodeOpenid());
			model.addAttribute("user", pageInfo.getUserInfo().getUser());
			model.addAttribute("jsapi", pageInfo.getJsApiParam());

			pageLogger(request, "/wechat/register", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.register");
		}
		return "user/register";
	}

	/**
	 * 注册 － ajax
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/me/registe")
	public void registe(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			String mobile = request.getParameter("mobile");
			String code = request.getParameter("checkCode");
			BaseResult br = userService.register(mobile, code, eo);
			ar.setErrCode(br.getErrCode());
			ar.setErrMsg(br.getErrMsg());
		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 添加宝贝页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/addItem")
	public String addItem(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			AddItemPageInfo addItemPageInfo = wechatWebService.addItemPage(request);
			model.addAttribute("jsapi", addItemPageInfo.getJsApiParam());
			model.addAttribute("itemTypes", addItemPageInfo.getItemTypes());

			String redirectUrl = registeredValidAndRedirect(addItemPageInfo.getUserInfo(), request);
			if (redirectUrl != null) {
				return redirectUrl;
			}

			pageLogger(request, "/wechat/item/addItem", addItemPageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.addItem");
		}
		return "item/addItem";
	}

	/**
	 * 我的宝贝页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/myItems")
	public String myItems(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			MyItemsPageInfo pageInfo = wechatWebService.myItemsPage(eo, request);

			model.addAttribute("jsapi", pageInfo.getJsApiParam());
			model.addAttribute("items", pageInfo.getItems());
			model.addAttribute("nextPage", pageInfo.getItems().size() == WechatConstant.PAGE_SIZE_MY_ITEM ? 1 : 0);

			String redirectUrl = registeredValidAndRedirect(pageInfo.getUserInfo(), request);
			if (redirectUrl != null) {
				return redirectUrl;
			}

			pageLogger(request, "/wechat/item/myItem", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.myItem");
		}
		return "item/myItems";
	}

	/**
	 * 我的交换页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/myExchanges")
	public String myExchanges(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			MyExchangesPageInfo pageInfo = wechatWebService.myExchangesPage(eo, request);

			model.addAttribute("jsapi", pageInfo.getJsApiParam());

			String redirectUrl = registeredValidAndRedirect(pageInfo.getUserInfo(), request);
			if (redirectUrl != null) {
				return redirectUrl;
			}

			pageLogger(request, "/wechat/item/myExchanges", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.myExchanges");
		}
		return "item/myExchanges";
	}

	/**
	 * 市集入口，宝贝列表页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/market")
	public String items(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			String itemType = request.getParameter("it");

			ItemsPageInfo pageInfo = wechatWebService.itemsPage(eo, itemType, request);
			model.addAttribute("eo", pageInfo.getUserInfo().getEncodeOpenid());
			model.addAttribute("jsapi", pageInfo.getJsApiParam());
			model.addAttribute("tableUrls", pageInfo.getTableURLs());
			model.addAttribute("itemTypes", pageInfo.getItemTypes());
			model.addAttribute("itemType", itemType == null || itemType.trim().equals("") ? "0" : itemType);

			pageLogger(request, "/wechat/market", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.items");
		}
		return "item/items";
	}

	@RequestMapping("/item/itemInExchange")
	public String itemDetailInExchange(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String itemIdStr = request.getParameter("ii");
			int itemId = 0;
			try {
				itemId = Integer.parseInt(itemIdStr);
			} catch (Exception e) {
				throw new NumberFormatException();
			}

			String eo = request.getParameter("eo");

			ItemDetailPageInfo pageInfo = wechatWebService.itemDetailPage(eo, itemId, request);
			model.addAttribute("owner", pageInfo.getOwnerInfo());
			model.addAttribute("user", pageInfo.getUserInfo());
			model.addAttribute("item", pageInfo.getItem());
			model.addAttribute("itemImgList", pageInfo.getItemImgs());
			model.addAttribute("comments", pageInfo.getComments());
			model.addAttribute("hasNextCommentPage", pageInfo.getHasNextCommentPage());
			model.addAttribute("jsapi", pageInfo.getJsApiParam());

			pageLogger(request, "/wechat/item/item", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.itemDetail");
		}
		return "item/itemDetail4Exchange";
	}

	@RequestMapping("/item/item")
	public String itemDetail(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String itemIdStr = request.getParameter("ii");
			int itemId = 0;
			try {
				itemId = Integer.parseInt(itemIdStr);
			} catch (Exception e) {
				throw new NumberFormatException();
			}

			String eo = request.getParameter("eo");

			ItemDetailPageInfo pageInfo = wechatWebService.itemDetailPage(eo, itemId, request);
			model.addAttribute("owner", pageInfo.getOwnerInfo());
			model.addAttribute("user", pageInfo.getUserInfo());
			model.addAttribute("item", pageInfo.getItem());
			model.addAttribute("itemImgList", pageInfo.getItemImgs());
			model.addAttribute("comments", pageInfo.getComments());
			model.addAttribute("hasNextCommentPage", pageInfo.getHasNextCommentPage());
			model.addAttribute("toExchangeUrl", pageInfo.getToExchangeUrl());
			model.addAttribute("jsapi", pageInfo.getJsApiParam());
			model.addAttribute("share", pageInfo.getWxShareParam());

			pageLogger(request, "/wechat/item/item", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.itemDetail");
		}
		return "item/itemDetail";
	}

	@RequestMapping("/item/toExchange")
	public String exchange(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			String targetItemIdStr = request.getParameter("tii");
			if (StringUtil.isBlank(targetItemIdStr)) {
				throw new ItemIllegalException();
			}

			int targetItemId = Integer.parseInt(targetItemIdStr);
			ExchangePageInfo pageInfo = wechatWebService.exchangePage(targetItemId, request);

			// if (pageInfo.getItems().size() == 0) {
			// return "redirect:addItem?eo=" +
			// pageInfo.getUserInfo().getEncodeOpenid();
			// }

			model.addAttribute("targetItem", pageInfo.getTargetItem());
			model.addAttribute("targetUser", pageInfo.getTargetUser());
			model.addAttribute("items", pageInfo.getItems());
			model.addAttribute("user", pageInfo.getUserInfo());
			model.addAttribute("isHasItems", pageInfo.getItems().size() == 0 ? 0 : 1);
			model.addAttribute("eo", pageInfo.getUserInfo().getEncodeOpenid());

			pageLogger(request, "/wechat/item/toExchange", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.exchange");
		}
		return "item/exchange";
	}

	@RequestMapping("/item/toAgreeExchange")
	public String toAgreeExchange(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			String exchangeIdStr = request.getParameter("e");
			if (StringUtil.isBlank(exchangeIdStr)) {
				throw new ItemIllegalException();
			}

			int exchangeId = Integer.parseInt(exchangeIdStr);

			String eo = request.getParameter("eo");

			AgreeExchangePageInfo pageInfo = wechatWebService.toAgreeExchangePage(exchangeId, eo);
			int isOwner = 0;

			model.addAttribute("ee", pageInfo.getExchangeInfo().getEncodeExchange());
			// modify by Ted 20160923 change to use equals
			if (pageInfo.getUserInfo().getUser().getUserId().intValue() == pageInfo.getExchangeInfo().getOwner()
					.getUserId().intValue()) {
				model.addAttribute("userItem", pageInfo.getExchangeInfo().getOwnerItem());
				model.addAttribute("otherItem", pageInfo.getExchangeInfo().getChangerItem());
				model.addAttribute("other", pageInfo.getExchangeInfo().getChanger());
				isOwner = 1;
			} else {
				model.addAttribute("userItem", pageInfo.getExchangeInfo().getChangerItem());
				model.addAttribute("otherItem", pageInfo.getExchangeInfo().getOwnerItem());
				model.addAttribute("other", pageInfo.getExchangeInfo().getOwner());
				isOwner = 0;
			}

			model.addAttribute("isOwner", isOwner);
			model.addAttribute("exchange", pageInfo.getExchangeInfo().getExchange());

			String redirectUrl = registeredValidAndRedirect(pageInfo.getUserInfo(), request);
			if (redirectUrl != null) {
				return redirectUrl;
			}

			pageLogger(request, "/wechat/item/toAgreeExchange", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.toAgreeExchange");
		}
		return "item/agreeExchange";
	}

	@RequestMapping("/item/toFinalAgreeExchange")
	public String toFinalAgreeExchange(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			String ee = request.getParameter("ee");
			if (StringUtil.isBlank(ee)) {
				throw new RequestParamsException("非法交换请求");
			}

			FinalAgreeExchangePageInfo pageInfo = wechatWebService.toFinalAgreeExchangePage(ee, request);
			model.addAttribute("ee", ee);
			model.addAttribute("changerInfo", pageInfo.getChanger());

			String redirectUrl = registeredValidAndRedirect(pageInfo.getUserInfo(), request);
			if (redirectUrl != null) {
				return redirectUrl;
			}

			pageLogger(request, "/wechat/item/toFinalAgreeExchange", pageInfo);
		} catch (Exception e) {
			return errorPageRouter(e, "WechatWebController.toFinalAgreeExchange");
		}
		return "item/finalAgreeExchange";
	}

	@RequestMapping("/item/rePublish")
	public void rePublish(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String ee = request.getParameter("ee");
			if (StringUtil.isBlank(ee)) {
				throw new RequestParamsException("非法交换请求");
			}

			String isOwnerStr = request.getParameter("isOwner");
			int isOwner = Integer.parseInt(isOwnerStr);

			itemService.rePublish(ee, isOwner);
			ar.setErrCode("0");

		} catch (Exception e) {
			if (e instanceof RequestParamsException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/exchangeLocation")
	public void exchangeLocation(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String ee = request.getParameter("ee");
			if (StringUtil.isBlank(ee)) {
				throw new RequestParamsException("非法交换请求");
			}

			String location = request.getParameter("location");

			itemService.exchangeLocation(ee, location);
			ar.setErrCode("0");

		} catch (Exception e) {
			if (e instanceof RequestParamsException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/agreeExchange")
	public void agreeExchange(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String ee = request.getParameter("ee");
			if (StringUtil.isBlank(ee)) {
				throw new RequestParamsException("非法交换请求");
			}

			String flag = request.getParameter("flag");

			itemService.agreeExchange(ee, flag);
			ar.setErrCode("0");

		} catch (Exception e) {
			if (e instanceof RequestParamsException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/exchange")
	public void executeExchange(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			String myItemIdStr = request.getParameter("myItemId");
			String targetItemIdStr = request.getParameter("targetItemId");

			if (StringUtil.isBlank(myItemIdStr) || StringUtil.isBlank(targetItemIdStr)) {
				throw new RequestParamsException();
			}

			int myItemId = 0;
			int targetItemId = 0;
			try {
				myItemId = Integer.parseInt(myItemIdStr);
				targetItemId = Integer.parseInt(targetItemIdStr);
			} catch (Exception e) {
				throw new RequestParamsException();
			}

			itemService.executeExchange(eo, myItemId, targetItemId);
			ar.setErrCode("0");

		} catch (Exception e) {
			if (e instanceof RequestParamsException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else if (e instanceof ItemIllegalException) {
				ar.setErrCode("1003");
				ar.setErrMsg(e.getMessage());
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/likeItem")
	public void likeItem(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			String itemIdStr = request.getParameter("ii");
			int itemId = 0;
			try {
				itemId = Integer.parseInt(itemIdStr);
			} catch (Exception e) {
				throw new NumberFormatException();
			}
			itemService.likeItem(itemId, eo);
			ar.setErrCode("0");
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else if (e instanceof UserIllegalException) {
				ar.setErrCode("1003");
				ar.setErrMsg(e.getMessage());
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/loadMyItems")
	public void loadMyItems(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			String page = request.getParameter("page");
			int pageNum = 2;
			try {
				pageNum = Integer.parseInt(page);
			} catch (Exception e) {
				throw new NumberFormatException("can not format the page number");
			}

			List<Item> items = itemService.loadMyItems(eo, pageNum);
			ar.putData("myItems", items);
			ar.putData("nextPage", items.size() == Pages.DEFAULT_PAGE_SIZE ? 1 : 0);
			ar.setErrCode("0");
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/loadMyExchanges")
	public void loadMyExchanges(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			String page = request.getParameter("page");
			int pageNum = 2;
			try {
				pageNum = Integer.parseInt(page);
			} catch (Exception e) {
				throw new NumberFormatException("can not format the page number");
			}
			String isOwner = request.getParameter("isOwner");

			List<MyExchangeInfo> infos = itemService.loadMyExchanges(eo, pageNum, isOwner, request);
			ar.putData("exchanges", infos);
			ar.putData("nextPage", infos.size() == Pages.DEFAULT_PAGE_SIZE ? 1 : 0);
			ar.setErrCode("0");
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 分页加载市集宝贝
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/item/loadItems")
	public void loadItems(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			if (StringUtil.isBlank(eo)) {
				throw new OAuthException();
			}

			String page = request.getParameter("page");
			int pageNum = 1;
			try {
				pageNum = Integer.parseInt(page);
			} catch (Exception e) {
				throw new NumberFormatException("can not format the page number");
			}

			String keyword = request.getParameter("keyword");

			String itemTypeStr = request.getParameter("itemType");
			int itemType = 0;
			try {
				itemType = Integer.parseInt(itemTypeStr);
			} catch (Exception e) {
				throw new NumberFormatException("can not format the page number");
			}

			String isRecommendedStr = request.getParameter("isRecommended");
			int isRecommended = 0;
			try {
				isRecommended = Integer.parseInt(isRecommendedStr);
			} catch (Exception e) {
				throw new NumberFormatException("can not format the page number");
			}

			List<ItemInfo> items = itemService.loadItems(eo, pageNum, keyword, itemType, isRecommended);
			ar.putData("iteminfos", items);
			ar.putData("nextPage", items.size() == Pages.DEFAULT_PAGE_SIZE ? 1 : 0);
			ar.setErrCode("0");
		} catch (Exception e) {
			if (e instanceof NumberFormatException) {
				ar.setErrCode("1002");
				ar.setErrMsg("服务器内部错误");
			} else {
				ar.setErrCode("1001");
			}
			e.printStackTrace();
		}
		try {
			String json = JsonUtil.Object2JsonStr(ar);
			ResponseHandler.output(response, json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 上传宝贝图片media_id-ajax <br>
	 * 目前本接口没有鉴权，存在一定安全隐患
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/item/uploadMediaIds")
	public void uploadMediaIds(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String mediaIds = request.getParameter("mediaIds");
			List<String> list = wechatNativeService.downloadItemImagesByMediaIds(mediaIds, request);
			ar.putData("urls", list);
			ar.setErrCode("0");
		} catch (Exception e) {
			ar.setErrCode("1001");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 保存新宝贝-ajax
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/item/saveItem")
	public void saveItem(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String itemName = request.getParameter("itemName");
			String itemDesc = request.getParameter("itemDesc");
			String itemType = request.getParameter("itemType");
			String imgs = request.getParameter("imgs");
			String wishItem = request.getParameter("wishItem");
			String eo = request.getParameter("eo");

			if (StringUtil.isBlank(wishItem) || StringUtil.isBlank(itemDesc) || StringUtil.isBlank(itemType)
					|| !StringUtil.isNumber(itemType) || StringUtil.isBlank(imgs) || StringUtil.isBlank(eo)) {
				ar.setErrCode("2001");
				ar.setErrMsg("信息不完整");
			} else {
				int itemTypeNum = 0;
				try {
					itemTypeNum = Integer.parseInt(itemType);
				} catch (Exception e) {
					ar.setErrCode("2002");
					ar.setErrMsg("信息不完整");
					String json = JsonUtil.Object2JsonStr(ar);
					ResponseHandler.output(response, json);
					return;
				}

				if (itemService.saveItem(eo, itemName, itemDesc, itemTypeNum, imgs, wishItem)) {
					ar.setErrCode("0");
					ar.setErrMsg("保存成功");
				} else {
					ar.setErrCode("2003");
					ar.setErrMsg("信息不完整");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/loadComment")
	public void loadComment(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String itemIdStr = request.getParameter("ii");
			String pagenum = request.getParameter("pageNum");

			Integer itemId = null;
			int pageNum = 0;
			try {
				itemId = Integer.parseInt(itemIdStr);
				pageNum = Integer.parseInt(pagenum);
			} catch (Exception e) {
				throw new RequestParamsException();
			}

			List<ItemCommentInfo> infos = itemService.loatComments(pageNum, itemId);
			ar.putData("infos", infos);
			ar.putData("nextPage", infos.size() == WechatConstant.PAGE_SIZE_ITEM_COMMENT ? 1 : 0);
			ar.setErrCode("0");

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	@RequestMapping("/item/itemComment")
	public void saveComment(HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			String itemIdStr = request.getParameter("ii");
			String content = request.getParameter("content");

			Integer itemId = null;
			try {
				itemId = Integer.parseInt(itemIdStr);
			} catch (Exception e) {
				throw new RequestParamsException();
			}

			itemService.saveItemComment(eo, itemId, content);
			ar.setErrCode(0 + "");
			ar.setErrMsg("保存成功");

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 错误页面路由
	 * 
	 * @param e
	 * @return
	 */
	private String errorPageRouter(Exception e, String module) {
		e.printStackTrace();

		if (e instanceof OAuthException) {
			logService.systemException("OAuth fail", module);
			return "error/403";
		} else if (e instanceof ItemIllegalException) {
			logService.systemException(e.getMessage(), module);
			return "error/404";
		}
		logService.systemException(e.getMessage(), module);
		return "error/500";
	}

	/**
	 * 页面浏览记录日志
	 * 
	 * @param request
	 * @param URI
	 * @param basePageInfo
	 */
	private void pageLogger(HttpServletRequest request, String URI, BasePageInfo basePageInfo) {
		String ip = null;
		try {
			ip = RequestHandler.getIpAddr(request);
		} catch (UnknownHostException e) {
			ip = e.getMessage();
		}
		int userId = 0;
		if (basePageInfo != null && basePageInfo.getUserInfo() != null && basePageInfo.getUserInfo().getUser() != null
				&& basePageInfo.getUserInfo().getUser().getUserId() != null) {
			userId = basePageInfo.getUserInfo().getUser().getUserId();
		}
		logService.pageLogger(URI, ip, userId);
	}

	/**
	 * 注册校验，并返回路径
	 * 
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	private String registeredValidAndRedirect(UserInfo userInfo, HttpServletRequest request) throws Exception {
		if (userInfo == null) {
			return "error/403";
		}

		if (userInfo.getUser().getMobile() == null || userInfo.getUser().getMobile().trim().equals("")) {
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + WechatConstant.SCHEMA_MARKET + "/";
			return "redirect:" + basePath + "me/register?eo=" + userInfo.getEncodeOpenid();
		}

		return null;
	}

	/**
	 * 阅读统一类型的消息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/readSameMsg")
	public void readMsgs(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			String msgType = request.getParameter("msgType");
			UserInfo user = userService.getUserInfoByEO(eo);
			wxUserMsgDbService.readSameTypeMsg(msgType, user.getUser().getUserId());
			ar.setErrCode(0 + "");
			ar.setErrMsg("阅读同类消息成功");

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 阅读 所有 消息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/readAllMsg")
	public void readAllMsgs(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			UserInfo user = userService.getUserInfoByEO(eo);
			wxUserMsgDbService.readAllMsgs(user.getUser().getUserId());
			ar.setErrCode(0 + "");
			ar.setErrMsg("清除消息成功");

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 收到一条 消息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/receiveMsg")
	public void receiveMsg(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			String msgType = request.getParameter("msgType");
			UserInfo user = userService.getUserInfoByEO(eo);
			wxUserMsgDbService.receiveWxUserMsg(msgType, user.getUser().getUserId());
			ar.setErrCode(0 + "");
			ar.setErrMsg("收到消息成功");

		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}

	/**
	 * 获取用户消息记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/user/msg")
	public void getUserMsg(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ar = new AjaxResult();
		try {
			String eo = request.getParameter("eo");
			UserInfo user = userService.getUserInfoByEO(eo);
			if (user != null && user.getUser() != null) {
				WxUserMsg wxUserMsg = new WxUserMsg();
				wxUserMsg.setUserid(user.getUser().getUserId());
				WxUserMsg msg = wxUserMsgDbService.getWxUserMsg(wxUserMsg);
				if (msg != null) {
					System.out.println(msg.getTotal());
				}
				ar.setErrCode(0 + "");
				ar.setErrMsg("获取所有消息成功");
				ar.putData("userMsg", msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ar.setErrCode("1001");
			ar.setErrMsg("信息不完整");
		}

		String json = JsonUtil.Object2JsonStr(ar);
		ResponseHandler.output(response, json);
	}
}
