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

wx.config({
	debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	appId : jsapiparam.appid, // 必填，公众号的唯一标识
	timestamp : jsapiparam.timeStamp, // 必填，生成签名的时间戳
	nonceStr : jsapiparam.nonceStr, // 必填，生成签名的随机串
	signature : jsapiparam.signature,// 必填，签名，见附录1
	jsApiList : [ "hideOptionMenu", "chooseImage", "uploadImage" ]
// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
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

Core.iScroll.pullUpAction = function() {
	if (page.nextPage) {

	}
}

Core.iScroll.onLoaded = function() {
	var isNextPage = document.getElementById("nextPage").value;
	if (isNextPage == "1") {
		page.nextPage = true;
		var screenHeight = document.getElementById("wrapper").offsetHeight;
		var scrollerH = document.getElementById("scroller").offsetHeight;

		if (screenHeight < scrollerH && page.nextPage) {
			Core.iScroll.pullUpBar.style.display = "block";
			Core.iScroll.isNextPage = true;
		}
	} else {
		Core.iScroll.pullUpBar.style.display = "none";
		Core.iScroll.isNextPage = false;
		page.nextPage = false;
		
	}
}

$(function() {

});

var Item = function(imgUrl, name, desc, createtime, itemId, eo, owner) {
	var item = {
		imgUrl : imgUrl,
		name : name,
		desc : desc,
		createtime : createtime,
		itemId : itemId,
		eo : eo,
		owner : owner
	};
	return item;
}

var loadNextPage = function() {
	if (Core.iScroll.isLoaded) {
		var eo = Core.getQueryString("eo");
		var pageNum = page.page + 1;

		$
				.ajax({
					url : "loadItems",
					data : {
						eo : eo,
						page : pageNum
					},
					type : "POST",
					success : function(data) {
						if (data.errCode == "0") {
							var items = data.data.items;
							if (items.length > 0) {
								page.nextPage = data.data.nextPage == "1" ? true
										: false;

								var html = $("#item_template").html();
								for (var i = 0; i < items.length; i++) {
									var item = new Item(
											items[i].firstItemCenterImgUrl,
											items[i].name,
											items[i].description,
											items[i].displayTime,
											items[i].itemId, eo,
											items[i].ownerNickname);
									var temp = new String(html);
									temp = Core.HtmlReplace(temp, item);
									$(".item-list").append(temp);

									var scrollerH = document
											.getElementById("scroller").offsetHeight;

									if (Core.iScroll.screenHeight < scrollerH
											&& page.nextPage) {
										Core.iScroll.pullUpBar.style.display = "block";
										Core.iScroll.isNextPage = true;
									}
								}
								Core.iScroll.myScroll.refresh();
							}
						} else {
							alert("load fail");
						}
					}
				});
	} else {
		setTimeout(loadNextPage, 100);
	}
}
