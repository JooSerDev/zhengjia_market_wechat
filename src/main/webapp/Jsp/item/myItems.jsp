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
<title>正佳分享集市</title>
<link rel="stylesheet" href="<%=path%>/include/css/myItems.css">
</head>
<body>

	<div class="container" id="container">
		<div class="cloud"></div>

		<div class="items"></div>

		<div class="row" id="pull_up_bar">
			<div class="pull-up">
				<div class="rect1"></div>
				<div class="rect2"></div>
				<div class="rect3"></div>
				<div class="rect4"></div>
				<div class="rect5"></div>
			</div>
			<div class="pull-up-text">加载更多</div>
		</div>
		
		<div class="bottom"></div>


		<div class="add-item-view">
			<div class="addItem" id="addItem">添加宝贝</div>
		</div>

	</div>

	<template id="item_template">
	<div class="item">
		<div class="time">{createtime}</div>
		<div class="info">
			<div class="desc">{desc}</div>
			<div class="img">
				<img src="{imgUrl}">
			</div>
		</div>
		<div class="state-view">
			<div class="state">{state}</div>
			<div class="line"></div>
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
	<script src="<%=path%>/include/page/myItems.js"></script>

</body>
</html>