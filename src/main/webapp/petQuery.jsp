<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>BNADE魔兽世界宠物价格查询 宠物全服务器价格比较查询 宠物成长类型分类查询</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul id="petQueryList" class="nav nav-sidebar"></ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="col-md-10">
					<form onsubmit="return false;" class="navbar-form">
						<div class="form-inline">
							<div class="form-group">
								<div class="input-group">
									<input id="petName" type="text" placeholder="宠物名"
										class="form-control">
									<div class="input-group-btn">
										<button type="button" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"
											class="btn btn-default dropdown-toggle">
											选择 <span class="caret"></span>
										</button>
										<ul id="petSelectList"
											class="dropdown-menu dropdown-menu-right"></ul>
									</div>
								</div>
							</div>
							<input id="queryBtn" type="button" value="查询"
								class="btn btn-success"> <input id="fuzzyQueryBtn"
								type="button" value="模糊查询" class="btn btn-success"> <input
								type="reset" value="重置" class="btn btn-success">
						</div>
					</form>
					<p id="msg"></p>
					<div id="petResult"></div>
					<p id="queryByUrl"></p>
					<p id="allRealmMsg"></p>
					<div id="allRealmCtlDiv">
						<a id="showAllA" href="javascript:void(0)" class="btn btn-primary">所有服务器+</a>
						<table id="showAllTbl" class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>服务器</th>
									<th>一口价</th>
									<th>卖家</th>
									<th>剩余时间</th>
									<th>宠物等级</th>
									<th>总数量</th>
								</tr>
							</thead>
							<tbody id="showAllBody"></tbody>
						</table>
					</div>
					<p>说明:</p>
					<ul>
						<li>所有宠物不区分等级和品质，默认25级蓝色品质</li>
					</ul>
				</div>
				<div class="col-md-2">
					<ul id="fuzzyItemsList" class="nav nav-sidebar"></ul>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/petQuery.js"></script>
</body>
</html>