<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="zh">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,user-scalable=no" />
<title>正佳分享市集</title>
<link rel="stylesheet" href="<%=path%>/include/css/itemDetail.css">
</head>
<body>
	<div class="container" id="container">

		<div class="main-view">

			<div class="head-view">
				<div class="head-img"
					onclick="evens.onHeadImgClick('${owner.openid}')">
					<img src="${owner.headImgUrl }">
				</div>
				<div class="info-view">
					<div class="line">
						<div class="nickname">${owner.nickname }</div>
						<div class="state">${item.lockStatus == 'exchanged' ? '已交换':'交换中' }</div>
					</div>
					<div class="line">
						<div class="time">${item.displayTime }</div>
						<div class="itemType">${item.itemTypeName }</div>
					</div>
				</div>
			</div>

			<div class="content-view">
				<div class="itemName">${item.name}</div>
				<div class="itemDescription">${item.description}</div>
				<c:forEach items="${itemImgList }" var="imgUrl">
					<div class="itemImg">
						<img src="${imgUrl }">
					</div>
				</c:forEach>

				<div class="wish">
					<div class="icon"></div>
					<div class="text">想换: ${item.wishItem }</div>
				</div>

				<div class="like-view" id="likeBtn">
					<div class="icon"></div>
					<div class="text" id="likeCount">${item.likeNum }</div>
				</div>
			</div>

			<div class="comment-view">
				<div class="header">
					<div class="count">
						<div class="icon"></div>
						<div class="text">${item.markNum }</div>
					</div>
				</div>
				<div class="comments"></div>
				<div class="loading">加载中</div>
			</div>

			<div class="foot-bar">
				<div class="btns">
					<div class="col-33">
						<div class="submitBtnView">
							<a class="btn" href="javascript:void(0);" id="toComment">写评论</a>
						</div>
					</div>
					<div class="col-66">
						<c:if
							test="${user.user.userId != owner.userId and item.lockStatus != 'exchanged'}">
							<div class="submitBtnView">
								<a class="btn" href="${toExchangeUrl}">我要交换</a>
							</div>
						</c:if>
					</div>
				</div>
				<div class="ps-text">
					<a id="onSendReport" href="javascript:void(0);">违规内容，我要举报</a>
				</div>
			</div>
		</div>
	</div>

	<div class="comment-post-view">
		<div class="comment-input">
			<textarea rows="8" placeholder="评论" id="itemComment" maxlength="500"></textarea>
		</div>
		<div class="post-btn" id="commentSubmitBtn">发表评论</div>
		<div class="post-btn" id="commentCancelBtn">返回</div>
	</div>

	<div class="report-post-view">
		<div class="comment-input">
			<textarea rows="8" placeholder="投诉内容" id="itemRepost" maxlength="500"></textarea>
		</div>
		<div class="post-btn" id="reportSubmitBtn">提交投诉</div>
		<div class="post-btn" id="reportCancelBtn">返回</div>
	</div>

	<template id="item_comment_template">
	<div class="comment">
		<div class="head-img">
			<img src="{headimg}">
		</div>
		<div class="info">
			<div class="line">
				<span>{nickname}</span> &nbsp;&nbsp;<span>{time}</span>
			</div>
			<div class="content">{comment}</div>
		</div>
	</div>
	</template>


	<input type="hidden" id="ii" value="${item.itemId}">

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<input type="hidden" id="shareTitle" value="${share.title}">
	<input type="hidden" id="shareImg" value="${share.imgUrl}">
	<input type="hidden" id="shareLink" value="${share.link}">
	<input type="hidden" id="shareDesc" value="${share.desc}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/itemDetail.js"></script>
</body>
</html>