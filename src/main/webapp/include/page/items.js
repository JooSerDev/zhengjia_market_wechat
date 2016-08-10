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

var keyword = "";

$(function() {
	Core.myScroll.endOffset = 61;
	loadNextPage();
	Core.myScroll.loadingNextAction = loadNextPage;
	// Core.myScroll.loadingNextAction();
	Core.myScroll.load();

	Core.tableBar.init(1);

	$("#searchBtn").on("click", function() {
		keyword = $("#searchInput").val();
		$(".items").empty();
		page.page = 1;
		page.nextPage = true;
		loadNextPage();
	});

	evens.onItemClick = function(id) {
		var eo = Core.getQueryString("eo");
		location.href = "item/item?ii=" + id + "&eo=" + eo;
	}
});

var Item = function(itemId, head_img, nickname, displayTime, description,
		recommended, imgHtml, wishItem, markNum, likeNum, itemTypeName) {
	var item = {
		itemId : itemId,
		head_img : head_img,
		nickname : nickname,
		displayTime : displayTime,
		description : description,
		recommended : recommended,
		imgHtml : imgHtml,
		wishItem : wishItem,
		markNum : markNum,
		likeNum : likeNum,
		itemTypeName : itemTypeName
	};
	return item;
}

var buildItemDOMs = function(iteminfos) {
	if (iteminfos && iteminfos.length > 0) {

		var html = $("#item_template").html();
		var imgHtml = $("#item_img_template").html();
		for (var i = 0; i < iteminfos.length; i++) {
			var iteminfo = iteminfos[i];

			var temp = new String(html);
			var imgtemp = new String(imgHtml);
			var imgDomHtml = "";

			if (iteminfo.item && iteminfo.ownerInfo) {
				var itemImg = iteminfo.item.itemCenterImgUrls;
				if (itemImg) {
					var imgs = itemImg.split(";");
					if (imgs.length > 0) {
						for (var j = 0; j < imgs.length; j++) {
							var imgUrl = imgs[j];
							if (imgUrl != '') {
								var obj = {
									url : imgUrl
								};
								imgDomHtml = imgDomHtml
										+ Core.HtmlReplace(imgtemp, obj);
							}

						}
					}
				}
				var owner = iteminfo.ownerInfo;
				var k = new Item(iteminfo.item.itemId, owner.headImgUrl,
						owner.nickname, iteminfo.item.displayTime,
						iteminfo.item.description, iteminfo.item.isRecommended,
						imgDomHtml, iteminfo.item.wishItem,
						iteminfo.item.markNum, iteminfo.item.likeNum,
						iteminfo.item.itemTypeName);

				temp = Core.HtmlReplace(temp, k);
				$(".items").append(temp);
			}
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
			url : "item/loadItems",
			data : {
				eo : eo,
				page : pageNum,
				keyword : keyword
			},
			type : "POST",
			success : function(data) {
				Core.myScroll.isLoadingNextPage = false;
				if (data.errCode == "0") {
					var items = data.data.iteminfos;
					var next = data.data.nextPage;
					if (next == "1") {
						page.nextPage = true;
					} else {
						page.nextPage = false;
					}
					page.page = page.page + 1;
					if (items.length > 0) {
						buildItemDOMs(items);
						refreshLoadingBar();
					}
				} else {
					alert("load fail");
				}
			}
		});
	}
}
