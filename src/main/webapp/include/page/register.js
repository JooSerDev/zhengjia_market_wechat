var jsapiparam = {};
jsapiparam.appid = document.getElementById("appid").value;
jsapiparam.nonceStr = document.getElementById("nonceStr").value;
jsapiparam.timeStamp = document.getElementById("timeStamp").value;
jsapiparam.signature = document.getElementById("signature").value;

jsapiparam.isWxJsApiReady = false;

wx.config({
	debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	appId : jsapiparam.appid, // 必填，公众号的唯一标识
	timestamp : jsapiparam.timeStamp, // 必填，生成签名的时间戳
	nonceStr : jsapiparam.nonceStr, // 必填，生成签名的随机串
	signature : jsapiparam.signature,// 必填，签名，见附录1
	jsApiList : [ "hideOptionMenu" ]
// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

wx.ready(function() {
	jsapiparam.isWxJsApiReady = true;
	wx.hideOptionMenu();
});

wx.error(function(res) {
	console.log(res);
});

var evens = {};
var countdownInterval;
var countdown = 0;

$(function() {

	evens.onSendCheckCodeClick = function() {
		if (countdown <= 0) {
			var eo = $("#eo").val();
			
			$.ajax({
				url : "saveItem?_t=" + time,
				data : {
					itemType : itemType,
					itemDesc : itemDesc,
					imgs : url,
					eo : eo,
					itemName : itemName
				},
				type : "POST",
				success : function(data) {
					if (data.errCode == "0") {
						alert(data.errMsg);
					} else {
						alert("save fail");
					}
				}
			});
			
			countdown = 60;
			countdownInterval = setInterval(() => {
				setCountdown();
			}, 1000);
		}
	}

	evens.onSubmitClick = function() {
		var eo = $("#eo").val();

		if (!eo || eo == null || eo == "") {
			alert("未授权使用者");
			return;
		}

		if (itemName == "") {
			alert("名称为空");
			return;
		}

		var time = new Date().getTime();

		$.ajax({
			url : "saveItem?_t=" + time,
			data : {
				itemType : itemType,
				itemDesc : itemDesc,
				imgs : url,
				eo : eo,
				itemName : itemName
			},
			type : "POST",
			success : function(data) {
				if (data.errCode == "0") {
					alert(data.errMsg);
				} else {
					alert("save fail");
				}
			}
		});

	}

});

var setCountdown = function() {
	var text = "发送验证码";
	countdown--;
	if (countdown >= 0) {
		text = countdown + " s";
	}else{ 
		clearInterval(countdownInterval);
	}
	$(".countdown").html(text);
}
