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
var page = {
	page : 1,
	nextPage : true
}

$(function() {
	Core.resizeContainer();

	$(window).resize(function() {
		Core.resizeContainer();
	});

	loadCommentNext();
	Core.myScroll.loadingNextAction = loadCommentNext;
	Core.myScroll.load();

	$("#toComment").on("click", function() {
		$("#container").hide();
		$(".comment-post-view").show();
	});

	$("#onSendReport").on("click", function() {
		var eo = Core.getQueryString("eo");
		var ii = $("#ii").val();

		$.ajax({
			url : "../sendReport",
			data : {
				eo : eo,
				ii : ii
			},
			type : "POST",
			success : function(data) {
				if (data.errCode == "0") {
					alert("comment success");
				} else {
				}
			}
		});
	});

	$("#commentCancelBtn").on("click", function() {
		$("#container").show();
		$(".comment-post-view").hide();
	});

	$("#commentSubmitBtn").on("click", function() {
		var eo = Core.getQueryString("eo");
		var ii = $("#ii").val();
		var content = $("#itemComment").val();

		$.ajax({
			url : "itemComment",
			data : {
				eo : eo,
				ii : ii,
				content : content
			},
			type : "POST",
			success : function(data) {
				if (data.errCode == "0") {
					alert("comment success");
					location.reload();
				} else {
					alert(data.errMsg);
				}
			}
		});
	});

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
					var likeCount = $("#likeCount").html();
					var num = new Number(likeCount);
					num = num + 1;
					$("#likeCount").html(num);
					// alert("Like success");
				} else {
					alert(data.errMsg);
				}
			}
		});
	});

	evens.onHeadImgClick = function(oi) {
		var eo = Core.getQueryString("eo");
		location.href = "../ta?eo=" + eo + "&oi=" + oi;
	}

});

var Comment = function(comment) {
	var cm = comment.comment;
	var u = comment.user;
	var ct = {
		headimg : u.headImgUrl,
		nickname : u.nickname,
		time : new Date(cm.createTime).format("yyyy-MM-dd hh:mm"),
		comment : cm.comment
	}
	return ct;
}

var buildCommentsDOMs = function(comments) {
	if (comments && comments.length) {
		var html = $("#item_comment_template").html();
		for (var i = 0; i < comments.length; i++) {
			var comment = comments[i];
			var temp = new String(html);
			var ct = new Comment(comment);
			temp = Core.HtmlReplace(temp, ct);
			$(".comments").append(temp);
		}
	}
}

var refreshLoadingBar = function() {
	if (page.nextPage) {
		$(".loading").html("加载中");
	} else {
		$(".loading").html("没有更多了");
	}
}

var loadCommentNext = function() {
	var ii = $("#ii").val();
	var pageNum = page.page;
	if (page.nextPage) {
		$.ajax({
			url : "loadComment",
			data : {
				pageNum : pageNum,
				ii : ii
			},
			type : "POST",
			success : function(data) {
				Core.myScroll.isLoadingNextPage = false;
				if (data.errCode == "0") {
					var comments = data.data.infos;
					var next = data.data.nextPage;
					if (next == "1") {
						page.nextPage = true;
					} else {
						page.nextPage = false;
					}
					page.page = page.page + 1;
					buildCommentsDOMs(comments);
					refreshLoadingBar();
				} else {
					alert(data.errMsg);
				}
			}
		});
	}
}
