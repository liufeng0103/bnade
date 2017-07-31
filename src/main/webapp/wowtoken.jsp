<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>时光徽章 历史价格 - BNADE魔兽世界</title>
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
				<li>wowtoken.info关闭了国服时光徽章的数据接口，询问了他们的官方也说不再支持国服，所以过一阵将关闭该页面，对大家带来的不便非常抱歉</li>
				<li>数据来源<a href="https://wowtoken.info" target="_blank">https://wowtoken.info</a></li>
			</ul>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highstock.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highcharts-more.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/modules/solid-gauge.js"></script>
	<script src="js/wowtoken.js"></script>
</body>
</html>