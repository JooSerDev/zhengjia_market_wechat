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
<title>正佳分享市集</title>
<link rel="stylesheet"
	href="<%=path%>/include/css/finalAgreeExchange.css">
</head>
<body>
	<div class="cloud"></div>

	<div id="agreeView">

		<p>新品亦或旧物，不以等价求公平</p>
		<p>一次偶然的邂逅，可能带来一份美好的友谊</p>
		<p>尊重人品、诚信交换</p>

		<div class="btn-view">
			<div class="bv">
				<button onclick="evens.onSubmitClick(0)" class="s1">拒绝交换</button>
			</div>
			<div class="bv">
				<button onclick="evens.onSubmitClick(1)" class="s2">同意交换</button>
			</div>
		</div>
	</div>

	<div id="locationView">
		<div class="show-view">请选择期望交换地点：</div>
		<div class="select-view">
			<div class="item header" onclick="evens.onItemClick(1)">
				<div class="text" id="mo_text_1">［到正佳广场交换］正佳广场是一个舒适的公共场所，可确保交换过程的安全；您可在营业时间到xxxx服务台，我们将竭诚协助您完成交换</div>
				<div class="checkbox">
					<input type="radio" id="radio_1" name="radio-1-set"
						class="regular-radio" /><label for="radio_1">
				</div>
			</div>
			<div class="item foot" onclick="evens.onItemClick(2)">
				<div class="text" id="mo_text_2">［自选地点交换］您可与对方自行协商交换地点，按照《分享市集免责声明》有关条款，正佳不承担交换双方相关风险责任</div>
				<div class="checkbox">
					<input type="radio" id="radio_2" name="radio-1-set"
						class="regular-radio" /><label for="radio_2">
				</div>
			</div>
		</div>

		<div class="btn-view" onclick="evens.onLocationClick()">确定</div>

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
		var modelId = 0;

		$(function() {
			evens.onItemClick = function(itemId) {
				$(".regular-radio").attr("checked", false);
				$(".regular-radio").removeClass("checked");
				$("#radio_" + itemId).attr("checked", true);
				$("#radio_" + itemId).addClass("checked");
				modelId = itemId;
			}

			evens.onLocationClick = function() {
				var location = "";
				if (modelId == 1) {
					location = "zhengjia";
				} else {
					location = "other";
				}

				var ee = $("#ee").val();

				$.ajax({
					url : "exchangeLocation",
					data : {
						ee : ee,
						location : location
					},
					type : "POST",
					success : function(data) {
						if (data.errCode == "0") {
							window.location.href = document.referrer;
						} else {
							alert("地点选择失败，情稍候再试");
						}
					}
				});
			}

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
							if (flag == 1) {
								alert("交换成功");
							} else {
								alert("已拒绝");
							}
							$("#locationView").show();
							$("#agreeView").hide();
							window.location.href = document.referrer;
						} else {
							alert("交换失败，情稍候再试");
						}
					}
				});
			}
		});
	</script>
</body>
</html>