<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%@ include file="includes/meta.jsp"%>
<title>管理我的角色</title>
<%@ include file="includes/link.jsp"%>
</head>
<body>
	<jsp:include page="includes/header.jsp" />
	<div class="container-fluid">
		<div class="container">
			<div class="row">
				<h1 class="page-header">管理我的角色</h1>
				<h3>添加角色</h3>
				<c:if test="${fn:length(realms) == 0}">
				<label class="text-danger">您还没有添加自己的服务器，请到<a href="/page/user/realm">我的服务器</a>页面设置自己的服务器</label>
				</c:if>
				<form onsubmit="return false;" class="form-inline">
					<select id="realmSlt" class="form-control">
					<c:forEach items="${realms}" var="realm">
						<option value="${realm.realmId}">${realm.realmName}</option>
					</c:forEach>
					</select>
					<input id="characterNameInput" type="text" placeholder="角色名" class="form-control">
					<button id="addCharacterBtn" class="btn btn-primary">
						<span class="glyphicon glyphicon-plus"></span>添加角色
					</button>
					<label id="msg" class="text-danger"></label>
				</form>
			</div>
			<div class="row">
				<h3>所有角色</h3>
				<table class="table table-hover table-condensed">
					<thead>
						<tr>
							<th>#</th>
							<th>服务器</th>
							<th>角色名</th>
							<th>被压查看</th>
							<th>删除</th>
						</tr>
					</thead>
					<tbody id="characterBody">
						<c:forEach items="${characters}" var="character" varStatus="status">
							<tr>
								<td>${status.count}</td>							
								<td>${character.realmName}</td>
								<td>${character.name}</td>
								<td><a class="btn btn-sm btn-primary" href="/page/auction/owneritems/${character.name}/${character.realmId}">查看</a></td>
								<td><button class="btn btn-sm btn-danger removeCharacterBtn" realmId="${character.realmId}" cName="${character.name}">删除</button></td>
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
		function deleteBind() {
			$(".removeCharacterBtn").click(function(){
				var name = $(this).attr("cName");
				var realmId = $(this).attr("realmId");
				var character = {
					realmId : realmId,
					name : name
				}
				if (confirm("确定要删除该角色吗？")) {
					var result = BN.Resource.deleteUserCharacter(character);
					if (result.code == 0) {
						$("#msg").html(name + "删除成功");
						$(this).parent().parent().remove();
					} else {
						$("#msg").html(result.message);
					}
				}
			});
		}
		$("#addCharacterBtn").click(function(){
			var name = $("#characterNameInput").val();
			var realmId = $("#realmSlt").val();
			var character = {
				realmId : realmId,
				name : name
			}
			var result = BN.Resource.addUserCharacter(character);
			if (result.code == 0) {
				$("#characterBody").append("<tr><td></td><td>"+$("#realmSlt").find("option:selected").text()+"</td><td>"+name+"</td><td><a class='btn btn-sm btn-primary' href='/page/auction/owneritems/"+name+"/"+realmId+"'>查看</a></td><td><button class='btn btn-sm btn-danger removeCharacterBtn'  realmId='"+realmId+"' cName='"+name+"'>删除</button></td></tr>");
				deleteBind();
			} else {
				$("#msg").html(result.message);
			}
		});
		deleteBind();
	</script>
</body>
</html>