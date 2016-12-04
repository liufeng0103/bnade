<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>${owner} | ${realmName} | BNADE</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					<a class="back" href="javascript:void(0)">
						<span class="glyphicon glyphicon-chevron-left"></span>
						${owner} | ${realmName}
					</a>				
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<table id="ownerAuctionTable" class="table table-hover" data-page-length="25">
					<thead>
						<tr>
							<th>物品</th>
							<th>是否被压</th>
							<th>压价玩家</th>
							<th>说明</th>
							<th>单价</th>
							<th>数量</th>
							<th>一口价</th>
							<th>竞价</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${auctions}" var="auction" varStatus="status">
						<tr>
							<td>
								<a href="/page/auction/item/${auction.item}">
									<img src="http://content.battlenet.com.cn/wow/icons/18/${auction.icon}.jpg" alt="${auction.name}"/> ${auction.name}
								</a>
							</td>
							<c:if test="${auction.owner == auction.owner2}">
							<td class="text-success">未被压</td>
							<td></td>
							</c:if>
							<c:if test="${auction.owner != auction.owner2}">
							<td  class="text-danger">被压</td>
							<td><a href="/page/auction/owner/${auction.owner2}/${realmId}">${auction.owner2}</a></td>
							</c:if>
							<td>${auction.bonus}</td>
							<td class="price">${auction.unitBuyoutGold}</td>
							<td>${auction.quantity}</td>
							<td class="price">${auction.buyoutGold}</td>
							<td class="price">${auction.bidGold}</td>
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
	$('#ownerAuctionTable').DataTable({
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
	$(".back").click(function() {
		history.back();
	});
	</script>
</body>
</html>