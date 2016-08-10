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
<meta name="format-detection" content="telephone=no">
<title>正佳分享集市</title>
<link rel="stylesheet" href="<%=path%>/include/css/me.css">
</head>
<body>

	<div class="scroller">

		<div class="container">

			<div class="cloud"></div>

			<div class="me-head">
				<div class="head-img">
					<img src="${user.headImgUrl }">
				</div>
				<div class="nickname">${user.nickname}</div>
				<div class="sex1"></div>

				<div class="info-view">
					<div class="integral-icon"></div>
					<div class="integral">12345</div>
					<div class="line"></div>
					<div class="like-icon"></div>
					<div class="like">${user.likeNum}</div>
					<div class="line"></div>
					<div class="v-icon"></div>
					<div class="v">123</div>
				</div>
			</div>

			<div class="list-view">
				<div class="label">联系方式</div>
				<div class="text">${user.mobile}</div>
			</div>
			<a href="item/myItems?eo=${eo}">
				<div class="list-view">
					<div class="label">我的宝贝</div>
					<div class="text">${user.itemNum}</div>
				</div>
			</a> <a href="item/myExchanges?eo=${eo}">
				<div class="list-view">
					<div class="label">我的交易</div>
					<div class="text">${user.exchangeNum }</div>
				</div>
			</a> <a href="item/addItem?eo=${eo}">
				<div class="add-item">发布宝贝</div>
			</a>

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
					<div class="table_bar_btn">
						<div class="logo-market"></div>
						<div class="label">集市</div>
					</div>
				</div>
				<div class="nav-view">
					<div class="table_bar_btn">
						<div class="logo-active"></div>
						<div class="label">活动</div>
					</div>
				</div>
				<div class="nav-view" id="table_bar_me">
					<div class="table_bar_btn active">
						<div class="logo-me"></div>
						<div class="label">我</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>

	<script>
		$(function() {
			Core.tableBar.init(3);
		});
	</script>

</body>
</html>