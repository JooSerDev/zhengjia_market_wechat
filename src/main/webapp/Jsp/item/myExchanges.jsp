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
<title>main</title>
<link rel="stylesheet" href="<%=path%>/include/core/icono.min.css">
<link rel="stylesheet" href="<%=path%>/include/iScroll/scrollbar.css">
<link rel="stylesheet" href="<%=path%>/include/core/myLess.css">
<link rel="stylesheet"
	href="<%=path%>/include/swiper/swiper-3.3.1.min.css">
</head>
<body>

	<!-- scroller start -->
	<div class="full" id="wrapper">
		<div class="scroller" id="scroller">
			<div class="row margin">
				<div class="exchange-list">
					<c:forEach items="${exchanges}" var="exchange">
						<div class="exchange" onclick="evens.onExchangeClick('${exchange.toAgreeExchangePath}')">
							<div class="exchange-img-view">
								<div class="owner-img-view">
									<img src="${exchange.ownerItem.firstItemCenterImgUrl}">
								</div>
								<div class="changer-img-view">
									<img src="${exchange.changerItem.firstItemCenterImgUrl}">
								</div>
							</div>
							<div class="exchange-info-view">
								<div>${exchange.ownerItem.name}</div>
								<div>${exchange.changerItem.name}</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

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

		</div>
	</div>
	<!-- scroller end -->

	<template id="item_template">
	<div class="exchange">
		<div class="exchange-img-view">
			<div class="owner-img-view">
				<img src="{ownerImgUrl}">
			</div>
			<div class="changer-img-view">
				<img src="{changerImgUrl}">
			</div>
		</div>
		<div class="exchange-info-view"></div>
	</div>
	</template>

	<input type="hidden" id="nextPage" value="${nextPage}">

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/iScroll/iscroll.js"></script>
	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/swiper/swiper-3.3.1.jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/myExchanges.js"></script>

</body>
</html>