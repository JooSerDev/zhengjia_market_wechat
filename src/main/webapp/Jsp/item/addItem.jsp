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
<link rel="stylesheet" href="<%=path%>/include/css/addItem.css">
</head>
<body>
	<div class="scroller">
		<div class="container">

			<div class="cloud"></div>

			<div class="list input-view">
				<div class="item">
					<div class="item-name-view">
						<input type="text" class="input-block" placeholder="宝贝名称（最多20字）"
							id="itemName" maxlength="20">
					</div>
					<div class="selector-view">
						<select id="itemType" class="selector">
							<option value="0">宝贝分类</option>
							<c:forEach items="${itemTypes}" var="it">
								<option value="${it.typeId }">${it.name }</option>
							</c:forEach>
						</select>
					</div>

				</div>
			</div>

			<div class="list input-view">
				<div class="item textarea">
					<textarea rows="8" placeholder="宝贝故事（描述）" id="itemDesc"
						maxlength="500"></textarea>
				</div>
				<div class="item">
					<div class="temp-view">描述模板</div>
				</div>
			</div>

			<div class="img-group-view">
				<div class="cameraBtnView" onclick="evens.onCameraClick()">
					<div class="icon"></div>
				</div>
			</div>

			<div class="list input-view">
				<div class="item wishItem">
					<input type="text" placeholder="期望交换的物品" id="wishItem">
				</div>
			</div>


			<div class="submitBtnView">
				<button class="btn" onclick="evens.onSubmitClick()">发布宝贝</button>
			</div>

			<div class="ps-text">宝贝发布需后台审核通过后才可见</div>

		</div>

	</div>

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/addItem.js"></script>
</body>
</html>