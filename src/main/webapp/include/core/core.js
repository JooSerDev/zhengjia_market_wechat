/**
 * Created by Shawnpoon on 2016/6/6.
 */
var Core = {};

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

		Core.iScroll.screenHeight = document.getElementById("wrapper").offsetHeight;
		var scrollerH = document.getElementById("scroller").offsetHeight;

		if (Core.iScroll.screenHeight >= scrollerH && !Core.iScroll.isNextPage) {
			Core.iScroll.pullUpBar.style.display = "none";
			Core.iScroll.isNextPage = false;
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
	}
};

Core.mySwiper = {
	screenProportion : 1 / 2,
	loadSwiper : function() {
		var swiperDOM = $("#mySwiper");
		var width = swiperDOM.width();
		var height = width * Core.mySwiper.screenProportion;
		swiperDOM.height(height);

		mySwiper = new Swiper('#mySwiper', {
			pagination : '.swiper-pagination',
			paginationClickable : false
		});
	}
}

Core.tableBar = {
	pageIndex : 0,
	init : function(idx) {
		Core.tableBar.pageIndex = idx;

		var homeURL = $("#homeUrl").val();
		var meURL = $("#meUrl").val();

		$("#table_bar_home").on("click", function() {
			if (Core.tableBar.pageIndex != 0) {
				location.href = homeURL;
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

document.addEventListener('touchmove', function(e) {
	e.preventDefault();
}, false);
document.addEventListener('DOMContentLoaded', Core.iScroll.loadIScroll, false);