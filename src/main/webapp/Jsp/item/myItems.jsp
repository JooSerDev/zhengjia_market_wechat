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
				<div class="item-list"></div>
			</div>

		</div>
	</div>
	<!-- scroller end -->

	<template id="item_template">
	<div class="item">
		<div class="item-body">
			<div class="item-pic">
				<img src="{imgUrl}">
			</div>
			<div class="item-name">{name}</div>
			<div class="item-desc">{desc}</div>
			<div class="item-createtime">{createtime}</div>
		</div>
	</div>
	</template>

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/iScroll/iscroll.js"></script>
	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/swiper/swiper-3.3.1.jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

	<script>
		var testData = {
			imgUrl : "http://www.joosure.com:80/wechatTest/item_img_center_square/20166/21/fLvylh5ZXYC8jS4khLEn8c7yLxMJ0Nmh.jpg",
			name : "item name",
			desc : "item desc single line",
			createtime : "2016-07-22"
		}

		var evens = {};

		$(function() {
			loadTestData();
		});

		var loadTestData = function() {

			var html = $("#item_template").html();
			for (var i = 0; i < 10; i++) {
				var temp = new String(html);
				temp = Core.HtmlReplace(temp, testData);
				$(".item-list").append(temp);
			}
			setTimeout(function() {
				Core.iScroll.myScroll.refresh();
			}, 1000);

		}
	</script>

</body>
</html>