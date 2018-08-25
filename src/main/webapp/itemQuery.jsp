<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界物价查询 24小时内价格波动 历史价格 全服务器价格查询比较</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div id="itemAucsModal" class="modal fade bs-example-modal-lg">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">所有拍卖</h4>
				</div>
				<div class="modal-body">
					<div class="modal-body-content"></div>
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal" class="btn btn-default">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul id="realmQueryList" class="nav nav-sidebar"></ul>
				<ul id="itemQueryList" class="nav nav-sidebar"></ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="col-md-10">
					<form onsubmit="return false;" class="navbar-form">
						<div class="form-inline">
							<div class="form-group">
								<div class="input-group">
									<input id="realm" type="text" placeholder="服务器(选填)"
										class="form-control">
									<div class="input-group-btn">
										<button type="button" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"
											class="btn btn-default dropdown-toggle">
											选择 <span class="caret"></span>
										</button>
										<ul id="realmSelectList"
											class="dropdown-menu dropdown-menu-right"></ul>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<input id="itemName" type="text" placeholder="物品名或ID"
										class="form-control">
									<div class="input-group-btn">
										<button type="button" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"
											class="btn btn-default dropdown-toggle">
											选择 <span class="caret"></span>
										</button>
										<ul id="itemSelectList"
											class="dropdown-menu dropdown-menu-right"></ul>
									</div>
								</div>
							</div>
							<input id="queryBtn" type="button" value="查询"
								class="btn btn-success"> <input id="itemFuzzyQueryBtn"
								type="button" value="模糊查询" class="btn btn-success"> <input
								type="reset" value="重置" class="btn btn-success">
						</div>
					</form>
					<p id="msg"></p>
					<div id="itemListByName"></div>
					<div id="itemDetail"></div>
					<p id="queryByUrl"></p>
					<p id="past24Msg"></p>
					<div id="past24CtlDiv" style="display: none">
						<h2 class="sub-header">总结</h2>
						<table class="table table-striped">
							<thead>
								<tr>
									<th>说明</th>
									<th>最低价格</th>
									<th>最高价格</th>
									<th>平均价格</th>
									<th>平均数量</th>
									<th>最近一次价格</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>24小时内</td>
									<td id="past24MinBuyout"></td>
									<td id="past24MaxBuyout"></td>
									<td id="past24AvgBuyout"></td>
									<td id="past24AvgQuantity"></td>
									<td id="past24LatestBuyout"></td>
								</tr>
								<tr>
									<td>一周内</td>
									<td id="pastWeekMinBuyout"></td>
									<td id="pastWeekMaxBuyout"></td>
									<td id="pastWeekAvgBuyout"></td>
									<td id="pastWeekAvgQuantity"></td>
									<td></td>
								</tr>
								<tr>
									<td>历史信息</td>
									<td id="historyMin"></td>
									<td id="historyMax"></td>
									<td id="historyAvg"></td>
									<td id="historyAvgQuantity"></td>
									<td></td>
								</tr>
								<tr>
									<td>所有服务器</td>
									<td id="allMinBuyout"></td>
									<td id="allMaxBuyout"></td>
									<td id="allAvgBuyout"></td>
									<td id="allAvgQuantity"></td>
									<td></td>
								</tr>
							</tbody>
						</table>
						<div id="past24Container"></div>
					</div>
					<p id="pastWeekMsg"></p>
					<div id="pastWeekCtlDiv">
						<div id="pastWeekContainer"
							style="min-width: 310px; height: 400px; margin: 0 auto"></div>
						<div id="pastWeekBuyoutHeatMapContainer"
							style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>
						<div id="pastWeekQuantityHeatMapContainer"
							style="min-width: 310px; max-width: 800px; height: 400px; margin: 0 auto"></div>
					</div>
					<div id="itemStatisticContainer" style="display: none">
						<p class="message"></p>
						<div id="itemStatisticChart"></div>
					</div>
					<p id="allRealmMsg"></p>
					<div id="allRealmCtlDiv">
						<div id="allRealmContainer" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
						<div id="tableContainer"></div>
					</div>
					<p>说明:</p>
					<ul>
						<!-- <li><a href="/page/auction/s810101">101圣物</a></li> -->
						<li><h2>由于网易数据源关闭，网站暂时无法更新数据，请大家耐心等待恢复，同时也做好最坏的打算，这次的数据关闭跟之前都不一样，可能短时间不再恢复</h2></li>
						<li>添加苏拉玛，瓦里安服务器拍卖数据查询， 十分感谢网友 不改 的帮助</li>
						<li>大家可以在IOS应用商店搜索并下载app 地精酒馆， 感谢网友Lincwee的帮助和制作</li>
						<li>装笼宠物价格查询请点击<a href="/petQuery.jsp">宠物价格</a>页面</li>
						<li>BNADE交流QQ群:518160038</li>
					</ul>
				</div>
				<div class="col-md-2">
					<ul id="fuzzyItemsList" class="nav nav-sidebar"></ul>
					<h4>搜索排行榜</h4>
					<ul role="tablist" class="nav nav-tabs">
						<li role="presentation" class="active"><a href="#dailyHot"
							aria-controls="dailyHot" role="tab" data-toggle="tab"
							style="padding-left: 8px; padding-right: 8px;">每日</a></li>
						<li role="presentation"><a href="#weeklyHot"
							aria-controls="weeklyHot" role="tab" data-toggle="tab"
							style="padding-left: 8px; padding-right: 8px;" class="hotSearchA">每周</a></li>
						<li role="presentation"><a href="#monthlyHot"
							aria-controls="monthlyHot" role="tab" data-toggle="tab"
							style="padding-left: 8px; padding-right: 8px;" class="hotSearchA">每月</a></li>
					</ul>
					<div class="tab-content">
						<div id="dailyHot" class="tab-pane active"></div>
						<div id="weeklyHot" class="tab-pane"></div>
						<div id="monthlyHot" class="tab-pane"></div>
					</div>
					<div data-spy="affix">
						<a href="#top" data-spy="scroll" data-target="body">返回顶部</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/highstock.js"></script>
	<script src="//cdn.bootcss.com/highcharts/4.2.7/modules/heatmap.js"></script>
	<script src="/js/itemQuery.js?v=6"></script>
</body>
</html>