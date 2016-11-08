<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					<input id="realmInput" type="text" placeholder="服务器名" class="form-control">
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
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>服务器</th>
							<th>数据更新时间</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="realmBody">
						<c:forEach items="${realms}" var="realm">
							<tr>
								<td>${realm.realmName}</td>
								<td>${realm.updated}前</td>
								<td>
									<button class="btn btn-sm btn-danger removeRealm" realmid="${realm.realmId}">
										<span class="glyphicon glyphicon-remove"></span> 删除
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@ include file="includes/footer.jsp"%>
	<%@ include file="includes/script.jsp"%>
	<script>
	var BN = window.BN || {};

	BN.UserRealmUI = new function() {
		var self = this;

		var getRow = function(realmId,realmName,lastModified) {
			var row = "<tr><td>{realmName}</td><td>{lastModified}</td><td><button class='btn btn-sm btn-danger removeRealm' realmid='{realmId}'><span class='glyphicon glyphicon-remove'></span> 删除</button></td></tr>";
			return row.replace("{realmId}",realmId).replace("{realmName}",realmName).replace("{lastModified}",lastModified);
		}
		
		self.init = function() {
			var realmSelect = $("#realmSlt");
			// 加载服务器数据
			var realms = BN.Realm.getAll();
			var optionsHtml = "";
			for (var i in realms) {
				var realm = realms[i];
				optionsHtml += "<option value='"+realm.id+"'>"+realm.connected+"</option>";
			}
			realmSelect.html(optionsHtml);
			$("#searchBtn").click(function(){
				$("#msg").html("");
				var realmName = $("#realmInput").val();
				if (realmName === "") {
					$("#msg").html("请设置一个服务器");
				} else {
					var machRealms = BN.Realm.getRealmsByName(realmName);
					if (machRealms.length === 0) {
						$("#msg").html("未找到服务器："+realmName);
					} else {
						realmSelect.html("");
						for (var i in machRealms) {
							var realm = machRealms[i];
							realmSelect.append("<option value='"+realm.id+"'>"+realm.connected+"</option>");
						}
					}
				}
			});
			$("#addRealmBtn").click(function(){
				var realmId = realmSelect.val();
				var realmName = realmSelect.find("option:selected").text();
				if (realmId === "") {
					$("#msg").html("出错：无法获取服务器信息");
				} else {
					var result = BN.Resource.addUserRealm(realmId);
					if (result.code === 0) {
						$("#msg").html(realmName+"添加成功");
						$("#realmBody").append(getRow(realmId,realmName,""));
						$(".removeRealm").click(function(){
							if (confirm("确定要删除该服务器吗？")) {
								var result = BN.Resource.deleteUserRealm($(this).attr("realmId"));
								if (result.code === 0) {
									$("#msg").html("删除成功");
									$(this).parent().parent().remove();
								} else {
									$("#msg").html("删除服务器出错："+result.message);
								}
							}
						});
					} else {
						$("#msg").html("添加服务器出错："+result.message);
					}
				}
			});
			$(".removeRealm").click(function(){
				if (confirm("确定要删除该服务器吗？")) {
					var result = BN.Resource.deleteUserRealm($(this).attr("realmId"));
					if (result.code === 0) {
						$("#msg").html("删除成功");
						$(this).parent().parent().remove();
					} else {
						$("#msg").html("删除服务器出错："+result.message);
					}
				}
			});
		};
		
		self.test = function() {};
	};
	BN.UserRealmUI.init();
	</script>
</body>
</html>