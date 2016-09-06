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
<link rel="stylesheet" href="<%=path%>/include/css/addItem.css">
</head>
<body>
	<div class="container" id="container">
		<div id="mainContainer">
			<div class="cloud"></div>

			<div class="list input-view">
				<div class="item">
					<div class="item-name-view">
						<input type="text" class="input-block" placeholder="宝贝名称（最多13字）"
							id="itemName" maxlength="13">
					</div>
					<div class="selector-view">
						<select id="itemType" class="selector">
							<option value="0">宝贝分类</option>
							<c:forEach items="${itemTypes}" var="it">
								<option value="${it.typeId }">${it.name }</option>
							</c:forEach>
						</select>
					</div>

				</div>
			</div>

			<div class="list input-view">
				<div class="item textarea">
					<textarea rows="8" placeholder="宝贝故事（描述）" id="itemDesc"
						maxlength="500"></textarea>
				</div>
				<div class="item">
					<div class="temp-view" onclick="evens.onModelBtnClick()">不会写？点击套用模版</div>
				</div>
			</div>

			<div class="img-group-view">
				<div class="cameraBtnView" onclick="evens.onCameraClick()">
					<div class="icon"></div>
				</div>
			</div>

			<div class="list input-view">
				<div class="item wishItem">
					<input type="text" placeholder="期望交换的物品" id="wishItem">
				</div>
			</div>


			<div class="submitBtnView">
				<button class="btn" onclick="evens.onSubmitClick()">提交审核</button>
			</div>

			<div class="ps-text">宝贝发布需后台审核通过后才可见</div>
		</div>

		<div id="modelContainer" style="display: none;">
			<div class="list input-view show-view">
				<div class="item textItem">选择你喜欢的模版</div>
			</div>

			<div class="list modelList">
				<div class="item header" onclick="evens.onItemClick(1)">
					<div class="text" id="mo_text_1">[求学的故事]十年寒窗苦读，终于如愿进入大学校园，面对四年的大学生活首次远离家乡，父母的叮嘱渐渐远去，临行时父亲一句话也没有说，从他那早已泛黄破败的包中掏出一个精致的礼盒交到我手中；接触的那一瞬间父亲粗糙的大手让我感到心里一阵酸楚，踏上远行的列车，打开父亲交给我的礼盒，发现里面是自己喜欢的钢笔，父亲的期盼顿时变得清晰起来……回想很久……</div>
					<div class="checkbox">
						<input type="radio" id="radio_1" name="radio-1-set"
							class="regular-radio" /><label for="radio_1">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(2)">
					<div class="text" id="mo_text_2">[第一份工资]毕业后很久都没有工作，求职，被拒，再求职，再被拒；不容易得到一份工作让自己感动，发工资哪天的感动至今难以忘怀，买了自己人生第一部手机，接通电话的那一瞬间母亲的声音在耳边想起，感动，感慨，思绪万千，那个手机保存至今；对于我，已然不能用金钱价值衡量，愿有缘人可以给它一个归属……</div>
					<div class="checkbox">
						<input type="radio" id="radio_2" name="radio-1-set"
							class="regular-radio" /><label for="radio_2">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(3)">
					<div class="text" id="mo_text_3">[ta送我的……]物似人非，往事已矣，这个皮夹已经随我度过了很多很多，但往事不堪回首，很多事不想再提；对于它的价值无法衡量，它承载的回忆太多，而我已经不是那个我，皮夹安静的躺在抽屉里许久，之前是我不愿放弃不愿前行；现在终于拨开云雾见日出，我已然不需要它了……</div>
					<div class="checkbox">
						<input type="radio" id="radio_3" name="radio-1-set"
							class="regular-radio" /><label for="radio_3">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(4)">
					<div class="text" id="mo_text_4">[书，读书……]浓浓的墨香，泛黄的纸张，陪伴我太多美好时光，高中、大学、工作，自己慢慢长大，不知不觉已经积攒了很多，都不知该往哪放；搬家的时候曾觉得不方便但又不舍得将它们丢弃，有些书早已不再阅读，偶尔打开书柜看到它们，发现就是自己的成长路线图。人生还有很多路要走还有很多未知，寻有缘人……</div>
					<div class="checkbox">
						<input type="radio" id="radio_4" name="radio-1-set"
							class="regular-radio" /><label for="radio_4">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(5)">
					<div class="text" id="mo_text_5">[孤独的世界]不知道从什么时候开始，自己喜欢上素模，喜欢按照自己的喜好上色；一件件小巧的素模经过自己加工后变得更加活灵活现的感觉真的很好，这个过程是孤独的，是另类的，是自己的，朋友都说我很宅，有时间都在家里琢磨上色，很多时候也想放弃，但是抬头看看这个世界后我又转身回到孤独，希望也有朋友喜欢一起分享。</div>
					<div class="checkbox">
						<input type="radio" id="radio_5" name="radio-1-set"
							class="regular-radio" /><label for="radio_5">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(6)">
					<div class="text" id="mo_text_6">[那一盏灯]多少天坐在书桌前，不知不觉已经10年了；老爸说要翻新一下房子，顺便换掉家里的一些老物件，看着桌上那盏台灯，斑驳的身躯已经不再像往日，琉璃灯罩依旧那么的明亮；老爸说：这个台灯已经有进30年的历史，陪他也走过不少岁月，现在要退休了还有点不舍得呢，愿有人愿意好好待他。</div>
					<div class="checkbox">
						<input type="radio" id="radio_6" name="radio-1-set"
							class="regular-radio" /><label for="radio_6">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(7)">
					<div class="text" id="mo_text_7">[一个球拍]有人说刘国梁这个胖子不懂乒乓球，我就呵呵了。回想起那个时代，我应还在家乡读书，小伙伴们都喜欢在放学后打上一两局乒乓球，刚开始的时候用一块木板就可以打好久，大家依旧玩的很开心。老妈过节送我的球拍，让我高兴了很久，那时候还不知道什么正胶反胶，直拍横拍；只觉得自己有球拍让很多小伙伴羡慕不已，长大以后偶尔会拿出那个球拍打一会……后来才知道是一个反胶横拍</div>
					<div class="checkbox">
						<input type="radio" id="radio_7" name="radio-1-set"
							class="regular-radio" /><label for="radio_7">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(8)">
					<div class="text" id="mo_text_8">[做自己]自己其实没有化妆的习惯，去年圣诞节和闺密去香港旅游；在她的唆使下决定尝试一下，买了这盒Doir的圣诞特别版；尝试打扮一下自己，结果发现自己果然不合适；搞的自己不像自己，立刻放弃闲置在家，还不如给合适的人，每个人都有自己的风格，还是做自己比较好</div>
					<div class="checkbox">
						<input type="radio" id="radio_8" name="radio-1-set"
							class="regular-radio" /><label for="radio_8">
					</div>
				</div>
				<div class="item" onclick="evens.onItemClick(9)">
					<div class="text" id="mo_text_9">[长裙]某天、某人、某地，逛商场的时候买的，自己本不太穿裙子，觉得这条裙子比较特别穿起来还算OK，于是就买了自己人生的第一条裙子，也就逛街穿了几次；发生了一些事，后来它就被我忘记在衣柜里了，整理物品的时候发现了它，觉得自己还是不习惯，至于为什么已然不重要了。</div>
					<div class="checkbox">
						<input type="radio" id="radio_9" name="radio-1-set"
							class="regular-radio" /><label for="radio_9">
					</div>
				</div>
				<div class="item foot" onclick="evens.onItemClick(10)">
					<div class="text" id="mo_text_10">[​老相机]它不知道什么时候开始就一直安静的在柜子里摆着，记得小时候爸妈带着我去游乐场都是用它给我拍照，现在家里那本相册已经很久没有新照片了，停留在那个时间；数码时代到来，这个老相机早已没人去使用了；还记得那个时候去相馆取照片的心情，客厅里充满大家的欢声笑语，我自己也是通过它学会了怎么拍照，什么是光圈、快门，手动对焦，构图……</div>
					<div class="checkbox">
						<input type="radio" id="radio_10" name="radio-1-set"
							class="regular-radio" /><label for="radio_10">
					</div>
				</div>
			</div>

			<div class="submitBtnView">
				<button class="btn" onclick="evens.onModelSubmitClick()">使用模板</button>
			</div>

		</div>
	</div>

	<input type="hidden" id="appid" value="${jsapi.appid}">
	<input type="hidden" id="nonceStr" value="${jsapi.nonceStr}">
	<input type="hidden" id="timeStamp" value="${jsapi.timeStamp}">
	<input type="hidden" id="signature" value="${jsapi.signature}">

	<script src="<%=path%>/include/jquery/jquery.min.js"></script>
	<script src="<%=path%>/include/core/core.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script src="<%=path%>/include/page/addItem.js"></script>
</body>
</html>