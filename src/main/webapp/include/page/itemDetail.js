document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);
document.addEventListener('DOMContentLoaded', Core.iScroll.loadIScroll, false);

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
	debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	appId : jsapiparam.appid, // 必填，公众号的唯一标识
	timestamp : jsapiparam.timeStamp, // 必填，生成签名的时间戳
	nonceStr : jsapiparam.nonceStr, // 必填，生成签名的随机串
	signature : jsapiparam.signature,// 必填，签名，见附录1
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
});
