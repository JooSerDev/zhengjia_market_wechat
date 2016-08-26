<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="zh">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=no" />
<title>正佳分享集市</title>
<link rel="stylesheet" href="<%=path%>/include/css/post.css">
</head>
<body>

	<div class="container" id="container">
		<div class="bg-img">
			<img src="<%=path%>/include/images/post.png">
		</div>

		<div class="table_bar">
			<input type="hidden" id="homeUrl" value="${tableUrls.homeUrl }">
			<input type="hidden" id="marketUrl" value="${tableUrls.marketUrl }">
			<input type="hidden" id="postUrl" value="${tableUrls.postUrl }">
			<input type="hidden" id="meUrl" value="${tableUrls.meUrl }">

			<div class="nav-view" id="table_bar_home">
				<div class="table_bar_btn ">
					<div class="logo-home"></div>
					<div class="label">首页</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_market">
				<div class="table_bar_btn">
					<div class="logo-market"></div>
					<div class="label">集市</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_post">
				<div class="table_bar_btn active">
					<div class="logo-active"></div>
					<div class="label">活动</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_me">
				<div class="table_bar_btn">
					<div class="logo-me"></div>
					<div class="label">我</div>
				</div>
			</div>
		</div>

	</div>

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
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
			jsApiList : [ "hideOptionMenu", "showMenuItems" ]
		});

		wx.ready(function() {
			jsapiparam.isWxJsApiReady = true;
			//wx.hideOptionMenu();
			wx.showMenuItems({
				menuList : [ "menuItem:copyUrl" ]
			});
		});

		wx.error(function(res) {
			wx.hideOptionMenu();
		});

		$(function() {
			Core.tableBar.init(2);
		});
	</script>
</body>
</html>