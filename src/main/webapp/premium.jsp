<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE 高级版</title>
<%@ include file="includes/link.jsp"%>
<style type="text/css">
/* Featurettes
------------------------- */

.featurette-divider {
  margin: 20px 0; /* Space out the Bootstrap <hr> more */
}

/* Thin out the marketing headings */
.featurette-heading {
  font-weight: 300;
  line-height: 1;
  letter-spacing: -1px;
}

@media (min-width: 768px) {
  .featurette-heading {
    font-size: 50px;
  }
}

@media (min-width: 992px) {
  .featurette-heading {
    margin-top: 120px;
  }
}
</style>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container">
		<div class="row">
			<h1 class="page-header">BNADE 高级版</h1>
			<p>高级版用户将可以使用BNADE额外的功能，下面列表列出了目前高级版用户的功能。</p>
			<p class="text-center">
				<a href="#activeDiv" class="btn btn-danger btn-lg">开通高级版</a>
			</p>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<h3>1. 物品拍卖历史查看</h3>
			<p>用户可以查看物品2天内的详细拍卖数据，以及2个月内的历史数据</p>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<h3>2. 物品提醒</h3>
			<p>用户可以设置服务器物品的提醒规则，当物品高于或低于某个价格时将第一时间提醒用户</p>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<h3>3. 压价查看</h3>
			<p>用户可以添加自己的服务器角色，并查看角色拍卖的物品情况以及哪些物品被压价了</p>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<h3>4. 热门物品查看</h3>
			<p>用户可以查看每天所有的热门物品，并快速的查看这些物品在各服务器的拍卖情况</p>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<h3>5. 玩家排行</h3>
			<p>用户可以查看各服务器，拍卖行玩家的排行情况，了解服务器大地精们的动向</p>
		</div>
		<hr class="featurette-divider">
		<div class="row">
			<h3>6. 新功能优先体验</h3>
			<p>我们将不断的完善和开发新功能，用户将优先体验这部分内容</p>
		</div>
		<hr class="featurette-divider">
		<div id="activeDiv" class="row">
			<h2>开通步骤:</h2>
			<p>高级版为网站增值服务，需要按照以下步骤获取激活码并激活</p>
			<ol>
				<li><a href="https://item.taobao.com/item.htm?id=542791401839">淘宝店</a>购买或者支付宝扫码下方二维码支付</li>
				<li>旺旺联系获取激活码，二维码支付用户在支付宝加好友，然后联系我获取激活码</li>
				<li>网站登录后打开激活高级版页面</li>
				<li>输入激活码激活</li>
			</ol>
			<img alt="支付宝付款" src="/images/zfb20.jpg">
			<p>支付宝 20元/月</p>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>