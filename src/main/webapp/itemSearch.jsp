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
			<div class="col-lg-12">
				<table id="itemTable" class="table table-hover" data-page-length="25">
					<thead>
						<tr>
							<th>#</th>
							<th>物品</th>
							<th>等级</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${items}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<c:if test="${item.id == 82800}">
							<td><a href="/petQuery.jsp?name=${item.name}"><img src="http://content.battlenet.com.cn/wow/icons/18/${item.icon}.jpg" alt="${item.name}"/> ${item.name}</a></td>
							</c:if>
							<c:if test="${item.id != 82800}">
							<td><a href="/itemQuery.jsp?itemName=${item.name}"><img src="http://content.battlenet.com.cn/wow/icons/18/${item.icon}.jpg" alt="${item.name}"/> ${item.name}</a></td>
							</c:if>
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
	$('#itemTable').DataTable({
		responsive : true,
		"language" : {
			"lengthMenu" : "每页显示 _MENU_ 条记录",
			"zeroRecords" : "数据未找到",
			"info" : "第_PAGE_页, 共_PAGES_页",
			"infoEmpty" : "找不到数据",
			"infoFiltered" : "(总数 _MAX_条)",
			"search" : "搜索:",
			"paginate" : {
				"first" : "首页",
				"last" : "末页",
				"next" : "下一页",
				"previous" : "上一页"
			},
		}
	});
	</script>
</body>
</html>