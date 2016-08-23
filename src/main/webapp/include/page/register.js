var jsapiparam = {};
jsapiparam.appid = document.getElementById("appid").value;
jsapiparam.nonceStr = document.getElementById("nonceStr").value;
jsapiparam.timeStamp = document.getElementById("timeStamp").value;
jsapiparam.signature = document.getElementById("signature").value;

jsapiparam.isWxJsApiReady = false;

wx.config({
	debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
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
var countdownInterval;
var countdown = 0;

$(function() {
	
	evens.ev1 = null;

	evens.onSendCheckCodeClick = function() {
		if (evens.ev1 == null) {
			evens.ev1 = new Date().getTime();
		} else {
			var ev2 = new Date().getTime();
			if (ev2 - evens.ev1 < 500) {
				evens.ev1 = ev2;
				return;
			} else {
				evens.ev1 = ev2;
			}
		}

		if (countdown <= 0) {
			var eo = $("#eo").val();
			var mobile = $("#mobile").val();

			if (mobile == "") {
				alert("手机号码不能为空");
			}

			$.ajax({
				url : "../sendCheckCode",
				data : {
					eo : eo,
					mobile : mobile
				},
				type : "POST",
				success : function(data) {
					if (data.errCode == "0") {
						countdown = 60;
						countdownInterval = setInterval(function() {
							setCountdown();
						}, 1000);
						alert(data.errMsg);
					} else {
						alert(data.errMsg);
					}
				}
			});

		}
	}

	evens.onSubmitClick = function() {
		if (evens.ev1 == null) {
			evens.ev1 = new Date().getTime();
		} else {
			var ev2 = new Date().getTime();
			if (ev2 - evens.ev1 < 500) {
				evens.ev1 = ev2;
				return;
			} else {
				evens.ev1 = ev2;
			}
		}

		var eo = $("#eo").val();
		var checkCode = $("#checkCode").val();
		var mobile = $("#mobile").val();

		if (mobile == "") {
			alert("手机号码不能为空");
		}

		if (!eo || eo == null || eo == "") {
			alert("未授权使用者");
			return;
		}

		if (checkCode == "") {
			alert("验证码不能为空");
			return;
		}

		var time = new Date().getTime();

		$.ajax({
			url : "registe?_t=" + time,
			data : {
				checkCode : checkCode,
				eo : eo,
				mobile : mobile
			},
			type : "POST",
			success : function(data) {
				if (data.errCode == "0") {
					alert(data.errMsg);
				} else {
					alert("register fail");
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
	} else {
		clearInterval(countdownInterval);
	}
	$(".countdown").html(text);
}