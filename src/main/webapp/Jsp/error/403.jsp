<%@page import="org.sword.wechat4j.oauth.OAuthManager"%>
<%@page import="com.joosure.server.mvc.wechat.constant.WechatConstant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + request.getContextPath()
			+ WechatConstant.SCHEMA_MARKET + "/";
	String homeURL = basePath + "redirecter?redirectURL=" + basePath + "home";
	String reStr = OAuthManager.generateRedirectURI(homeURL, WechatConstant.SCOPE_SNSAPI_USERINFO, "");
%>
<!DOCTYPE html>
<html>
<head lang="zh">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=no" />
<title>正佳分享市集</title>
<link rel="stylesheet" href="<%=path%>/include/css/error.css">
</head>
<body>
	<div class="cloud"></div>

	<div class="icon"></div>

	<div class="text">授权过期啦!</div>

	<div class="btn-view">
		<a href="<%=reStr%>">返回首页</a>
	</div>

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		var jsapiparam = {};
		jsapiparam.appid = document.getElementById("appid").value;
		jsapiparam.nonceStr = document.getElementById("nonceStr").value;
		jsapiparam.timeStamp = document.getElementById("timeStamp").value;
		jsapiparam.signature = document.getElementById("signature").value;

		jsapiparam.isWxJsApiReady = false;

		wx.config({
			debug : false,
			appId : jsapiparam.appid,
			timestamp : jsapiparam.timeStamp,
			nonceStr : jsapiparam.nonceStr,
			signature : jsapiparam.signature,
			jsApiList : [ "hideOptionMenu" ]
		});

		wx.ready(function() {
			jsapiparam.isWxJsApiReady = true;
			wx.hideOptionMenu();
		});

		wx.error(function(res) {
			wx.hideOptionMenu();
		});

		$(function() {

		});
	</script>
</body>
</html>