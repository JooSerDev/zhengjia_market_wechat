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
<title>添加宝贝</title>
<link rel="stylesheet" href="<%=path%>/include/core/icono.min.css">
<link rel="stylesheet" href="<%=path%>/include/iScroll/scrollbar.css">
<link rel="stylesheet" href="<%=path%>/include/css/addItem.css">
<link rel="stylesheet"
	href="<%=path%>/include/swiper/swiper-3.3.1.min.css">
</head>
<body>

	<div class="container">

		<div class="list">
			<div class="item">
				<div class="label selector-label">宝贝类别</div>
				<div class="ui">
					<select id="itemType" class="selector">
						<c:forEach items="${itemTypes}" var="it">
							<option value="${it.typeId }">${it.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="list">
			<div class="item">
				<input type="text" class="input-block" placeholder="宝贝名称"
					id="itemName">
			</div>
			<div class="item">
				<input type="text" class="input-block" placeholder="想要换"
					id="wishItem">
			</div>
		</div>

		<div class="list">
			<div class="item">
				<textarea rows="5" placeholder="宝贝介绍" id="itemDesc"></textarea>
			</div>
			<div class="item" id="imgGroup"></div>
			<div class="item">
				<div class="cameraBtnView" onclick="evens.onCameraClick()"></div>
				<div class="submitBtnView">
					<button class="btn" onclick="evens.onSubmitClick()">发布宝贝</button>
				</div>
			</div>
		</div>

		<div class="list">
			<div class="item divider-bottom">文章模板</div>
			<div class="item postTemplate">衣不如新，人不如故</div>
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
	<script src="<%=path%>/include/page/addItem.js"></script>
</body>
</html>