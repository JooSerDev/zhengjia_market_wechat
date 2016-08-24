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
<link rel="stylesheet" href="<%=path%>/include/css/items.css">
</head>
<body>
	<div class="container" id="container">

		<div class="search-bar">
			<div class="search-input-view">
				<div class="search-btn" id="searchBtn"></div>
				<input type="text" id="searchInput">
			</div>
			<div class="search-btn-view">
				<div class="text">全部分类</div>
				<div class="icon"></div>
			</div>
		</div>
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
			<div class="pull-up-text">加载中</div>
		</div>

		<div class="fab withTableBar" id="fab">
			<div class="icon"></div>
		</div>

		<div class="table_bar">
			<input type="hidden" id="homeUrl" value="${tableUrls.homeUrl }">
			<input type="hidden" id="marketUrl" value="${tableUrls.marketUrl }">
			<input type="hidden" id="meUrl" value="${tableUrls.meUrl }">

			<div class="nav-view" id="table_bar_home">
				<div class="table_bar_btn">
					<div class="logo-home"></div>
					<div class="label">首页</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_market">
				<div class="table_bar_btn active">
					<div class="logo-market"></div>
					<div class="label">集市</div>
				</div>
			</div>
			<div class="nav-view">
				<div class="table_bar_btn">
					<div class="logo-active"></div>
					<div class="label">购物车</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_me">
				<div class="table_bar_btn">
					<div class="logo-me"></div>
					<div class="label">我的</div>
				</div>
			</div>
		</div>

	</div>

	<template id="item_template">
	<div class="item" onclick="evens.onItemClick({itemId})">
		<div class="title">
			<div class="head-img">
				<img src="{head_img}">
			</div>
			<div class="nickname">{nickname}</div>
			<div class="recommended"></div>
			<div class="time">{displayTime}</div>
		</div>

		<div class="description">
			<div class="text">{description}</div>
		</div>

		<div class="imgs">{imgHtml}</div>

		<div class="wishBar">
			<div class="tag"></div>
			<div class="wishItem">{wishItem}</div>
		</div>

		<div class="foot-bar">
			<div class="text">{markNum}</div>
			<div class="commentNumTag"></div>
			<div class="text">{likeNum}</div>
			<div class="likeNumTag"></div>
		</div>

		<div class="itemType">
			<div class="img"></div>
			<div class="text">{itemTypeName}</div>
		</div>

	</div>
	</template>

	<template id="item_img_template">
	<div>
		<div class="img-view">
			<img src="{url}">
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
	<script src="<%=path%>/include/page/items.js"></script>

</body>
</html>