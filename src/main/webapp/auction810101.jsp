<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>810等级101可穿戴圣物 | BNADE</title>
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
						810等级101可穿戴圣物
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
							<th>竞价</th>
							<th>一口价</th>
							<th>服务器</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${auctions}" var="auction" varStatus="status">
						<tr>
							<td>${auction.itemObj.name}</td>
							<td class="price">${auction.bid/10000}</td>
							<td class="price">${auction.buyout/10000}</td>
							<td class="realmId" data-realm-id="${auction.realmId}"></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<p>本页面用于查询所有服务器的101等级可以穿戴的圣物，有网友希望提供这类数据的查询，临时使用这个页面, 目前查询以下所有的物品，如果有缺的请联系我添加</p>
				<ul>
					<li>诺达尼尔精华</li>
					<li>纳萨拉斯手稿</li>
					<li>瓦格里仪式</li>
					<li>力量法则</li>
					<li>双头怪碎骨</li>
					<li>鲜血图腾的堕落</li>
					<li>梦境林地幼芽</li>
					<li>沙拉尼尔幼芽</li>
					<li>晶化魔力</li>
					<li>法术火焰油膏</li>
				</ul>
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
	$(".realmId").each(function() {
		var realmId = $(this).attr("data-realm-id");
		var realmName = Realm.getNameById(parseInt(realmId));
		$(this).text(realmName);
		//$(this).html(Realm.getNameById($(this).attr("data-realm-id")));
		//$(this).text();
	});
	
	</script>
</body>
</html>