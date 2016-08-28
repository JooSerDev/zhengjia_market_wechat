var jsapiparam = {};
jsapiparam.appid = document.getElementById("appid").value;
jsapiparam.nonceStr = document.getElementById("nonceStr").value;
jsapiparam.timeStamp = document.getElementById("timeStamp").value;
jsapiparam.signature = document.getElementById("signature").value;

jsapiparam.isWxJsApiReady = false;

wx.config({
    debug: false,
    appId: jsapiparam.appid,
    timestamp: jsapiparam.timeStamp,
    nonceStr: jsapiparam.nonceStr,
    signature: jsapiparam.signature,
    jsApiList: ["hideOptionMenu"]
});

wx.ready(function () {
    jsapiparam.isWxJsApiReady = true;
    wx.hideOptionMenu();
});

wx.error(function (res) {
    wx.hideOptionMenu();
});

var evens = {};
var page = {
    page: 1,
    nextPage: true
}

$(function () {
    loadNextPage();
    Core.myScroll.loadingNextAction = loadNextPage;
    Core.myScroll.load();

    $("#addItem").on("click", function () {
        var eo = Core.getQueryString("eo");
        location.href = "addItem?eo=" + eo;
    });
});

var Item = function (imgUrl, state, desc, createtime, name) {
    var stateStr = "";
    if (state == "wait") {
        stateStr = "审核中";
    } else if (state == "no") {
        stateStr = "审核不通过";
    } else {
        stateStr = "审核以上架";
    }

    var item = {
        imgUrl: imgUrl,
        state: stateStr,
        desc: desc,
        createtime: createtime,
        name: name,
        stateClass: state
    };
    return item;
}

var buildDom = function (items) {
    var html = $("#item_template").html();
    for (var i = 0; i < items.length; i++) {
        var item = new Item(items[i].firstItemCenterImgUrl,
            items[i].approvalStatus, items[i].description,
            items[i].displayTime, items[i].name);
        var temp = new String(html);
        temp = Core.HtmlReplace(temp, item);
        $(".items").append(temp);
    }
}

var refreshLoadingBar = function () {
    if (page.nextPage) {
        $("#pull_up_bar").show();
        $(".pull-up-text").html("加载中");
    } else {
        $("#pull_up_bar").hide();
        $(".pull-up-text").html("没有更多了");
    }
}

var loadNextPage = function () {
    var eo = Core.getQueryString("eo");
    var pageNum = page.page;
    if (page.nextPage) {
        $.ajax({
            url: "loadMyItems",
            data: {
                eo: eo,
                page: pageNum
            },
            type: "POST",
            success: function (data) {
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
