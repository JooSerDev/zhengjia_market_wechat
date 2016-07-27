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
	<div class="container" id="wrapper">
		<div class="scroller" id="scroller">

			<div class="row margin me-head-img-banner">
				<div class="head-img">
					<img src="${user.headImgUrl }">
				</div>
			</div>

			<div class="row margin">
				<a href="item/addItem?eo=${eo}">
					<div class="list-item">
						<div class="pull-left">加宝贝</div>
						<div class="pull-right">2</div>
					</div>
				</a>
			</div>

			<div class="row margin">
				<a href="item/myItems?eo=${eo}">
					<div class="list-item">
						<div class="pull-left">我的宝贝</div>
						<div class="pull-right">2</div>
					</div>
				</a>
			</div>

			<div class="row margin">
				<a href="tel:13268112212"> 我电话 </a>
			</div>


		</div>
	</div>
	<!-- scroller end -->

	<div class="table_bar clearfix">
		<input type="hidden" id="homeUrl" value="${tableUrls.homeUrl }">
		<input type="hidden" id="marketUrl" value="${tableUrls.marketUrl }">
		<input type="hidden" id="meUrl" value="${tableUrls.meUrl }">

		<div class="col-25 text-center" id="table_bar_home">
			<div class="table_bar_btn active">
				<div class="logo">
					<div class="icono-home"></div>
				</div>
				<div class="label">首页</div>
			</div>
		</div>
		<div class="col-25 text-center" id="table_bar_market">
			<div class="table_bar_btn">
				<div class="logo">
					<div class="icono-market"></div>
				</div>
				<div class="label">集市</div>
			</div>
		</div>
		<div class="col-25 text-center">
			<div class="table_bar_btn">
				<div class="logo">
					<div class="icono-cart"></div>
				</div>
				<div class="label">购物车</div>
			</div>
		</div>
		<div class="col-25 text-center" id="table_bar_me">
			<div class="table_bar_btn">
				<div class="logo">
					<div class="icono-gear"></div>
				</div>
				<div class="label">我的</div>
			</div>
		</div>
	</div>

	<script src="<%=path%>/include/iScroll/iscroll.js"></script>
	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/swiper/swiper-3.3.1.jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>

	<script>
		document.addEventListener('touchmove', function(e) {
			e.preventDefault();
		}, false);
		document.addEventListener('DOMContentLoaded', Core.iScroll.loadIScroll,
				false);

		$(function() {
			Core.tableBar.init(3);
		});
	</script>

</body>
</html>