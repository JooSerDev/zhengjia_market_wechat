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
<meta content="telephone=no" name="format-detection" />
<meta name="viewport" content="width=device-width,user-scalable=no" />
<title>正佳分享市集</title>
<link rel="stylesheet" href="<%=path%>/include/css/agreeExchange.css">
</head>
<body>

	<div class="container" id="container">
		<div class="cloud"></div>

		<div class="my-item-view">
			<div class="name">${userItem.name}</div>
			<div class="text">${userItem.description}</div>
			<div class="img">
				<img src="${userItem.firstItemCenterImgUrl}">
			</div>
		</div>
		<div class="other-item-view"
			onclick="evens.onOtherItemClick(${otherItem.itemId})">
			<div class="change-icon"></div>
			<div class="img">
				<img src="${otherItem.firstItemCenterImgUrl}">
			</div>
			<div class="name">${otherItem.name}</div>
			<div class="text">${otherItem.description}</div>
			<div class="changer-view">
				<div class="line">
					<div class="user">
						<div class="label">交换人</div>
						<div class="headimg">
							<img src="${other.headImgUrl}">
						</div>
						<div class="nickname">${other.nickname}</div>
					</div>
				</div>
				<div class="line">
					<div class="icon"></div>
					<div class="displayTime">${exchange.displayTime}</div>
				</div>
			</div>
		</div>

		<c:if
			test="${isOwner ==  1 and exchange.exchangeState == 'exchanging'}">
			<p class="ps-text">交换请求有效期为10天，请尽快处理</p>
			<p class="ps-text">在10分钟内处理（同意/拒绝）可获5积分</p>
		</c:if>

		<div class="contact-view">
			<div class="label">当前交易状态</div>
			<div class="phone">
				<c:if
					test="${exchange.exchangeState == 'exchanging' and isOwner ==  1}">等待处理</c:if>
				<c:if
					test="${exchange.exchangeState == 'exchanging' and isOwner ==  0}">等待对方处理</c:if>
				<c:if test="${exchange.exchangeState == 'exchanged' }">已同意</c:if>
				<c:if test="${exchange.exchangeState == 'cancel' }">已拒绝</c:if>
			</div>
		</div>

		<div class="contact-view">
			<div class="label">对方联系方式</div>
			<div class="phone">
				<c:if test="${exchange.exchangeState == 'exchanged' }">${other.mobile }</c:if>
				<c:if test="${exchange.exchangeState != 'exchanged' }">同意交换才可显示</c:if>
			</div>
		</div>

		<div class="contact-view">
			<div class="label">期望交换地点</div>
			<div class="phone">
				<c:if test="${exchange.exchangeLocation == 'zhengjia' }">到正佳广场交换</c:if>
				<c:if test="${exchange.exchangeLocation == 'other' }">自选地点交换</c:if>
			</div>
		</div>

		<c:if
			test="${isOwner ==  1 and exchange.exchangeState == 'exchanging'}">
			<div class="btn-view">
				<div class="gotoAgreePageBtn"
					onclick="evens.toFinalAgreePageClick()">同意/拒绝交换</div>
			</div>
		</c:if>

		<c:if test="${exchange.exchangeState == 'exchanged' }">
			<div class="btn-view">
				<div class="gotoAgreePageBtn" onclick="evens.gotoDescTextPage()">点击了解如何与Ta交换？</div>
			</div>
			<div class="btn-view">
				<div class="gotoAgreePageBtn" onclick="evens.rePublish()">交易失败？重新发布我的宝贝</div>
			</div>
		</c:if>

	</div>

	<input type="hidden" id="ee" value="${ee}">
	<input type="hidden" id="isOwner" value="${isOwner }">

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
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

			Core.resizeContainer();

			$(window).resize(function() {
				Core.resizeContainer();
			});

			evens.toFinalAgreePageClick = function() {
				var ee = $("#ee").val();
				location.href = "toFinalAgreeExchange?ee=" + ee;
			}
			evens.gotoDescTextPage = function() {
				location.href = "../../include/core/agreement2.html";
			}
			evens.onOtherItemClick = function(ii){
				var eo = Core.getQueryString("eo");
				location.href = "itemInExchange?eo=" + eo+"&ii="+ii;
			}
			evens.rePublish = function(){
				var ee = $("#ee").val();
				var isOwner = $("#isOwner").val();
				
				$.ajax({
					url : "rePublish",
					data : {
						ee : ee,
						isOwner : isOwner
					},
					type : "POST",
					success : function(data) {
						if (data.errCode == "0") {
							alert("重新提交成功");
						} else {
							alert("重新提交失败，情稍候再试");
						}
					}
				});
			}

		});
	</script>
</body>
</html>