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
	<div class="container full" id="wrapper">
		<div class="scroller" id="scroller">
			
			<div class="row margin">
				<div></div>
			</div>
			
		</div>
	</div>
	<!-- scroller end -->

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
		$(function() {
			Core.mySwiper.loadSwiper();
		});
	</script>

</body>
</html>