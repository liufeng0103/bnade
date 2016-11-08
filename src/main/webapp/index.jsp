<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界物价查询</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h2>BNADE魔兽世界</h2>
			<p>魔兽世界各服务器物品的价格走势和所有服务器的价格比较查询，有兴趣的点击进入按钮</p>
			<p>
				<a href="/itemQuery.jsp" role="button"
					class="btn btn-primary btn-lg">进入 &raquo;</a>
			</p>
		</div>
	</div>
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-4">
					<h2>物价查询</h2>
					<p>魔兽世界物价查询,走势,全服务器价格比较</p>
					<p>
						<a href="/itemQuery.jsp" role="button" class="btn btn-default">进入
							&raquo;</a>
					</p>
				</div>
				<div class="col-md-4">
					<h2>宠物查询</h2>
					<p>魔兽世界全服务器宠物各成长类型价格查询</p>
					<p>
						<a href="/petQuery.jsp" role="button" class="btn btn-default">进入
							&raquo;</a>
					</p>
				</div>
				<div class="col-md-4">
					<h2>时光徽章</h2>
					<p>魔兽世界时光徽章实时价格查询,24小时价格波动,历史价格查询</p>
					<p>
						<a href="/wowtoken.jsp" role="button" class="btn btn-default">进入
							&raquo;</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>