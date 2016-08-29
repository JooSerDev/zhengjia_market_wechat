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
<title>正佳分享集市</title>
<link rel="stylesheet"
	href="<%=path%>/include/css/finalAgreeExchange.css">
</head>
<body>
	<div class="cloud"></div>

	<div class="contact-view">
		<div class="label">联系方式</div>
		<div class="phone">******</div>
	</div>

	<div class="btn-view">
		<div class="bv">
			<button onclick="evens.onSubmitClick(0)">拒绝</button>
		</div>
		<div class="bv">
			<button onclick="evens.onSubmitClick(1)">同意</button>
		</div>
	</div>

	<input type="hidden" id="ee" value="${ee}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
		var jsapiparam = {};
		jsapiparam.appid = "";
		jsapiparam.nonceStr = "";
		jsapiparam.timeStamp = "";
		jsapiparam.signature = "";

		jsapiparam.isWxJsApiReady = false;

		wx.config({
			debug : false,
			appId : jsapiparam.appid,
			timestamp : jsapiparam.timeStamp,
			nonceStr : jsapiparam.nonceStr,
			signature : jsapiparam.signature,
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