<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="zh">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=no" />
<title>我的交换</title>
<link rel="stylesheet" href="<%=path%>/include/css/myExchanges.css">
</head>
<body>

	<div class="container" id="container">
		<div>
			<button onclick="evens.onOwnerClick()">owner</button>
			<button onclick="evens.onChangerClick()">changer</button>
		</div>

		<div class="exchanges"></div>

		<div class="row" id="pull_up_bar">
			<div class="pull-up">
				<div class="rect1"></div>
				<div class="rect2"></div>
				<div class="rect3"></div>
				<div class="rect4"></div>
				<div class="rect5"></div>
			</div>
			<div class="pull-up-text">加载中</div>
		</div>

	</div>

	<template id="exchange_template">
	<div class="exchange">
		<div class="my-item-bar">
			<div class="desc">{myDesc}</div>
			<div class="img">
				<img src="{myItemImg}">
			</div>
		</div>

		<div class="target-item-bar">
			<div class="exchange-icon"></div>
			<div class="desc">
				<div class="text">{targetDesc}</div>
			</div>
			<div class="img">
				<img src="{targetItemImg}">
			</div>
			<div class="target-user-view">
				<div class="img">
					<img src="{targetHeadImg}">
				</div>
				<div class="nickname">{targetNickname}</div>
			</div>
		</div>

		<div class="foot-bar">
			<div class="state-view">
				<div class="state">{exchangeState}</div>
				<div class="line"></div>
			</div>
			<div class="time-view">
				<div class="time">{displayTime}</div>
				<div class="icon"></div>
			</div>
		</div>
	</div>
	</template>

	<input type="hidden" id="nextPage" value="${nextPage}">

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/myExchanges.js"></script>

</body>
</html>