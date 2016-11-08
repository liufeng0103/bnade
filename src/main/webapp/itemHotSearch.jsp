<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>搜索排行榜</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="page-content container">
			<div class="panel panel-default">
	      		<div class="panel-heading">今日搜索排行榜</div>
	      		<table class="table table-hover">
	      			<thead>
	      				<tr>
	      					<th>#</th>
	      					<th>物品</th>
	      					<th>人数</th>
	      					<th>市场价</th>
	      					<th>ID</th>
	      				</tr>
	      			</thead>
	      			<tbody>
	      			<c:forEach items="${items}" var="item" varStatus="status">
	      				<tr>
	      					<td>${offset + status.count}</td>
	      					<td><img src="http://content.battlenet.com.cn/wow/icons/18/${item.icon}.jpg"/> ${item.name}</td>
	      					<td>${item.queried}</td>
	      					<td>${item.price}</td>
	      					<td>${item.itemId}</td>
	      				</tr>
	      			</c:forEach>
	      			</tbody>
	      		</table>
				<nav class="text-center">
					<ul class="pagination">
						<li><a href="/page/item/hotSearch">首页</a></li>
						<c:if test="${offset != 0}">
							<li><a href="/page/item/hotSearch?offset=${offset - limit}">下一页</a></li>
						</c:if>
						<c:if test="${offset == 0}">
							<li class="disabled"><a href="#">上一页</a></li>
						</c:if>
						<c:if test="${isLast != 1}">
							<li><a href="/page/item/hotSearch?offset=${offset + limit}">下一页</a></li>
						</c:if>
						<c:if test="${isLast == 1}">
							<li class="disabled"><a href="#">下一页</a></li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
</body>
</html>