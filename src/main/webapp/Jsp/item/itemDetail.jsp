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
<title>宝贝详情</title>
<link rel="stylesheet" href="<%=path%>/include/core/icono.min.css">
<link rel="stylesheet" href="<%=path%>/include/iScroll/scrollbar.css">
<link rel="stylesheet" href="<%=path%>/include/css/itemDetail.css">
<link rel="stylesheet"
	href="<%=path%>/include/swiper/swiper-3.3.1.min.css">
</head>
<body>

	<!-- scroller start -->
	<div id="wrapper" class="full">
		<div class="scroller" id="scroller">

			<div class="list">
				<div class="item">
					<div class="title">
						<div class="head-img">
							<img src="${owner.headImgUrl }">
						</div>
						<div class="nickname">${owner.nickname }</div>
						<div class="time">${item.displayTime }</div>
					</div>
				</div>
				<div class="item">${item.description}</div>
				<c:forEach items="${itemImgList }" var="imgUrl">
					<div class="item">
						<img class="item-img" src="${imgUrl }">
					</div>
				</c:forEach>
			</div>

			<div class="item-nav-bar">
				<div class="like-view">
					<a class="btn" href="javascript:void(0);" id="likeBtn">点赞</a>
				</div>

				<div class="btn-view">
					<c:if
						test="${user.user.userId != owner.userId and item.lockStatus != 'exchanged'}">
						<a class="btn" href="${toExchangeUrl}">我想换</a>
					</c:if>
				</div>
			</div>

			<div class="list">
				<div class="item divider-bottom">评论</div>
				<div class="item"></div>
			</div>
		</div>
	</div>
	<!-- scroller end -->

	<input type="hidden" id="ii" value="${item.itemId}">

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<input type="hidden" id="shareTitle" value="${share.title}">
	<input type="hidden" id="shareImg" value="${share.imgUrl}">
	<input type="hidden" id="shareLink" value="${share.link}">
	<input type="hidden" id="shareDesc" value="${share.desc}">

	<script src="<%=path%>/include/iScroll/iscroll.js"></script>
	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/swiper/swiper-3.3.1.jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/itemDetail.js"></script>
</body>
</html>