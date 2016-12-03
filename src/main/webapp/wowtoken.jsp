<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界时光徽章实时价格查询 时光徽章历史价格 时光徽章价格波动</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<p id="msg"></p>
			<div id="wowtokenBuyContainer"></div>
			<div id="wowTokenContainer"></div>
			<p>说明:</p>
			<ul>
				<li class="text-danger">大家可以在IOS应用商店搜索并下载app 地精酒馆， 感谢网友Lincwee的帮助和制作</li>
				<li>数据来源<a href="https://wowtoken.info">https://wowtoken.info</a></li>
				<li>BNADE交流QQ群:518160038</li>
			</ul>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highstock.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highcharts-more.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/modules/solid-gauge.js"></script>
	<script src="js/wowtoken.js?rev=@@hash"></script>
</body>
</html>