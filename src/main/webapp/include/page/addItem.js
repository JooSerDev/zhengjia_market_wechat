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
});

wx.ready(function() {
	jsapiparam.isWxJsApiReady = true;
	wx.hideOptionMenu();
});

wx.error(function(res) {
	console.log(res);
});

var evens = {};

var localIdsList = [];
var imgServerIds = [];
var imgUrls = [];

$(function() {

	evens.onCameraClick = function() {
		if (jsapiparam.isWxJsApiReady) {

			wx.chooseImage({
				count : 9, // 默认9
				sizeType : [ 'original', 'compressed' ], // 可以指定是原图还是压缩图，默认二者都有
				sourceType : [ 'album', 'camera' ], // 可以指定来源是相册还是相机，默认二者都有
				success : function(res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
					if (localIds.length > 0) {
						for (var i = 0; i < localIds.length; i++) {
							localIdsList.push(localIds[i]);
						}
						uploadImages();
					}
				}
			});
		}
	}

	evens.onSubmitClick = function() {
		var itemName = $("#itemName").val();
		var itemDesc = $("#itemDesc").val();
		var itemType = $("#itemType").val();
		var wishItem = $("#wishItem").val();
		var eo = Core.getQueryString("eo");
		
		if(itemType == "0"){
			alert("未选择分类");
			return;
		}

		if (!eo || eo == null || eo == "") {
			alert("未授权使用者");
			return;
		}

		if (wishItem == "") {
			alert("想换为空");
			return;
		}

		if (itemDesc == "") {
			alert("介绍为空");
			return;
		}

		if (imgUrls.length > 0) {
			var url = "";
			for (var i = 0; i < imgUrls.length; i++) {
				url = url + imgUrls[i] + ";";
			}

			var time = new Date().getTime();

			$.ajax({
				url : "saveItem?_t=" + time,
				data : {
					itemType : itemType,
					itemDesc : itemDesc,
					imgs : url,
					eo : eo,
					itemName : itemName,
					wishItem : wishItem
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

		} else {
			alert("没有图片");
			return;
		}
	}

});

var uploadImages = function() {
	if (localIdsList.length > 0) {
		if (jsapiparam.isWxJsApiReady) {
			var localId = localIdsList.pop();
			wx.uploadImage({
				localId : localId, // 需要上传的图片的本地ID，由chooseImage接口获得
				isShowProgressTips : 1, // 默认为1，显示进度提示
				success : function(res) {
					var serverId = res.serverId; // 返回图片的服务器端ID
					imgServerIds.push(serverId);
					uploadImages();
				},
				fail : function(res) {
					alert("upload img fail");
				}
			});
		}
	} else {
		uploadMediaIds();
	}
}

var uploadMediaIds = function() {
	if (imgServerIds.length > 0) {
		var mediaIds = "";
		var mediaIdSize = imgServerIds.length;
		for (var i = 0; i < mediaIdSize; i++) {
			mediaIds = mediaIds + imgServerIds.pop() + ";";
		}

		var time = new Date().getTime();

		$.ajax({
			url : "uploadMediaIds?_t=" + time,
			data : {
				mediaIds : mediaIds
			},
			type : "POST",
			success : function(data) {
				if (data.errCode == "0") {
					var urls = data.data.urls;
					if (urls.length > 0) {
						for (var i = 0; i < urls.length; i++) {
							var url = urls[i];
							imgUrls.push(url);
							var html = '<div class="item-img"><img src="' + url
									+ '"></div>';
							$(".cameraBtnView").before(html);
						}
					}
				} else {
					alert("upload fail");
				}
			}
		});
	}
}