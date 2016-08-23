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
<title>add item</title>
<link rel="stylesheet" href="<%=path%>/include/css/register.css">
</head>
<body>

	<div class="container" id="container">

		<div class="cloud"></div>
		<div class="h3">完善您的个人信息</div>

		<div class="input-view">
			<input class="block-input" type="text" placeholder="请输入手机号码"
				maxlength="11" id="mobile">
		</div>

		<div class="input-view">
			<div class="code-view">
				<input class="block-input" type="text" placeholder="请输入验证码"
					maxlength="6" id="checkCode">
			</div>
			<div class="btn-view">
				<button class="block-btn" onclick="evens.onSendCheckCodeClick()">点击获取验证码</button>
			</div>
		</div>

		<div class="submit-view">
			<div class="submit-btn" onclick="evens.onSubmitClick()">提交资料</div>
		</div>

		<div class="h6">个人信息隐私说明</div>


	</div>

	<input type="hidden" id="eo" value="${eo}">

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/register.js"></script>
</body>
</html>