<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界服务器拍卖物品数据排行 拍卖行玩家数量</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<h3>服务器拍卖排行</h3>
			<div id="tableContainer"></div>
			<p>说明:</p>
			<ul>
				<li>BNADE交流QQ群:518160038</li>
				<li>目前国服总共170个服务器</li>
			</ul>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/auctionQuantity.js?v=1"></script>
</body>
</html>