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
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header"><a class="back" href="javascript:void(0)"><span class="glyphicon glyphicon-chevron-left"></span>搜索排行榜</a></h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
		      		<div class="panel-heading"><span class="glyphicon glyphicon-list"></span> 今日</div>
		      		<table class="table table-hover">
		      			<thead>
		      				<tr>
		      					<th>#</th>
		      					<th>物品</th>
		      					<th>搜索人数</th>
		      					<th>市场价</th>
		      					<th>搜索历史</th>
		      				</tr>
		      			</thead>
		      			<tbody>
		      			<c:forEach items="${items}" var="item" varStatus="status">
		      				<tr>
		      					<td>${offset + status.count}</td>
		      					<td><a href="/page/auction/item/${item.itemId}"><img src="http://content.battlenet.com.cn/wow/icons/18/${item.icon}.jpg" alt="${item.name}"/> ${item.name}</a></td>
		      					<td>${item.queried}</td>
		      					<td>${item.price}</td>
		      					<td><a href="/page/item/searchHistory/${item.itemId}">查看</a></td>
		      				</tr>
		      			</c:forEach>
		      			</tbody>
		      		</table>
					<nav class="text-center">
						<ul class="pagination">
							<li><a href="/page/item/hotSearch">首页</a></li>
							<c:if test="${offset != 0}">
								<li><a href="/page/item/hotSearch?offset=${offset - limit}">上一页</a></li>
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
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script>
	$(".back").click(function(){
		history.back();
	});
	</script>
</body>
</html>