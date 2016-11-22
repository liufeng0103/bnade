<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>物品查询 | BNADE</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					物品查询
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-3 itemClass">
			<div class="panel panel-default">
				<div class="panel-heading">全部</div>
						<c:forEach items="${itemClasses}" var="classes" varStatus="status">
						<div class="panel-group" role="tablist">
							<div class="panel panel-default">
								<div class="panel-heading" role="tab">
									<h4 class="panel-title">
										<a data-toggle="collapse" href="#collapseListGroup${classes.itemClass}" aria-expanded="true" aria-controls="collapseListGroup1">
											${classes.name}
										</a>
									</h4>
								</div>
								<div id="collapseListGroup${classes.itemClass}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="collapseListGroupHeading1" aria-expanded="true">
									<ul class="list-group">
									<c:forEach items="${classes.subclasses}" var="subclasses" varStatus="status">
										<li class="list-group-item"><a href="/page/item/search?itemClass=${classes.itemClass}&subclass=${subclasses.subclass}">${subclasses.name}</a></li>
									</c:forEach>
									</ul>
								</div>
							</div>
						</div>
						</c:forEach>
					
				</div>
			</div>
			<div class="col-lg-9">
				<table class="table table-hover table-bordered">
					<thead>
						<tr class="active">
							<th>#</th>
							<th>物品</th>
							<th>等级</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${items}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td><a href="/page/auction/item/${item.id}"><img src="http://content.battlenet.com.cn/wow/icons/18/${item.icon}.jpg" alt="${item.name}"/> ${item.name}</a></td>
							<td>${item.itemLevel}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/jquery.dataTables.min.js"></script>
	<script src="/js/dataTables.bootstrap.min.js"></script>
	<script src="/js/dataTables.responsive.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highstock.js"></script>
	<script>
	
	</script>
</body>
</html>