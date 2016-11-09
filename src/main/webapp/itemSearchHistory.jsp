<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>${item.name}搜索历史</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header"><a class="back" href="javascript:void(0)"><span class="glyphicon glyphicon-chevron-left"></span><img class="img-rounded" alt="${item.name}" src="http://content.battlenet.com.cn/wow/icons/56/${item.icon}.jpg"> ${item.name}</a></h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-stats"></span> [${item.name}] 搜索历史图表
					</div>
					<div class="panel-body">
						<div id="historyContainer"></div>
					</div>
				</div>
			</div>
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-th-list"></span> [${item.name}] 搜索历史详情
					</div>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th>搜索人数</th>
								<th>日期</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${items}" var="history" varStatus="status">
								<tr>
									<td>${offset + status.count}</td>
									<td>${history.queried}</td>
									<td>
										<fmt:formatDate type="date" value="${history.date}"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highstock.js"></script>
	<script>
		$(".back").click(function(){
			history.back();
		});
		Highcharts.setOptions({
			lang : {
				months : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
						'9月', '10月', '11月', '12月' ],
				shortMonths : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
						'9月', '10月', '11月', '12月' ],
				weekdays : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六', ]
			}
		});
		
		var data = ${data};

		$('#historyContainer')
				.highcharts(
						'StockChart',
						{
							rangeSelector : {
								selected : 1,
								inputEnabled : false,
								buttons : [ {
									type : 'week',
									count : 1,
									text : '周'
								}, {
									type : 'month',
									count : 1,
									text : '月'
								}, {
									type : 'all',
									text : '全部'
								} ]
							},
							credits : {
								enabled : false
							},
							navigator : {
								enabled : false
							},
							scrollbar : {
								enabled : false
							},
			
							xAxis : {
								type : 'datetime',
								dateTimeLabelFormats : {
									second : '%H:%M:%S',
									minute : '%H:%M',
									hour : '%H:%M',
									day : '%m-%d',
									week : '%m-%d',
									month : '%m',
									year : '%Y'
								}
							},
							series : [ {
								name : '搜索人数',
								type : 'area',
								data : data,
								tooltip : {
									valueDecimals : 0,
									valueSuffix : ''
								},
								fillColor : {
									linearGradient : {
										x1 : 0,
										y1 : 0,
										x2 : 0,
										y2 : 1
									},
									stops : [
											[
													0,
													Highcharts.getOptions().colors[0] ],
											[
													1,
													Highcharts
															.Color(
																	Highcharts
																			.getOptions().colors[0])
															.setOpacity(0).get(
																	'rgba') ] ]
								},
								threshold : null
							} ]
						});
	</script>
</body>
</html>