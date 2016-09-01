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
<meta name="format-detection" content="telephone=no">
<title>正佳分享市集</title>
<link rel="stylesheet" href="<%=path%>/include/css/me.css">
</head>
<body>

	<div class="container" id="container">

		<div class="cloud"></div>

		<div class="me-head">
			<div class="head-img">
				<img src="${user.headImgUrl }">
			</div>
			<div class="nickname">${user.nickname}</div>
			<div class="sex1"></div>

			<div class="info-view">
				<div class="integral-icon"></div>
				<div class="integral">${user.score }</div>
				<div class="line"></div>
				<div class="like-icon"></div>
				<div class="like">${user.likeNum}</div>
				<div class="line"></div>
				<div class="v-icon"></div>
				<div class="v">${user.finishNum}</div>
			</div>
		</div>

		<div class="list-view">
			<div class="label">联系方式</div>
			<div class="text">${user.mobile}</div>
		</div>
		<a href="item/myItems?eo=${eo}">
			<div class="list-view">
				<div class="label">我的宝贝</div>
				<div class="arrow"></div>
				<div class="text">${user.itemNum}</div>
			</div>
		</a> <a href="item/myExchanges?eo=${eo}">
			<div class="list-view">
				<div class="label">我的交换</div>
				<div class="arrow"></div>
				<div class="text">${user.exchangeNum }</div>
			</div>
		</a> <a href="item/addItem?eo=${eo}">
			<div class="add-item">发布宝贝</div>
		</a>

		<div class="score-view">
			<div class="title">积分规则说明</div>
			<div class="desc">
				<p>● 完善个人信息，获10分</p>
				<p>● “点赞”或“留言”，每次加1分；单日累计最多通过“点赞”或“留言”加5分</p>
				<p>● 上传宝贝，每次审核通过加5分</p>
				<p>● 宝贝获小编推荐，每次加5分（在审核通过的基础上额外加分）</p>
				<p>● 每个宝贝，每获得一个点赞得1分；一个宝贝获的点赞积分总共最多10分</p>
				<p>● 收到请求交易的，在10分钟内处理（无论同意或拒绝），每次加5分</p>
				<p>● 请求交易获得对方同意的（无论对方多久响应），每次加5分</p>
				<p>● 到正佳现场完成交易并获“认证”的，双方各加20分</p>
				<p>● 上传的宝贝因内容违规被强制下架，每次扣10分</p>
				<p>● 被管理员执行“清除评论”的用户，每次扣20分</p>
				<p>● 上传的宝贝经现场核实为“信息不实”，每次扣20分</p>
				<p>● 约定在正佳现场交换，但因爽约被投诉的，每次扣30分</p>
				<p>● 经常发违法、不实内容，或是多次爽约的，会被封号</p>
			</div>
		</div>

		<div class="table_bar">
			<input type="hidden" id="homeUrl" value="${tableUrls.homeUrl }">
			<input type="hidden" id="marketUrl" value="${tableUrls.marketUrl }">
			<input type="hidden" id="postUrl" value="${tableUrls.postUrl }">
			<input type="hidden" id="meUrl" value="${tableUrls.meUrl }">

			<div class="nav-view" id="table_bar_home">
				<div class="table_bar_btn ">
					<div class="logo-home"></div>
					<div class="label">首页</div>
				</div>
			</div>
			<div class="nav-view" id="table_bar_market">
				<div class="table_bar_btn ">
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
				<div class="table_bar_btn active">
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
			Core.tableBar.init(3);
		});
	</script>

</body>
</html>