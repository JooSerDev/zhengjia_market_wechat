/**
 * Created by Shawnpoon on 2016/6/6.
 */
var Core = {};

Core.myScroll = {
	container : undefined,
	isLoaded : false,
	isLoadingNextPage : false,
	isNextPage : false,
	loadingBar : undefined,
	loadingBarOffset : 0,
	endOffset : 0,
	loadingNextAction : function() {
	},
	refresh : function() {
	},
	load : function() {
		Core.myScroll.container = document.getElementById("container");
		Core.myScroll.loadingBar = document.getElementById("pull_up_bar");
		if (Core.myScroll.loadingBar != undefined
				&& Core.myScroll.loadingBar != null) {
			Core.myScroll.loadingBarOffset = Core.myScroll.loadingBar.offsetHeight;
		}

		var screenHeight = window.screen.availHeight;
		if (Core.myScroll.container != undefined
				&& Core.myScroll.container != null) {
			Core.myScroll.container.style.margin = "0 0  "
					+ Core.myScroll.endOffset + "px 0";
			Core.myScroll.container.style.minHeight = screenHeight + "px";

			$(window)
					.scroll(
							function() {
								if (document.body.scrollTop > Core.myScroll.container.offsetHeight
										- screenHeight
										- Core.myScroll.loadingBarOffset
										&& !Core.myScroll.isLoadingNextPage) {
									Core.myScroll.isLoadingNextPage = true;
									Core.myScroll.loadingNextAction();
								}

								// $("#fab").animate({
								// left : '-50px'
								// }, 1000);
							});

		}

	}
};

// IScroll
Core.iScroll = {
	myScroll : {},
	isLoaded : false,
	isNextPage : true,
	screenHeight : 0,
	pullDownBar : undefined,
	pullUpBar : undefined,
	pullDownOffset : 0,
	pullUpOffset : 0,
	loadIScroll : function() {
		Core.iScroll.pullDownBar = document.getElementById("pull_down_bar");
		Core.iScroll.pullUpBar = document.getElementById("pull_up_bar");
		if (Core.iScroll.pullDownBar != undefined
				&& Core.iScroll.pullDownBar != null) {
			Core.iScroll.pullDownOffset = Core.iScroll.pullDownBar.offsetHeight;
		}

		if (Core.iScroll.pullUpBar != undefined
				&& Core.iScroll.pullUpBar != null) {
			Core.iScroll.pullUpOffset = Core.iScroll.pullUpBar.offsetHeight;
		}

		var wrapperDOM = document.getElementById("wrapper");

		if (wrapperDOM) {
			Core.iScroll.screenHeight = wrapperDOM.offsetHeight;
			var scrollerH = document.getElementById("scroller").offsetHeight;

			if (Core.iScroll.screenHeight >= scrollerH
					&& !Core.iScroll.isNextPage) {
				Core.iScroll.pullUpBar.style.display = "none";
				Core.iScroll.isNextPage = false;
			}
		}

		Core.iScroll.myScroll = new iScroll(
				'wrapper',
				{
					scrollbarClass : 'myScrollbar',
					preventDefault : false,
					click : true,
					useTransition : false,
					topOffset : Core.iScroll.pullDownOffset,
					onRefresh : function() {
						if (Core.iScroll.pullDownBar != undefined
								&& Core.iScroll.pullDownBar != null) {
							if (Core.iScroll.pullDownBar.className
									.match('loading')) {
								Core.iScroll.pullDownBar.className = '';
								Core.iScroll.pullDownBar
										.querySelector('.pull-down-text').innerHTML = '下拉刷新...';
							}
						}

						if (Core.iScroll.pullUpBar != undefined
								&& Core.iScroll.pullUpBar != null) {
							if (Core.iScroll.pullUpBar.className
									.match('loading')) {
								Core.iScroll.pullUpBar.className = '';
								Core.iScroll.pullUpBar
										.querySelector('.pull-up-text').innerHTML = '加载更多...';
							}
						}

					},
					onScrollMove : function() {
						if (Core.iScroll.pullDownBar != undefined
								&& Core.iScroll.pullDownBar != null) {
							if (this.y > 5
									&& !Core.iScroll.pullDownBar.className
											.match('flip')) {
								Core.iScroll.pullDownBar.className = 'flip';
								Core.iScroll.pullDownBar
										.querySelector('.pull-down-text').innerHTML = '你敢松手试试';
								this.minScrollY = 0;
							} else if (this.y < 5
									&& Core.iScroll.pullDownBar.className
											.match('flip')) {
								Core.iScroll.pullDownBar.className = '';
								Core.iScroll.pullDownBar
										.querySelector('.pull-down-text').innerHTML = '下拉刷新...';
								this.minScrollY = -Core.iScroll.pullDownOffset;
							}
						}

						if (Core.iScroll.pullUpBar != undefined
								&& Core.iScroll.pullUpBar != null
								&& Core.iScroll.isNextPage) {
							if (this.y < (this.maxScrollY - 5)
									&& !Core.iScroll.pullUpBar.className
											.match('flip')) {
								Core.iScroll.pullUpBar.className = 'flip';
								Core.iScroll.pullUpBar
										.querySelector('.pull-up-text').innerHTML = '你敢松手试试';
								this.maxScrollY = this.maxScrollY;
							} else if (this.y > (this.maxScrollY - 5)
									&& Core.iScroll.pullUpBar.className
											.match('flip')) {
								Core.iScroll.pullUpBar.className = '';
								Core.iScroll.pullUpBar
										.querySelector('.pull-up-text').innerHTML = '加载更多...';
								this.maxScrollY = -Core.iScroll.pullUpOffset;
							}
						}
					},
					onScrollEnd : function() {
						if (Core.iScroll.pullDownBar != undefined
								&& Core.iScroll.pullDownBar != null) {
							if (Core.iScroll.pullDownBar.className
									.match('flip')) {
								Core.iScroll.pullDownBar.className = 'loading';
								Core.iScroll.pullDownBar
										.querySelector('.pull-down-text').innerHTML = '加载中...';
								Core.iScroll.pullDownAction();
							}
						}

						if (Core.iScroll.pullUpBar != undefined
								&& Core.iScroll.pullUpBar != null
								&& Core.iScroll.isNextPage) {

							if (Core.iScroll.pullUpBar.className.match('flip')) {
								Core.iScroll.pullUpBar.className = 'loading';
								Core.iScroll.pullUpBar
										.querySelector('.pull-up-text').innerHTML = '加载中...';
								Core.iScroll.pullUpAction();
							}

						}

					}
				});
		Core.iScroll.isLoaded = true;
		Core.iScroll.onLoaded();
	},
	pullDownAction : function() {
		setTimeout(function() {
			Core.iScroll.myScroll.refresh();
		}, 400);
	},
	pullUpAction : function() {
		setTimeout(function() {
			Core.iScroll.myScroll.refresh();
		}, 400);
	},
	onLoaded : function() {
	}
};

Core.mySwiper = {
	screenProportion : 380 / 750,
	loadSwiper : function() {
		var swiperDOM = $("#mySwiper");
		var width = swiperDOM.width();
		var height = width * Core.mySwiper.screenProportion;
		swiperDOM.height(height);

		mySwiper = new Swiper('#mySwiper', {
			pagination : '.swiper-pagination',
			paginationClickable : false,
			autoplay: 5000
		});
	}
}

Core.tableBar = {
	pageIndex : 0,
	init : function(idx) {
		Core.tableBar.pageIndex = idx;

		var homeURL = $("#homeUrl").val();
		var marketUrl = $("#marketUrl").val();
		var postURL = $("#postUrl").val();
		var meURL = $("#meUrl").val();

		$("#table_bar_home").on("click", function() {
			if (Core.tableBar.pageIndex != 0) {
				location.href = homeURL;
			}
		});

		$("#table_bar_market").on("click", function() {
			if (Core.tableBar.pageIndex != 1) {
				location.href = marketUrl;
			}
		});

		$("#table_bar_post").on("click", function() {
			if (Core.tableBar.pageIndex != 2) {
				location.href = postURL;
			}
		});

		$("#table_bar_me").on("click", function() {
			if (Core.tableBar.pageIndex != 3) {
				location.href = meURL;
			}
		});

	}
}

Core.getQueryString = function(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}

Core.HtmlReplace = function(html, obj) {
	for ( var k in obj) {
		var regexp = eval("/\{" + k + "\}/ig");
		html = html.replace(regexp, obj[k]);
	}
	return html;
}

Core.resizeContainer = function() {
	var screenHeight = window.screen.availHeight;
	var container = document.getElementById("container");
	container.style.minHeight = screenHeight + "px";
}

Date.prototype.format = function(format) {
	var date = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S+" : this.getMilliseconds()
	};
	if (/(y+)/i.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + '')
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in date) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? date[k]
					: ("00" + date[k]).substr(("" + date[k]).length));
		}
	}
	return format;
}

/**
 * 获取未读消息
 */
Core.myMsg = {
	total:0,
	userId:0,
	msgId:0,
	msgType:[],
	msgCount:[],
	initMsg:function(url,eo,divId){
		var reqUrl="/fxsj/wechat/user/msg?eo="+eo;
		$.get(reqUrl,function(data){
			if(data.errCode=="0"){
				var userMsg = data.data.userMsg;
				Core.myMsg.total = userMsg.total;
				Core.myMsg.userId = userMsg.userid;
				Core.myMsg.msgId = userMsg.msgid;
				Core.myMsg.msgType = userMsg.msgtype.split(",");
				Core.myMsg.msgCount = userMsg.msgcount.split(",");
				if(userMsg!=null && userMsg.total>0){
					$("#"+divId).show();
				}else{
					$("#"+divId).hide();
				}
			}
		});
	},
	readOneMsg:function(url,eo,msgType){
		
	},
	readSameMsg:function(url,eo,msgType){//阅读一个类型的消息
		var reqUrl="/fxsj/wechat/user/readSameMsg?eo="+eo+"&msgType="+msgType;
		$.get(reqUrl,function(data){
			if(data.errCode=="0"){
				return;
			}
		});
	},
	readAllMsg:function(url,eo,divId){//一次阅读所有消息
		var reqUrl="/fxsj/wechat/user/readAllMsg?eo="+eo;
		$.get(reqUrl,function(data){
			if(data.errCode=="0"){
				$("#"+divId).hide();
			}
		});
	},
	receiveMsg:function(url,eo,msgType){//收到消息
		var reqUrl="/fxsj/wechat/user/receiveMsgs?eo="+eo+"&msgType="+msgType;
		$.get(reqUrl,function(data){
			if(data.errCode=="0"){
				return;
			}
		});
	}
}

// document.addEventListener('touchmove', function(e) {
// e.preventDefault();
// }, false);
// document.addEventListener('DOMContentLoaded', Core.iScroll.loadIScroll,
// false);
