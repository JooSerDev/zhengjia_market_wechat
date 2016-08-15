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
});

wx.ready(function() {
	jsapiparam.isWxJsApiReady = true;
	wx.hideOptionMenu();
});

wx.error(function(res) {
	wx.hideOptionMenu();
});

var evens = {};
var page = {
	page : 1,
	nextPage : true
}
var isOwner = 1;

$(function() {
	Core.myScroll.loadingNextAction = loadNextPage;
	Core.myScroll.load();
	Core.myScroll.loadingNextAction();

	evens.onExchangeClick = function(url) {
		if (isOwner == 1) {
			location.href = url;
		}
	}

	$("#ownerBtn").on("click", function() {
		$("#ownerBtn").addClass("active");
		$("#changerBtn").removeClass("active");
		$(".exchanges").empty();
		isOwner = 1;
		page.page = 1;
		page.nextPage = true;
		refreshLoadingBar();
		Core.myScroll.loadingNextAction();
	});

	$("#changerBtn").on("click", function() {
		$("#changerBtn").addClass("active");
		$("#ownerBtn").removeClass("active");
		$(".exchanges").empty();
		isOwner = 0;
		page.page = 1;
		page.nextPage = true;
		refreshLoadingBar();
		Core.myScroll.loadingNextAction();
	});

});

var Exchange = function(exchangeInfo) {
	var e = exchangeInfo.exchange;
	var target = exchangeInfo.target;
	var targetItem = exchangeInfo.targetItem
	var user = exchangeInfo.user;
	var userItem = exchangeInfo.userItem;
	var toAgreeExchangePath = exchangeInfo.toAgreeExchangePath;

	var exchange = {
		myDesc : userItem.description,
		myItemImg : userItem.firstItemCenterImgUrl,
		targetDesc : targetItem.description,
		targetItemImg : targetItem.firstItemCenterImgUrl,
		targetHeadImg : target.headImgUrl,
		targetNickname : target.nickname,
		exchangeState : e.exchangeState == "exchanged" ? "已成功" : "进行中",
		displayTime : e.displayTime,
		toAgreeExchangePath : toAgreeExchangePath
	};
	return exchange;
}

var buildDom = function(data) {
	var exchanges = data.exchanges;
	var next = data.nextPage;
	if (next == "1") {
		page.nextPage = true;
	} else {
		page.nextPage = false;
	}

	if (exchanges.length > 0) {
		var html = $("#exchange_template").html();
		for (var i = 0; i < exchanges.length; i++) {
			var ei = exchanges[i];
			var exchange = new Exchange(ei);
			var temp = new String(html);
			temp = Core.HtmlReplace(temp, exchange);
			$(".exchanges").append(temp);
		}
	}
}

var refreshLoadingBar = function() {
	if (page.nextPage) {
		$("#pull_up_bar").show();
		$(".pull-up-text").html("加载中");
	} else {
		$("#pull_up_bar").hide();
		$(".pull-up-text").html("没有更多了");
	}
}

var loadNextPage = function() {
	var eo = Core.getQueryString("eo");
	var pageNum = page.page;
	if (page.nextPage) {
		$.ajax({
			url : "loadMyExchanges",
			data : {
				eo : eo,
				page : pageNum,
				isOwner : isOwner
			},
			type : "POST",
			success : function(data) {
				Core.myScroll.isLoadingNextPage = false;
				if (data.errCode == "0") {
					page.page = page.page + 1;
					buildDom(data.data);
					refreshLoadingBar();
				} else {
					alert("load fail");
				}
			}
		});
	}
}
