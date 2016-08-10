var jsapiparam = {};
jsapiparam.appid = document.getElementById("appid").value;
jsapiparam.nonceStr = document.getElementById("nonceStr").value;
jsapiparam.timeStamp = document.getElementById("timeStamp").value;
jsapiparam.signature = document.getElementById("signature").value;
jsapiparam.isWxJsApiReady = false;

var shareparam = {};
shareparam.shareTitle = document.getElementById("shareTitle").value;
shareparam.shareImg = document.getElementById("shareImg").value;
shareparam.shareLink = document.getElementById("shareLink").value;
shareparam.shareDesc = document.getElementById("shareDesc").value;

wx.config({
	debug : false,
	appId : jsapiparam.appid,
	timestamp : jsapiparam.timeStamp,
	nonceStr : jsapiparam.nonceStr,
	signature : jsapiparam.signature,
	jsApiList : [ "hideOptionMenu", "showMenuItems", "onMenuShareTimeline",
			"onMenuShareAppMessage" ]
});

wx.ready(function() {
	jsapiparam.isWxJsApiReady = true;

	// 设置显示的菜单
	wx.showMenuItems({
		menuList : [ "menuItem:profile", "menuItem:addContact",
				"menuItem:share:appMessage", "menuItem:share:timeline" ]
	});

	// 分享到朋友圈
	wx.onMenuShareTimeline({
		title : shareparam.shareDesc,
		link : shareparam.shareLink,
		imgUrl : shareparam.shareImg,
		success : function() {
		},
		cancel : function() {
		}
	});

	// 分享到微信朋友
	wx.onMenuShareAppMessage({
		title : shareparam.shareTitle,
		desc : shareparam.shareDesc,
		link : shareparam.shareLink,
		imgUrl : shareparam.shareImg,
		success : function() {
		},
		cancel : function() {
		}
	});

});

wx.error(function(res) {
	wx.hideOptionMenu();
});

var evens = {};

$(function() {
	Core.myScroll.load();
	
	$("#likeBtn").on("click", function() {
		var eo = Core.getQueryString("eo");
		var ii = $("#ii").val();

		$.ajax({
			url : "likeItem",
			data : {
				eo : eo,
				ii : ii
			},
			type : "POST",
			success : function(data) {
				if (data.errCode == "0") {
					alert("Like success");
				} else {
					alert(data.errMsg);
				}
			}
		});
	});
});
