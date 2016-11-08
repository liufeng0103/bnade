<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>管理服务器</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<h1>管理我的服务器</h1>
			<div class="row">
				<h3>添加服务器</h3>
				<form onsubmit="return false;" class="form-inline">
					<input id="realmInput" type="text" placeholder="服务器名"
						class="form-control">
					<button id="searchBtn" class="btn btn-primary">搜索</button>
					<select id="realmSlt" class="form-control"></select>
					<button id="addRealmBtn" class="btn btn-primary">
						<span class="glyphicon glyphicon-plus"></span>添加服务器
					</button>
					<label id="msg" class="text-danger"></label>
				</form>
			</div>
			<div class="row">
				<h3>所有服务器</h3>
				<table id="realmTbl" class="table table-striped">
					<thead>
						<tr>
							<th>服务器</th>
							<th>数据更新时间</th>
							<th></th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/userRealm.js"></script>
</body>
</html>