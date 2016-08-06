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
<title>申请交换</title>
<link rel="stylesheet" href="<%=path%>/include/core/icono.min.css">
<link rel="stylesheet" href="<%=path%>/include/iScroll/scrollbar.css">
<link rel="stylesheet" href="<%=path%>/include/core/myLess.css">
<link rel="stylesheet"
	href="<%=path%>/include/swiper/swiper-3.3.1.min.css">
</head>
<body>

	<div class="container">

		<div class="row margin">
			<div class="itemType">
				<div class="label">我的宝贝</div>
				<div class="selecter">
					<select id="myItemId">
						<c:forEach items="${items}" var="item">
							<option value="${item.itemId }">${item.name }</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="row margin">
			<button class="btn btn-block" onclick="evens.onSubmitClick()">提交申请</button>
		</div>
	</div>

	<input type="hidden" id="targetItemId" value="${targetItem.itemId}">
	<input type="hidden" id="eo" value="${eo}">

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
		var jsapiparam = {};
		jsapiparam.appid = document.getElementById("appid").value;
		jsapiparam.nonceStr = document.getElementById("nonceStr").value;
		jsapiparam.timeStamp = document.getElementById("timeStamp").value;
		jsapiparam.signature = document.getElementById("signature").value;

		jsapiparam.isWxJsApiReady = false;

		wx.config({
			debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
			appId : jsapiparam.appid, // 必填，公众号的唯一标识
			timestamp : jsapiparam.timeStamp, // 必填，生成签名的时间戳
			nonceStr : jsapiparam.nonceStr, // 必填，生成签名的随机串
			signature : jsapiparam.signature,// 必填，签名，见附录1
			jsApiList : [ "hideOptionMenu", "chooseImage", "uploadImage" ]
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});

		wx.ready(function() {
			jsapiparam.isWxJsApiReady = true;
			wx.hideOptionMenu();
		});

		wx.error(function(res) {
			wx.hideOptionMenu();
		});

		var evens = {};

		$(function() {
			evens.onSubmitClick = function() {
				var eo = $("#eo").val();
				var myItemId = $("#myItemId").val();
				var targetItemId = $("#targetItemId").val();

				alert(myItemId + " --- " + targetItemId);

				$.ajax({
					url : "exchange",
					data : {
						myItemId : myItemId,
						targetItemId : targetItemId,
						eo : eo
					},
					type : "POST",
					success : function(data) {
						if (data.errCode == "0") {
							alert("exchange success");
						} else {
							alert("load fail");
						}
					}
				});
			}
		});
	</script>
</body>
</html>