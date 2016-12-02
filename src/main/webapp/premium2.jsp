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
  margin: 80px 0; /* Space out the Bootstrap <hr> more */
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
		<h1 class="page-header">BNADE 高级版</h1>
		<p>高级版用户将可以使用BNADE额外的功能，下面列表列出了目前高级版用户的功能。</p>
		<p class="text-center">
			<a href="#premium-div" class="btn btn-danger btn-lg">开通高级版</a>
		</p>
		<hr class="featurette-divider">
		<div class="row featurette">
			<div class="col-lg-6">
				<h2 class="featurette-heading">物品提醒</h2>
				<p class="lead">用户可以设置服务器物品的提醒规则，当物品高于或低于某个价格时将第一时间提醒用户。普通用户限制设置5个物品通知，高级版用户将不受限制的使用此功能！</p>
			</div>
			<div class="col-lg-6">
				<img class="img-responsive" alt="物品提醒" src="/images/itemN.png">
			</div>
		</div>
		<hr class="featurette-divider">
		<div class="row" id="premium-div">
			<p class="text-center">目前提供2种方式开通高级版，微信或支付宝扫码支付。</p>
			<div class="col-lg-6 text-center">
				<img src="/images/wx.jpg">
				<p>微信 10元/月</p>
			</div>
			<div class="col-lg-6 text-center">
				<img src="/images/zfb.jpg">
				<p>支付宝  10元/月</p>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>