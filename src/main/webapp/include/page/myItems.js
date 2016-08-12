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

$(function() {
	loadNextPage();
	Core.myScroll.loadingNextAction = loadNextPage;
	Core.myScroll.load();

	$("#addItem").on("click", function() {
		var eo = Core.getQueryString("eo");
		location.href = "addItem?eo=" + eo;
	});
});

var Item = function(imgUrl, state, desc, createtime) {
	var stateStr = state == "yes" ? "可交换" : "未审核";
	var item = {
		imgUrl : imgUrl,
		state : stateStr,
		desc : desc,
		createtime : createtime
	};
	return item;
}

var buildDom = function(items) {
	var html = $("#item_template").html();
	for (var i = 0; i < items.length; i++) {
		var item = new Item(items[i].firstItemCenterImgUrl,
				items[i].approvalStatus, items[i].description,
				items[i].displayTime);
		var temp = new String(html);
		temp = Core.HtmlReplace(temp, item);
		$(".items").append(temp);
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
			url : "loadMyItems",
			data : {
				eo : eo,
				page : pageNum
			},
			type : "POST",
			success : function(data) {
				Core.myScroll.isLoadingNextPage = false;
				if (data.errCode == "0") {
					page.page = page.page + 1;
					var next = data.data.nextPage;
					if (next == "1") {
						page.nextPage = true;
					} else {
						page.nextPage = false;
					}
					var items = data.data.myItems;
					if (items.length > 0) {
						buildDom(items);
						refreshLoadingBar();
					}
				} else {
					alert("load fail");
				}
			}
		});
	}
}
