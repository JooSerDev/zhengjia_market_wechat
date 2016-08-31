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
<title>正佳分享市集</title>
<link rel="stylesheet" href="<%=path%>/include/css/home.css">
<link rel="stylesheet"
	href="<%=path%>/include/swiper/swiper-3.3.1.min.css">
</head>
<body>
	<div class="container" id="container">
		<%-- <div class="banner">
			<img src="<%=path%>/include/images/banner.png">
		</div> --%>

		<!-- swiper start -->
		<div class="swiper-container" id="mySwiper">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<a href="http://www.baidu.com"> <img class="bannerImg"
						src="<%=path%>/include/images/banner4.png">
					</a>
				</div>
				<div class="swiper-slide">
					<a href="http://www.163.com"> <img class="bannerImg"
						src="<%=path%>/include/images/banner2.png">
					</a>
				</div>
				<div class="swiper-slide">
					<a href="http://www.qq.com"> <img class="bannerImg"
						src="<%=path%>/include/images/banner3.png">
					</a>
				</div>
			</div>
			<div class="swiper-pagination"></div>
		</div>
		<!-- swiper end -->


		<!-- <div class="cloud"></div> -->

		<div class="ranks">
			<div class="header"></div>
			<div class="rank">
				<div class="header">
					<div class="label bbs"></div>
					<div class="text">热门人气社区TOP8</div>
				</div>
				<div class="grid">
					<c:forEach items="${types }" var="type">
						<div class="item">
							<div class="card" onclick="evens.onTypeClick(${type.typeId})">
								<div class="icon">
									<img
										src="<%=path%>/include/images/itemType/g${type.typeId}.png">
								</div>
								<div class="text">${type.nameGroup}</div>
							</div>
						</div>
					</c:forEach>

				</div>
			</div>

			<!-- 分享达人 -->
			<div class="rank">
				<div class="header">
					<div class="label users"></div>
					<div class="text">集赞达人大头贴</div>
				</div>
				<div class="grid">
					<c:forEach items="${users }" var="user">
						<div class="item">
							<div class="card" onclick="evens.onUserClick('${user.openid}')">
								<div class="headimg">
									<img src="${user.headImgUrl }">
									<div class="like-bar">
										<div class="icon"></div>
										<div class="text">${user.likeNum }</div>
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
					<div class="text">小编推荐良心好物</div>
				</div>
				<div class="list">
					<c:forEach items="${items }" var="ii">
						<div class="item" onclick="evens.onItemClick(${ii.item.itemId})">
							<div class="info">
								<div class="head-img">
									<img src="${ii.ownerInfo.headImgUrl }">
								</div>
								<div class="name">${ii.item.name}</div>
								<div class="description">${ii.item.description}</div>

								<div class="wishBar">
									<div class="tag"></div>
									<div class="wishItem">想换: ${ii.item.wishItem}</div>
								</div>
								<div class="img-view">
									<img src="${ii.item.firstItemCenterImgUrl }">
								</div>
							</div>

							<div class="foot-bar">
								<div class="text">${ii.item.likeNum}</div>
								<div class="likeNumTag"></div>
							</div>

							<div class="itemType" style="background: ${ii.item.itemTypeBg}">
								<div class="img">
									<img
										src="<%=path%>/include/images/itemType/t${ii.item.itemType}.png">
								</div>
								<div class="text">${ii.item.itemTypeName}</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

		</div>

		<div class="table_bar">
			<input type="hidden" id="homeUrl" value="${tableUrls.homeUrl }">
			<input type="hidden" id="marketUrl" value="${tableUrls.marketUrl }">
			<input type="hidden" id="postUrl" value="${tableUrls.postUrl }">
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
					<div class="label">市集</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_post">
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
	<script src="<%=path%>/include/swiper/swiper-3.3.1.jquery.min.js"></script>
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
		
		var evens = {};

		$(function() {
			Core.tableBar.init(0);

			Core.resizeContainer();

			$(window).resize(function() {
				Core.resizeContainer();
			});
			
			Core.mySwiper.loadSwiper();
			
			evens.onItemClick = function(id) {
				var eo = Core.getQueryString("eo");
				location.href = "item/item?ii=" + id + "&eo=" + eo;
			}
			
			evens.onUserClick = function(id){
				var eo = Core.getQueryString("eo");
				location.href = "ta?eo="+eo+"&oi="+id;
			}
			
			evens.onTypeClick = function(id){
				var eo = Core.getQueryString("eo");
				location.href = "market?eo="+eo+"&it="+id;
			}
		});
	</script>

</body>
</html>