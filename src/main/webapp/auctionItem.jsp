<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>${item.name} | BNADE</title>
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
						<img class="img-rounded" alt="${item.name}" src="http://content.battlenet.com.cn/wow/icons/56/${item.icon}.jpg"> ${item.name}
					</a>				
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-stats"></span> [${item.name}]在所有服务器图表
					</div>
					<div class="panel-body">
						<div id="itemAuctionContainer"></div>
					</div>
				</div>
			</div>
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading"><span class="glyphicon glyphicon-list"></span> [${item.name}]在所有服务器详情</div>
					<div class="panel-body">
						<table id="itemAuctionTable" class="table table-striped table-bordered table-hover" width="100%">
							<thead>
								<tr>
									<th>#</th>
									<th>一口价</th>
									<th>数量</th>
									<th>服务器</th>
									<th>卖家</th>
									<th>剩余时间</th>
									<th>更新时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${auctions}" var="auction" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${auction.price}</td>
									<td>${auction.quantity}</td>
									<td>${auction.realmName}</td>
									<td><a href="/page/auction/owner/${auction.urlOwner}/${auction.realmId}">${auction.owner}</a></td>
									<td>${auction.timeLeftCN}</td>
									<td><fmt:formatDate pattern="HH:mm" value="${auction.updated}"/></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
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
		$('#itemAuctionTable').DataTable({
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
		$("#itemAuctionContainer").highcharts({
	        chart:{zoomType: 'xy',ignoreHiddenSeries: false},
	        title:{text: "${item.name}"},
	        xAxis:[{categories: ${labels},crosshair: true,labels: {
	            enabled: false
	        }}],
	        yAxis:[{
	        		title:{
	               		text:'数量',style:{color: Highcharts.getOptions().colors[8]}
	           		},
	           		labels:{
	                	format:'{value}个',style:{color: Highcharts.getOptions().colors[1]}
		            },
		            opposite: true
		        },{
		        	labels:{
		        		format:'{value}G',style:{color: Highcharts.getOptions().colors[1]}
		        	},
		           	title:{
		           		text:'价格',style:{color: Highcharts.getOptions().colors[0]}
		        	},		 
		        	min: 0,
		        	max: ${maxBuyout},
	        	}],
	        tooltip: {
	            shared: true
	        },
	        series:[{
	            name:'数量',
	            type: "column",	
	            data: ${quantities},
	            color: Highcharts.getOptions().colors[8],
	            marker: {
	                enabled: false
	            },
	            tooltip:{valueSuffix:'个'}
	       	},{
	            name:'价格',
	            type: "spline",
	            yAxis:1,
	            data: ${price},
	            color: Highcharts.getOptions().colors[0],
	            marker: {
	                enabled: false
	            },
	            tooltip:{valueSuffix:'G'}
	        }]
	    });
	</script>
</body>
</html>