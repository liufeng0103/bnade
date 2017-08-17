<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>物品市场价排行 - BNADE魔兽世界</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-header">市场价排行</h1>
					<table id="itemMarketTbl" class="table table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th>id</th>
								<th>名称</th>
								<th>市场价</th>
								<th>数量</th>
								<th>参考服务器数</th>
								<th>说明</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<p id="msg"></p>
					<div class="text-center">
						<button id="prePageBtn" type="button" class="btn btn-primary">上一页</button>
						<button id="nextPageBtn" type="button" class="btn btn-primary">下一页</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/itemMarket.js"></script>
</body>
</html>