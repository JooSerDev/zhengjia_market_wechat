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
<link rel="stylesheet" href="<%=path%>/include/css/agreeExchange.css">
</head>
<body>

	<div class="container">

		<div class="row margin"></div>

		<div class="row margin">
			<button class="btn btn-block" onclick="evens.onSubmitClick()">提交申请</button>
		</div>
	</div>

	<input type="hidden" id="ee" value="${ee}">

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
			debug : false,
			appId : jsapiparam.appid, // 必填，公众号的唯一标识
			timestamp : jsapiparam.timeStamp, // 必填，生成签名的时间戳
			nonceStr : jsapiparam.nonceStr, // 必填，生成签名的随机串
			signature : jsapiparam.signature,// 必填，签名，见附录1
			jsApiList : [ "hideOptionMenu" ]
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
				var ee = $("#ee").val();

				$.ajax({
					url : "agreeExchange",
					data : {
						ee : ee
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