<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界全服务器拍卖物品数据排行 拍卖行玩家数量</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<h3>所有服务器拍卖排行</h3>
			<div id="auctionQuantityContainer">
				<table class="table table-striped table-condensed">
					<thead>
						<tr>
							<th>#</th>
							<th>服务器</th>
							<th><select class="tblHeadSelect"><option>类型</option>
									<option>pve</option>
									<option>pvp</option></select></th>
							<th><div id="sort1" class="tblHeadBtn">
									拍卖总数<span class="glyphicon glyphicon-sort-by-attributes-alt"
										aria-hidden="true"></span>
								</div></th>
							<th><div id="sort2" class="tblHeadBtn">
									拍卖行玩家数<span class="glyphicon glyphicon-sort" aria-hidden="true"></span>
								</div></th>
							<th><div id="sort3" class="tblHeadBtn">
									物品种类<span class="glyphicon glyphicon-sort" aria-hidden="true"></span>
								</div></th>
							<th><div id="sort4" class="tblHeadBtn">
									更新时间<span class="glyphicon glyphicon-sort" aria-hidden="true"></span>
								</div></th>
						</tr>
					</thead>
					<tbody id="tblBody">
					</tbody>
				</table>
			</div>
			<p>说明:</p>
			<ul>
				<li>BNADE交流QQ群:518160038</li>
				<li>目前国服总共170个服务器</li>
			</ul>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/auctionQuantity.js?rev=@@hash"></script>
</body>
</html>