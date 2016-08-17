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

	<div class="container" id="container">
		<div class="cloud"></div>

		<div class="my-item-view">
			<div class="text">${userItem.description}</div>
			<div class="img">
				<img src="${userItem.firstItemCenterImgUrl}">
			</div>
		</div>
		<div class="other-item-view">
			<div class="change-icon"></div>
			<div class="img">
				<img src="${otherItem.firstItemCenterImgUrl}">
			</div>
			<div class="text">${otherItem.description}</div>
			<div class="changer-view">
				<div class="line">
					<div class="user">
						<div class="label">申请人</div>
						<div class="headimg">
							<img src="${changer.headImgUrl}">
						</div>
						<div class="nickname">${changer.nickname}</div>
					</div>
				</div>
				<div class="line">
					<div class="icon"></div>
					<div class="displayTime">${exchange.displayTime}</div>
				</div>
			</div>
		</div>
		<div class="contact-view"></div>
		<div class="btn-view">
			<button onclick="evens.onSubmitClick(0)">拒绝</button>
			<button onclick="evens.onSubmitClick(1)">同意</button>
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
			evens.onSubmitClick = function(flag) {
				var ee = $("#ee").val();

				$.ajax({
					url : "agreeExchange",
					data : {
						ee : ee,
						flag : flag
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