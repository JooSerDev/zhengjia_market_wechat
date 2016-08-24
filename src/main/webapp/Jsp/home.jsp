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
<link rel="stylesheet" href="<%=path%>/include/css/home.css">
</head>
<body>
	<div class="container" id="container">
		<div class="banner">
			<img src="<%=path%>/include/images/banner.png">
		</div>

		<div class="cloud-up"></div>

		<div class="ranks">
			<div class="header"></div>
			<div class="rank">
				<div class="header">
					<div class="label bbs"></div>
					<div class="text">热门人气社区TOP8</div>
				</div>
				<div class="grid">
					<div class="item">
						<div class="card">
							<div class="icon"></div>
							<div class="text">家居控</div>
						</div>
					</div>
					<div class="item">
						<div class="card">
							<div class="icon"></div>
							<div class="text">数码潮人</div>
						</div>
					</div>
					<div class="item">
						<div class="card">
							<div class="icon"></div>
							<div class="text">家居控</div>
						</div>
					</div>
					<div class="item">
						<div class="card">
							<div class="icon"></div>
							<div class="text">家居控</div>
						</div>
					</div>

				</div>
			</div>

			<!-- 分享达人 -->
			<div class="rank">
				<div class="header">
					<div class="label users"></div>
					<div class="text">分享达人</div>
				</div>
				<div class="grid">
					<c:forEach items="${users }" var="user">
						<div class="item">
							<div class="card">
								<div class="headimg">
									<img src="${user.headImgUrl }">
									<div class="like-bar">
										<div class="icon"></div>
										<div class="text">${user.itemNum }</div>
									</div>
								</div>
								<div class="text">${user.nickname }</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

			<!-- 小编推荐 -->
			<div class="rank">
				<div class="header">
					<div class="label items"></div>
					<div class="text">小编推荐</div>
				</div>
				<div class="list">
					<c:forEach items="${items }" var="ii">
						<div class="item">
							<div class="info">
								<div class="head-img">
									<img src="${ii.ownerInfo.headImgUrl }">
								</div>
								<div class="description">${ii.item.description}</div>

								<div class="wishBar">
									<div class="tag"></div>
									<div class="wishItem">${ii.item.wishItem}</div>
								</div>
								<div class="img-view">
									<img src="${ii.item.firstItemCenterImgUrl }">
								</div>
							</div>

							<div class="foot-bar">
								<div class="text">${ii.item.likeNum}</div>
								<div class="likeNumTag"></div>
							</div>

							<div class="itemType"></div>
						</div>
					</c:forEach>
				</div>
			</div>

		</div>

		<div class="table_bar">
			<input type="hidden" id="homeUrl" value="${tableUrls.homeUrl }">
			<input type="hidden" id="marketUrl" value="${tableUrls.marketUrl }">
			<input type="hidden" id="meUrl" value="${tableUrls.meUrl }">

			<div class="nav-view" id="table_bar_home">
				<div class="table_bar_btn active">
					<div class="logo-home"></div>
					<div class="label">首页</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_market">
				<div class="table_bar_btn">
					<div class="logo-market"></div>
					<div class="label">集市</div>
				</div>
			</div>
			<div class="nav-view">
				<div class="table_bar_btn">
					<div class="logo-active"></div>
					<div class="label">活动</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_me">
				<div class="table_bar_btn">
					<div class="logo-me"></div>
					<div class="label">我</div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	
	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>

	<script>
		var jsapiparam = {};
		jsapiparam.appid = document.getElementById("appid").value;
		jsapiparam.nonceStr = document.getElementById("nonceStr").value;
		jsapiparam.timeStamp = document.getElementById("timeStamp").value;
		jsapiparam.signature = document.getElementById("signature").value;

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

		$(function() {
			Core.tableBar.init(0);

			Core.resizeContainer();

			$(window).resize(function() {
				Core.resizeContainer();
			});
		});
	</script>

</body>
</html>