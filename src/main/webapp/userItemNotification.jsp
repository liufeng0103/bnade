<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>物品提醒管理</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<h1>物品提醒管理</h1>
				<h3>添加</h3>
				<p>
					服务器请在<a href="userRealm.html">管理服务器页面</a>设置
				</p>
				<form onsubmit="return false;" class="form-inline">
					<input id="itemInput" type="text" placeholder="物品名"
						class="form-control">
					<button id="searchBtn" class="btn btn-primary">搜索</button>
					<label id="msg" class="text-danger"></label>
				</form>
				<p></p>
				<div id="setItemNForm" class="form-inline">
					<span>当</span> <select id="itemSlt" class="form-control"></select><span>在</span>
					<select id="realmSlt" class="form-control"></select><span>的最低一口单价</span>
					<select id="isInvertedSlt" class="form-control">
						<option value="0">低于</option>
						<option value="1">高于</option>
					</select> <input id="goldInput" type="text" placeholder="如：1.25" class="form-control"><span>金时通知我</span>
					<button id="addItemBtn" class="btn btn-primary">
						<span class="glyphicon glyphicon-plus"></span>添加
					</button>
				</div>
				<h3>所有的物品通知</h3>
				<div>
					<button id="delItemNBtn" class="btn btn-sm btn-danger">
						<span class='glyphicon glyphicon-remove'></span> 删除
					</button>
					<button id="enableMailBtn" class="btn btn-sm btn-success">
						<span class='glyphicon glyphicon-envelope'></span> 启用邮件通知
					</button>
					<button id="disableMailBtn" class="btn btn-sm btn-warning">
						<span class='glyphicon glyphicon-minus'></span> 关闭邮件通知
					</button>
				</div>
				<table class="table table-condensed table-hover">
					<thead>
						<tr>
							<th>服务器</th>
							<th>物品</th>
							<th>邮件通知</th>
							<th>高于/低于</th>
							<th>单价一口价</th>
							<th>修改</th>
							<th><input id="selectAllCheckbox" class="checkbox"
								type="checkbox" /></th>
						</tr>
					</thead>
					<tbody id="itemNotificationBody"></tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script src="/js/userItemNotification.js"></script>
</body>
</html>