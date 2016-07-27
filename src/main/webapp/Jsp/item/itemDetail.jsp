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
<title>add item</title>
<link rel="stylesheet" href="<%=path%>/include/core/icono.min.css">
<link rel="stylesheet" href="<%=path%>/include/iScroll/scrollbar.css">
<link rel="stylesheet" href="<%=path%>/include/core/myLess.css">
<link rel="stylesheet"
	href="<%=path%>/include/swiper/swiper-3.3.1.min.css">
</head>
<body>

	<div class="container">

		<div class="row margin">
			<div>${owner.user.nickname }</div>
			<div>${item.displayTime }</div>
		</div>

		<div class="row margin">
			<div>${item.description}</div>
		</div>

		<div class="row margin">
			<img src="${item.firstItemImgUrl }" class="img">
		</div>


		<div class="row margin"></div>

		<div class="row margin"></div>

		<div class="item-nav-bar">
			<div class="col-66">
				<a>👍</a>&nbsp;&nbsp;<a></a>&nbsp;&nbsp;<a></a>
			</div>
			<c:if test="${user.user.userId != owner.user.userId}">

				<div class="col-33" style="background-color: #f00; height: 100%;">
					<a style="width: 100%; height: 100%; text-align: center;"
						href="${toExchangeUrl}">我想换</a>
				</div>
			</c:if>
		</div>
	</div>

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/iScroll/iscroll.js"></script>
	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/swiper/swiper-3.3.1.jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<%-- <script src="<%=path%>/include/page/addItem.js"></script> --%>
</body>
</html>