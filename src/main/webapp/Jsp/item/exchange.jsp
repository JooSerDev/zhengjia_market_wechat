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
<link rel="stylesheet" href="<%=path%>/include/css/exchange.css">
</head>
<body>

	<div class="container" id="container">
		<div class="cloud"></div>

		<div class="target-item-view">
			<div class="info">
				<div class="headimg">
					<img class="target-head-img" src="${targetUser.headImgUrl }">
				</div>
				<div class="text">${targetItem.description }</div>
			</div>
			<div class="wish">
				<div class="icon"></div>
				<div class="text">${targetItem.wishItem}</div>
			</div>
			<div class="img">
				<img src="${targetItem.firstItemCenterImgUrl }">
			</div>
		</div>
		<div class="divider-view">请选择交换物品</div>
		<div class="items">
			<c:forEach items="${items}" var="item">
				<div class="item" id="item_${item.itemId }"
					onclick="evens.onItemClick(${item.itemId })">
					<div class="time">${item.displayTime }</div>
					<div class="info">
						<div class="desc">${item.description}</div>
						<div class="img">
							<img src="${item.firstItemCenterImgUrl }">
						</div>
					</div>
					<div class="radio-view">
						<input type="radio" id="radio_${item.itemId }" name="radio-1-set"
							class="regular-radio" /><label for="radio_${item.itemId }"></label>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="btn-view">
			<button onclick="evens.onSubmitClick()">提交申请</button>
		</div>

	</div>

	<input type="hidden" id="targetItemId" value="${targetItem.itemId}">
	<input type="hidden" id="eo" value="${eo}">

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
			appId : jsapiparam.appid, 
			timestamp : jsapiparam.timeStamp, 
			nonceStr : jsapiparam.nonceStr, 
			signature : jsapiparam.signature,
			jsApiList : [ "hideOptionMenu","showOptionMenu"]
		});

		wx.ready(function() {
			jsapiparam.isWxJsApiReady = true;
			wx.hideOptionMenu();
		});

		wx.error(function(res) {
			wx.hideOptionMenu();
		});

		var evens = {};
		var myItemId = 0;

		$(function() {
			Core.myScroll.load();
			
			evens.onItemClick = function(itemId){
				myItemId = itemId;
				$(".regular-radio").attr("checked",false);
				$(".regular-radio").removeClass("checked");
				$("#radio_" + itemId).attr("checked",true);
				$("#radio_" + itemId).addClass("checked");
			}

			evens.onSubmitClick = function() {
				var eo = $("#eo").val();
				var targetItemId = $("#targetItemId").val();

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
							alert("交换成功");
							location.href = "../market?eo="+eo;
						} else {
							alert(data.errMsg);
						}
					}
				});
			}
		});
	</script>
</body>
</html>